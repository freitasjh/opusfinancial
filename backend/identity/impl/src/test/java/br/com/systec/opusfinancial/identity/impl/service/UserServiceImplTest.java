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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void whenCreateUser_thenReturnUserVO() {
        UserVO userToSaveVO = UserFake.toFakeVO();
        User userToReturnSave = UserFake.toFake();

        Mockito.doReturn(userToReturnSave).when(repository).save(Mockito.any(User.class));
        Mockito.doReturn("encodedPass").when(passwordEncoder).encode(Mockito.any());

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

    @Test
    void whenFindById_thenReturnUserNotFoundException() {
        UUID userId = UUID.randomUUID();
        Mockito.doReturn(Optional.empty()).when(repository).findById(userId);

        Assertions.assertThatThrownBy(() -> userService.findById(userId))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void whenFindById_thenErrorRepository_thenReturnUserException() {
        UUID userId = UUID.randomUUID();
        Mockito.doThrow(new RuntimeException("Database error")).when(repository).findById(userId);

        Assertions.assertThatThrownBy(() -> userService.findById(userId))
                .isInstanceOf(UserException.class);
    }

    @Test
    void whenUpdateUser_andNotUpdatePassword_thenReturnUserVOSuccess() {
        String passwordUserSaved = new BCryptPasswordEncoder().encode("teste");
        User userToReturnFindById = UserFake.toFake();
        userToReturnFindById.setPassword(passwordUserSaved);

        UserVO userToUpdate = UserFake.toFakeVO();
        userToUpdate.setId(userToReturnFindById.getId());
        userToUpdate.setPassword(null);

        Mockito.doReturn(userToReturnFindById).when(repository).update(Mockito.any(User.class));
        Mockito.doReturn(Optional.of(userToReturnFindById)).when(repository).findById(userToUpdate.getId());

        userService.update(userToUpdate);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(repository).update(userCaptor.capture());
        
        User userCaptured = userCaptor.getValue();
        Assertions.assertThat(userCaptured.getPassword()).isEqualTo(passwordUserSaved);
    }

    @Test
    void whenUpdateUser_andUpdatePassword_thenReturnUserVOSuccess() {
        String passwordUserSaved = new BCryptPasswordEncoder().encode("teste");
        String newPassword = new BCryptPasswordEncoder().encode("user01");
        User userToReturnFindById = UserFake.toFake();
        userToReturnFindById.setPassword(passwordUserSaved);

        UserVO userToUpdate = UserFake.toFakeVO();
        userToUpdate.setId(userToReturnFindById.getId());
        userToUpdate.setPassword("user01");

        Mockito.doReturn(userToReturnFindById).when(repository).update(Mockito.any(User.class));
        Mockito.doReturn(Optional.of(userToReturnFindById)).when(repository).findById(userToUpdate.getId());
        Mockito.doReturn(newPassword).when(passwordEncoder).encode(Mockito.anyString());

        userService.update(userToUpdate);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(repository).update(userCaptor.capture());
        Mockito.verify(repository).findById(userToUpdate.getId());

        User userCaptured = userCaptor.getValue();
        Assertions.assertThat(userCaptured.getPassword()).isNotEqualTo(passwordUserSaved);
        Assertions.assertThat(userCaptured.getPassword()).isEqualTo(newPassword);
    }

    @Test
    void whenUpdateUser_thenReturnUserNotFoundException() {
        Mockito.doReturn(Optional.empty()).when(repository).findById(Mockito.any(UUID.class));

        Assertions.assertThatThrownBy(() -> userService.update(UserFake.toFakeVO()))
                .isInstanceOf(UserNotFoundException.class);

        Mockito.verify(repository).findById(Mockito.any(UUID.class));
        Mockito.verify(repository, Mockito.never()).update(Mockito.any(User.class));
    }

    @Test
    void whenUpdateUser_thenReturnUserFoundException() {
        Mockito.doReturn(Optional.of(UserFake.toFake())).when(repository)
                .findById(Mockito.any(UUID.class));

        Mockito.doThrow(new RuntimeException("Erro banco")).when(repository).update(Mockito.any(User.class));

        Assertions.assertThatThrownBy(() -> userService.update(UserFake.toFakeVO()))
                .isInstanceOf(UserException.class);

        Mockito.verify(repository).findById(Mockito.any(UUID.class));
        Mockito.verify(repository).update(Mockito.any(User.class));
    }

    @Test
    void saveTenantId_shouldUpdateUserTenantId_whenUserExists() {
        // Arrange
        UUID userId = UUID.randomUUID();
        UUID tenantId = UUID.randomUUID();
        User user = UserFake.toFake();
        user.setId(userId);

        Mockito.when(repository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(repository.update(Mockito.any(User.class))).thenReturn(user);

        // Act
        userService.saveTenantId(userId, tenantId);

        // Assert
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(repository).update(userCaptor.capture());
        User capturedUser = userCaptor.getValue();

        Assertions.assertThat(capturedUser.getId()).isEqualTo(userId);
        Assertions.assertThat(capturedUser.getTenantId()).isEqualTo(tenantId);
    }

    @Test
    void saveTenantId_shouldThrowUserNotFoundException_whenUserDoesNotExist() {
        // Arrange
        UUID userId = UUID.randomUUID();
        UUID tenantId = UUID.randomUUID();

        Mockito.when(repository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThatThrownBy(() -> userService.saveTenantId(userId, tenantId))
                .isInstanceOf(UserNotFoundException.class);

        Mockito.verify(repository, Mockito.never()).update(Mockito.any());
    }

    @Test
    void saveTenantId_shouldThrowUserException_whenRepositoryFails() {
        // Arrange
        UUID userId = UUID.randomUUID();
        UUID tenantId = UUID.randomUUID();
        User user = UserFake.toFake();

        Mockito.when(repository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.doThrow(new RuntimeException("Database error")).when(repository).update(Mockito.any(User.class));

        // Act & Assert
        Assertions.assertThatThrownBy(() -> userService.saveTenantId(userId, tenantId))
                .isInstanceOf(UserException.class);
    }
}
