package br.com.systec.opusfinancial.financial.catalog.impl.service;

import br.com.systec.opusfinancial.api.service.BankService;
import br.com.systec.opusfinancial.api.vo.BankVO;
import br.com.systec.opusfinancial.financial.api.exceptions.AccountNotFoundException;
import br.com.systec.opusfinancial.financial.api.filter.FilterAccount;
import br.com.systec.opusfinancial.financial.api.vo.AccountType;
import br.com.systec.opusfinancial.financial.api.vo.AccountVO;
import br.com.systec.opusfinancial.financial.catalog.impl.domain.Account;
import br.com.systec.opusfinancial.financial.catalog.impl.repository.AccountRepository;
import br.com.systec.opusfinancial.i18n.I18nTranslate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository repository;
    @Mock
    private BankService bankService;

    @InjectMocks
    private AccountServiceImpl service;

    @Mock
    private ResourceBundleMessageSource messageSource;
    @InjectMocks
    private I18nTranslate i18nTranslate;

    @Test
    @DisplayName("Should create account successfully")
    void shouldCreateAccountSuccessfully() {
        AccountVO accountVO = createAccountVO();
        Account account = createAccountFromVO(accountVO);


        when(repository.save(any(Account.class))).thenReturn(account);

        AccountVO result = service.create(accountVO);

        assertNotNull(result);
        assertEquals(accountVO.getId(), result.getId());
        assertEquals(accountVO.getAccountName(), result.getAccountName());
        assertEquals(accountVO.getBalance(), result.getBalance());
        assertEquals(accountVO.getAccountType(), result.getAccountType());
        
        verify(repository, times(1)).save(any(Account.class));
    }

    @Test
    @DisplayName("Should create account without bank successfully")
    void shouldCreateAccountWithoutBankSuccessfully() {
        AccountVO accountVO = createAccountVO();
        accountVO.setBank(null);
        
        Account account = createAccountFromVO(accountVO);
        account.setBankId(null);

        when(repository.save(any(Account.class))).thenReturn(account);

        AccountVO result = service.create(accountVO);

        assertNotNull(result);
        assertEquals(accountVO.getId(), result.getId());
        assertNull(result.getBank());
        
        verify(repository, times(1)).save(any(Account.class));
    }

    @Test
    @DisplayName("Should update account successfully")
    void shouldUpdateAccountSuccessfully() {
        AccountVO accountVO = createAccountVO();
        Account existingAccount = createAccountFromVO(accountVO);
        Date createdAt = new Date();
        existingAccount.setCreateAt(createdAt);

        when(repository.findById(accountVO.getId())).thenReturn(Optional.of(existingAccount));
        // The service saves the entity converted from VO, but with createdAt from existing
        when(repository.save(any(Account.class))).thenAnswer(invocation -> {
            Account saved = invocation.getArgument(0);
            // Verify createdAt was preserved
            assertEquals(createdAt, saved.getCreateAt());
            return saved;
        });

        AccountVO result = service.update(accountVO);

        assertNotNull(result);
        assertEquals(accountVO.getId(), result.getId());
        verify(repository, times(1)).findById(accountVO.getId());
        verify(repository, times(1)).save(any(Account.class));
    }

    @Test
    @DisplayName("Should throw AccountNotFoundException when updating non-existent account")
    void shouldThrowExceptionWhenUpdatingNonExistentAccount() {
        AccountVO accountVO = createAccountVO();

        when(repository.findById(accountVO.getId())).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> service.update(accountVO));
        verify(repository, times(1)).findById(accountVO.getId());
        verify(repository, never()).save(any(Account.class));
    }

    @Test
    @DisplayName("Should find account by ID successfully")
    void shouldFindAccountByIdSuccessfully() {
        UUID id = UUID.randomUUID();
        Account account = createAccount();
        account.setId(id);

        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(account));
        when(bankService.findById(any(UUID.class))).thenReturn(createBankVO());

        AccountVO result = service.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertNotNull(result.getBank());

        verify(repository, times(1)).findById(any(UUID.class));
        verify(bankService, times(1)).findById(any(UUID.class));
    }

    @Test
    @DisplayName("Should throw AccountNotFoundException when finding non-existent account by ID")
    void shouldThrowExceptionWhenFindingNonExistentAccountById() {
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> service.findById(UUID.randomUUID()));
        verify(repository, times(1)).findById(any(UUID.class));
    }

    @Test
    @DisplayName("Should find accounts by filter successfully")
    void shouldFindAccountsByFilterSuccessfully() {
        FilterAccount filter = new FilterAccount("test", 10, 0);
        Account account = createAccount();
        Page<Account> page = new PageImpl<>(Collections.singletonList(account));

        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);

        Page<AccountVO> result = service.findByFilter(filter);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(repository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    @DisplayName("Should find accounts by filter with empty keyword successfully")
    void shouldFindAccountsByFilterWithEmptyKeywordSuccessfully() {
        FilterAccount filter = new FilterAccount("", 10, 0);
        Account account = createAccount();
        Page<Account> page = new PageImpl<>(Collections.singletonList(account));

        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);

        Page<AccountVO> result = service.findByFilter(filter);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(repository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }

    private AccountVO createAccountVO() {
        AccountVO vo = new AccountVO();
        vo.setId(UUID.randomUUID());
        vo.setAccountName("Test Account");
        vo.setBalance(BigDecimal.TEN);
        vo.setAccountType(AccountType.CHECKING);
        vo.setTenantId(UUID.randomUUID());

        vo.setBank(createBankVO());
        
        return vo;
    }

    private BankVO createBankVO() {
        BankVO bankVO = new BankVO();
        bankVO.setId(UUID.randomUUID());
        return bankVO;
    }

    private Account createAccount() {
        Account account = new Account();
        account.setId(UUID.randomUUID());
        account.setAccountName("Test Account");
        account.setBalance(BigDecimal.TEN);
        account.setAccountType(AccountType.CHECKING);
        account.setBankId(UUID.randomUUID());

        return account;
    }
    
    private Account createAccountFromVO(AccountVO vo) {
        Account account = new Account();
        account.setId(vo.getId());
        account.setAccountName(vo.getAccountName());
        account.setBalance(vo.getBalance());
        account.setAccountType(vo.getAccountType());
        account.setTenantId(vo.getTenantId());
        
        if (vo.getBank() != null) {
            account.setBankId(vo.getBank().getId());
        }
        
        return account;
    }
}
