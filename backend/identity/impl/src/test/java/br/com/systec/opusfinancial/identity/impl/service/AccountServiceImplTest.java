package br.com.systec.opusfinancial.identity.impl.service;

import br.com.systec.opusfinancial.commons.exceptions.BaseException;
import br.com.systec.opusfinancial.identity.api.exceptions.UsernameNotProvideInformationException;
import br.com.systec.opusfinancial.identity.api.services.TenantService;
import br.com.systec.opusfinancial.identity.api.services.UserService;
import br.com.systec.opusfinancial.identity.api.vo.AccountVO;
import br.com.systec.opusfinancial.identity.api.vo.TenantVO;
import br.com.systec.opusfinancial.identity.api.vo.UserVO;
import br.com.systec.opusfinancial.identity.impl.fake.AccountFake;
import br.com.systec.opusfinancial.identity.impl.fake.TenantFake;
import br.com.systec.opusfinancial.identity.impl.fake.UserFake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private TenantService tenantService;

    @InjectMocks
    private AccountServiceImpl accountService;

    private AccountVO accountVO;
    private UserVO userVO;
    private TenantVO tenantVO;

    @BeforeEach
    void setUp() {
        accountVO = AccountFake.toFakeVO();
        userVO = UserFake.toFakeVO();
        tenantVO = TenantFake.toFakeVO();
    }

    @Test
    void create_shouldCreateUserAndTenantSuccessfully() throws BaseException {
        // Arrange
        when(userService.create(any(UserVO.class))).thenReturn(userVO);
        doReturn(tenantVO).when(tenantService).create(any());

        // Act
        accountService.create(accountVO);

        // Assert
        verify(userService, times(1)).create(any(UserVO.class));
        verify(tenantService, times(1)).create(any());
    }

    @Test
    void create_shouldThrowBaseException_whenUserCreationFails() {
        // Arrange
        when(userService.create(any(UserVO.class))).thenThrow(new RuntimeException("User creation failed"));

        // Act & Assert
        assertThrows(BaseException.class, () -> accountService.create(accountVO));
        verify(tenantService, never()).create(any());
    }

    @Test
    void create_shouldThrowBaseException_whenTenantCreationFails() {
        // Arrange
        when(userService.create(any(UserVO.class))).thenReturn(userVO);
        doThrow(new RuntimeException("Tenant creation failed")).when(tenantService).create(any());

        // Act & Assert
        assertThrows(BaseException.class, () -> accountService.create(accountVO));
        verify(userService, times(1)).create(any(UserVO.class));
        verify(tenantService, times(1)).create(any());
    }

    @Test
    void whenCreateAccount_thenUsernameNotProvideInformation() {
        doThrow(UsernameNotProvideInformationException.class).when(userService).create(Mockito.any());

        assertThrows(UsernameNotProvideInformationException.class, () -> accountService.create(accountVO));
    }
}
