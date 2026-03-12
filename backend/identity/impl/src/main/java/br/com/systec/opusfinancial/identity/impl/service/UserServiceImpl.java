package br.com.systec.opusfinancial.identity.impl.service;

import br.com.systec.opusfinancial.identity.api.domain.User;
import br.com.systec.opusfinancial.identity.api.exceptions.UserException;
import br.com.systec.opusfinancial.identity.api.exceptions.UserNotFoundException;
import br.com.systec.opusfinancial.identity.api.exceptions.UserPasswordNotProvideInformationException;
import br.com.systec.opusfinancial.identity.api.exceptions.UserUsernameOrEmailExistException;
import br.com.systec.opusfinancial.identity.api.exceptions.UsernameNotProvideInformationException;
import br.com.systec.opusfinancial.identity.api.services.UserService;
import br.com.systec.opusfinancial.identity.impl.entities.UserEntity;
import br.com.systec.opusfinancial.identity.impl.mapper.UserMapper;
import br.com.systec.opusfinancial.identity.impl.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User create(User userVO) {
        UserEntity userToSave = UserMapper.of().toEntity(userVO);

        validateSaveNewUser(userToSave);
        userToSave.setPassword(passwordEncoder.encode(userToSave.getPassword()));

        UserEntity userAfterSave = repository.save(userToSave);

        return UserMapper.of().toVO(userAfterSave);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User update(User userVO) {
        UserEntity userSaved = repository.findById(userVO.getId()).orElseThrow(UserNotFoundException::new);

        UserEntity userBeforeUpdate = UserMapper.of().toEntity(userVO);
        userBeforeUpdate.setCreateAt(userSaved.getCreateAt());

        if (userVO.getPassword() == null || userVO.getPassword().isEmpty()) {
            userBeforeUpdate.setPassword(userSaved.getPassword());
        } else {
            userBeforeUpdate.setPassword(passwordEncoder.encode(userVO.getPassword()));
        }

        UserEntity userAfterUpdate = repository.save(userBeforeUpdate);

        return UserMapper.of().toVO(userAfterUpdate);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveTenantId(UUID userId, UUID tenantId) throws UserException {
        UserEntity userToSave = repository.findById(userId).orElseThrow(UserNotFoundException::new);
        userToSave.setTenantId(tenantId);
        repository.save(userToSave);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User findById(UUID id) {
        UserEntity user = repository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        return UserMapper.of().toVO(user);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public User findByUsername(String username) {
        UserEntity user = repository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        return UserMapper.of().toVO(user);

    }

    private void validateSaveNewUser(UserEntity user) {
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new UserPasswordNotProvideInformationException();
        }

        if (user.getUsername().isBlank()) {
            throw new UsernameNotProvideInformationException();
        }

        Optional<UserEntity> userExist = repository.findByUsernameOrEmail(user.getUsername(), user.getEmail());

        if (userExist.isPresent()) {
            throw new UserUsernameOrEmailExistException();
        }
    }
}
