package br.com.systec.opusfinancial.financial.catalog.impl.service;

import br.com.systec.opusfinancial.api.service.CategoryService;
import br.com.systec.opusfinancial.api.domain.Category;
import br.com.systec.opusfinancial.financial.api.exceptions.IncomingFinancialNotFoundException;
import br.com.systec.opusfinancial.financial.api.filter.IncomingTransactionFilter;
import br.com.systec.opusfinancial.financial.api.service.AccountService;
import br.com.systec.opusfinancial.financial.api.domain.Account;
import br.com.systec.opusfinancial.financial.api.domain.FinancialTransaction;
import br.com.systec.opusfinancial.financial.catalog.impl.entity.FinancialTransactionEntity;
import br.com.systec.opusfinancial.financial.catalog.impl.fake.AccountFake;
import br.com.systec.opusfinancial.financial.catalog.impl.fake.CategoryFake;
import br.com.systec.opusfinancial.financial.catalog.impl.fake.FinancialTransactionFake;
import br.com.systec.opusfinancial.financial.catalog.impl.repository.TransactionRepository;
import br.com.systec.opusfinancial.i18n.I18nTranslate;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class IncomingTransactionServiceImplTest {

    @Mock
    private TransactionRepository repository;

    @Mock
    private AccountService accountService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private ResourceBundleMessageSource messageSource;
    @InjectMocks
    private I18nTranslate i18nTranslate;

    @InjectMocks
    private IncomingTransactionServiceImpl service;

    @Test
    void shouldSaveIncomingTransaction() {
        FinancialTransaction financialTransactionToSave = FinancialTransactionFake.createIncomingTransactionVO();
        FinancialTransactionEntity financialTransactionSaved = FinancialTransactionFake.createIncomingTransaction();

        doReturn(financialTransactionSaved).when(repository).save(any());

        FinancialTransaction incomingTransactionSaved = service.save(financialTransactionToSave);

        assertThat(incomingTransactionSaved).isNotNull();
        assertThat(incomingTransactionSaved.getId()).isNotNull();

        verify(repository).save(any());
    }

    @Test
    void shouldUpdateIncomingTransaction() {
        FinancialTransactionEntity financialTransactionToFindReturn = FinancialTransactionFake.createIncomingTransaction();
        FinancialTransaction financialTransactionToUpdate = FinancialTransactionFake.createExpenseTransactionVO();

        FinancialTransactionEntity financialTransactionUpdated = FinancialTransactionFake.createIncomingTransaction();

        doReturn(Optional.of(financialTransactionToFindReturn)).when(repository).findById(any());
        doReturn(financialTransactionUpdated).when(repository).save(any());

        FinancialTransaction incomingTransactionUpdate = service.update(financialTransactionToUpdate);

        assertThat(incomingTransactionUpdate).isNotNull();

        verify(repository).findById(any());
        verify(repository).save(any());
    }

    @Test
    void whenUpdateIncomingTransaction_thenFindObjectNotFound() {
        FinancialTransaction financialTransactionToSave = FinancialTransactionFake.createIncomingTransactionVO();

        doReturn(Optional.empty()).when(repository).findById(any());

        assertThatThrownBy(() -> service.update(financialTransactionToSave)).isInstanceOf(IncomingFinancialNotFoundException.class);
    }

    @Test
    void whenFindById_thenReturnIncomingTransaction() {
        FinancialTransactionEntity financialTransactionToReturn = FinancialTransactionFake.createIncomingTransaction();

        doReturn(Optional.of(financialTransactionToReturn)).when(repository).findById(any());

        FinancialTransaction financialTransaction = service.findById(UUID.randomUUID());

        assertThat(financialTransaction).isNotNull();
    }

    @Test
    void whenFindById_thenReturnNoFoundException() {
        doReturn(Optional.empty()).when(repository).findById(any());

        assertThatThrownBy(() -> service.findById(UUID.randomUUID())).isInstanceOf(IncomingFinancialNotFoundException.class);
    }

    @Test
    void whenFindByFilter() {
        List<FinancialTransactionEntity> listTransactionToReturn = List.of(FinancialTransactionFake.createIncomingTransaction());
        Page<FinancialTransactionEntity> pageResultToReturn = new PageImpl<>(listTransactionToReturn);
        List<Account> listOfAccountTOReturn = List.of(AccountFake.toFakeVO());
        List<Category> listOfCategoryToReturn = List.of(CategoryFake.toFakeVO());

        doReturn(listOfAccountTOReturn).when(accountService).findByIds(any());
        doReturn(listOfCategoryToReturn).when(categoryService).findByIds(any());
        doReturn(pageResultToReturn).when(repository).findAll(any(Specification.class), any(Pageable.class));

        Page<FinancialTransaction> pageReturn = service.findByFilter(new IncomingTransactionFilter(null, 30, 0));

        assertThat(pageReturn).isNotNull();

        verify(repository).findAll(any(Specification.class), any(Pageable.class));
        verify(accountService).findByIds(any());
        verify(categoryService).findByIds(any());

    }

}