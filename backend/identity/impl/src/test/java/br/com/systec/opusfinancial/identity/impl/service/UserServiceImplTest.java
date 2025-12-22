package br.com.systec.opusfinancial.identity.impl.service;

import br.com.systec.opusfinancial.identity.api.exceptions.UserException;
import br.com.systec.opusfinancial.identity.api.exceptions.UserNotFoundException;
import br.com.systec.opusfinancial.identity.api.exceptions.UserPasswordNotProvideInformationException;
import br.com.systec.opusfinancial.identity.api.exceptions.UserUsernameOrEmailExistException;
import br.com.systec.opusfinancial.identity.api.exceptions.UsernameNotProvideInformationException;
import br.com.systec.opusfinancial.identity.api.vo.UserVO;
import br.com.systec.opusfinancial.identity.impl.entities.User;
import br.com.systec.opusfinancial.identity.impl.fake.UserFake;
import br.com.systec.opusfinancial.identity.impl.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void whenCreateUser_thenReturnUserVO() {
        UserVO userToSaveVO = UserFake.toFakeVO();
        User userToReturnSave = UserFake.toFake();

        Mockito.doReturn(userToReturnSave).when(repository).save(Mockito.any(User.class));

        UserVO userAfterSave = userService.create(userToSaveVO);

        Assertions.assertThat(userAfterSave).isNotNull();
        Assertions.assertThat(userAfterSave.getId()).isEqualTo(userToReturnSave.getId());
        Assertions.assertThat(userAfterSave.getName()).isEqualTo(userToReturnSave.getName());
        Assertions.assertThat(userAfterSave.getEmail()).isEqualTo(userToReturnSave.getEmail());

        Mockito.verify(repository).save(Mockito.any(User.class));
    }

    @Test
    void whenCreateUser_thenUsernameNotProvideInformationException() {
        UserVO userToSaveVO = UserFake.toFakeVO();
        userToSaveVO.setUsername(null);

        Assertions.assertThatThrownBy(() -> userService.create(userToSaveVO))
                .isInstanceOf(UsernameNotProvideInformationException.class);
    }

    @Test
    void whenCreateUser_thenUserPasswordNotProvideInformationException() {
        UserVO userToSaveVO = UserFake.toFakeVO();
        userToSaveVO.setPassword(null);

        Assertions.assertThatThrownBy(() -> userService.create(userToSaveVO))
                .isInstanceOf(UserPasswordNotProvideInformationException.class);
    }

    @Test
    void whenCreateUser_thenUsernameOrEmailExistException() {
        UserVO userToSaveVO = UserFake.toFakeVO();

        Mockito.doReturn(Optional.of(UserFake.toFake())).when(repository).findByUsernameOrEmail(
                Mockito.anyString(), Mockito.anyString()
        );

        Assertions.assertThatThrownBy(() -> userService.create(userToSaveVO))
                .isInstanceOf(UserUsernameOrEmailExistException.class);
    }

    @Test
    void whenCreateUser_thenErrorRepository_thenReturnUserException() {
        UserVO userToSaveVO = UserFake.toFakeVO();

        Mockito.doThrow(new RuntimeException("Erro teste de banco")).when(repository)
                .save(Mockito.any(User.class));

        Assertions.assertThatThrownBy(() -> userService.create(userToSaveVO))
                .isInstanceOf(UserException.class);
    }

    @Test
    void whenFindByUsername_thenReturnUserVO() {
        User userToReturn = UserFake.toFake();

        Mockito.doReturn(Optional.of(userToReturn)).when(repository).findByUsername(Mockito.anyString());

        UserVO userAfterFind = userService.findByUsername(userToReturn.getUsername());

        Assertions.assertThat(userAfterFind).isNotNull();
        Assertions.assertThat(userAfterFind.getId()).isEqualTo(userToReturn.getId());
        Assertions.assertThat(userAfterFind.getName()).isEqualTo(userToReturn.getName());
        Assertions.assertThat(userAfterFind.getEmail()).isEqualTo(userToReturn.getEmail());

        Mockito.verify(repository).findByUsername(Mockito.anyString());
    }

    @Test
    void whenFindByUsername_thenReturnUserNotFoundException() {
        Mockito.doReturn(Optional.empty()).when(repository).findByUsername(Mockito.anyString());

        Assertions.assertThatThrownBy(() -> userService.findByUsername(UserFake.toFake().getUsername()))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void whenFindByUsername_thenErrorRepository_thenReturnSecurityException() {
        Mockito.doThrow(new RuntimeException("Erro teste de banco")).when(repository)
                .findByUsername(Mockito.anyString());

        Assertions.assertThatThrownBy(() -> userService.findByUsername(UserFake.toFake().getEmail()))
                .isInstanceOf(SecurityException.class);
    }

    @Test
    void whenFindById_thenReturnUserVO() {
        User userToReturn = UserFake.toFake();

        Mockito.doReturn(Optional.of(userToReturn)).when(repository).findById(Mockito.any());

        UserVO userAfterFind = userService.findById(userToReturn.getId());

        Assertions.assertThat(userAfterFind).isNotNull();
        Assertions.assertThat(userAfterFind.getId()).isEqualTo(userToReturn.getId());
        Assertions.assertThat(userAfterFind.getName()).isEqualTo(userToReturn.getName());
        Assertions.assertThat(userAfterFind.getEmail()).isEqualTo(userToReturn.getEmail());

        Mockito.verify(repository).findById(Mockito.any());
    }


}
