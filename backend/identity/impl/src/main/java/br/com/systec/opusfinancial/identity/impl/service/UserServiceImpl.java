package br.com.systec.opusfinancial.identity.impl.service;

import br.com.systec.opusfinancial.commons.exceptions.BaseException;
import br.com.systec.opusfinancial.identity.api.exceptions.UserException;
import br.com.systec.opusfinancial.identity.api.exceptions.UserNotFoundException;
import br.com.systec.opusfinancial.identity.api.exceptions.UserPasswordNotProvideInformationException;
import br.com.systec.opusfinancial.identity.api.exceptions.UserUsernameOrEmailExistException;
import br.com.systec.opusfinancial.identity.api.exceptions.UsernameNotProvideInformationException;
import br.com.systec.opusfinancial.identity.api.services.UserService;
import br.com.systec.opusfinancial.identity.api.vo.UserVO;
import br.com.systec.opusfinancial.identity.impl.entities.User;
import br.com.systec.opusfinancial.identity.impl.mapper.UserMapper;
import br.com.systec.opusfinancial.identity.impl.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserVO create(UserVO userVO) throws UserException {
        try {
            User userToSave = UserMapper.of().toEntity(userVO);

            validateSaveNewUser(userToSave);
            userToSave.setPassword(new BCryptPasswordEncoder().encode(userToSave.getPassword()));

            User userAfterSave = repository.save(userToSave);

            return UserMapper.of().toVO(userAfterSave);
        } catch (BaseException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Erro ao tentar salvar o usuario", e);
            throw new UserException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserVO update(UserVO userVO) throws UserException {
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserVO findById(UUID id) throws UserException {
        try {
            User user = repository.findById(id)
                    .orElseThrow(UserNotFoundException::new);

            return UserMapper.of().toVO(user);
        } catch (BaseException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Erro ao tentar pesquisar o usuario");
            throw new UserException(e);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UserVO findByUsername(String username) throws BaseException {
        try {
            User user = repository.findByUsername(username).orElseThrow(UserNotFoundException::new);
            return UserMapper.of().toVO(user);
        } catch (BaseException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar pesqusiar o usuario", e);
            throw new SecurityException();
        }
    }

    private void validateSaveNewUser(User user) {
        if(user.getPassword() == null || user.getPassword().isBlank()) {
            throw new UserPasswordNotProvideInformationException();
        }

        if(user.getUsername() == null || user.getUsername().isBlank()) {
            throw new UsernameNotProvideInformationException();
        }

        Optional<User> userEmailOrUsername = repository.findByUsernameOrEmail(user.getUsername(), user.getEmail());

        if (userEmailOrUsername.isPresent()) {
            throw new UserUsernameOrEmailExistException();
        }
    }
}
