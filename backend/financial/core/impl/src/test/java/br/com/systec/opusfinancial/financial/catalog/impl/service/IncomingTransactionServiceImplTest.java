package br.com.systec.opusfinancial.financial.catalog.impl.service;

import br.com.systec.opusfinancial.api.service.CategoryService;
import br.com.systec.opusfinancial.api.vo.CategoryVO;
import br.com.systec.opusfinancial.financial.api.exceptions.IncomingFinancialNotFoundException;
import br.com.systec.opusfinancial.financial.api.filter.IncomingTransactionFilter;
import br.com.systec.opusfinancial.financial.api.service.AccountService;
import br.com.systec.opusfinancial.financial.api.vo.AccountVO;
import br.com.systec.opusfinancial.financial.api.vo.FinancialTransactionVO;
import br.com.systec.opusfinancial.financial.catalog.impl.domain.FinancialTransaction;
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
        FinancialTransactionVO financialTransactionToSave = FinancialTransactionFake.toFakeVO();
        FinancialTransaction financialTransactionSaved = FinancialTransactionFake.toFake();

        doReturn(financialTransactionSaved).when(repository).save(any());

        FinancialTransactionVO incomingTransactionSaved = service.save(financialTransactionToSave);

        assertThat(incomingTransactionSaved).isNotNull();
        assertThat(incomingTransactionSaved.getId()).isNotNull();

        verify(repository).save(any());
    }

    @Test
    void shouldUpdateIncomingTransaction() {
        FinancialTransaction financialTransactionToFindReturn = FinancialTransactionFake.toFake();
        FinancialTransactionVO financialTransactionToUpdate = FinancialTransactionFake.toFakeVO();

        FinancialTransaction financialTransactionUpdated = FinancialTransactionFake.toFake();

        doReturn(Optional.of(financialTransactionToFindReturn)).when(repository).findById(any());
        doReturn(financialTransactionUpdated).when(repository).save(any());

        FinancialTransactionVO incomingTransactionUpdate = service.update(financialTransactionToUpdate);

        assertThat(incomingTransactionUpdate).isNotNull();

        verify(repository).findById(any());
        verify(repository).save(any());
    }

    @Test
    void whenUpdateIncomingTransaction_thenFindObjectNotFound() {
        FinancialTransactionVO financialTransactionToSave = FinancialTransactionFake.toFakeVO();

        doReturn(Optional.empty()).when(repository).findById(any());

        assertThatThrownBy(() -> service.update(financialTransactionToSave)).isInstanceOf(IncomingFinancialNotFoundException.class);
    }

    @Test
    void whenFindById_thenReturnIncomingTransaction() {
        FinancialTransaction financialTransactionToReturn = FinancialTransactionFake.toFake();

        doReturn(Optional.of(financialTransactionToReturn)).when(repository).findById(any());

        FinancialTransactionVO financialTransaction = service.findById(UUID.randomUUID());

        assertThat(financialTransaction).isNotNull();
    }

    @Test
    void whenFindById_thenReturnNoFoundException() {
        doReturn(Optional.empty()).when(repository).findById(any());

        assertThatThrownBy(() -> service.findById(UUID.randomUUID())).isInstanceOf(IncomingFinancialNotFoundException.class);
    }

    @Test
    void whenFindByFilter() {
        List<FinancialTransaction> listTransactionToReturn = List.of(FinancialTransactionFake.toFake());
        Page<FinancialTransaction> pageResultToReturn = new PageImpl<>(listTransactionToReturn);
        List<AccountVO> listOfAccountTOReturn = List.of(AccountFake.toFakeVO());
        List<CategoryVO> listOfCategoryToReturn = List.of(CategoryFake.toFake());

        doReturn(listOfAccountTOReturn).when(accountService).findByIds(any());
        doReturn(listOfCategoryToReturn).when(categoryService).findByIds(any());
        doReturn(pageResultToReturn).when(repository).findAll(any(Specification.class), any(Pageable.class));

        Page<FinancialTransactionVO> pageReturn = service.findByFilter(new IncomingTransactionFilter(null, 30, 0));

        assertThat(pageReturn).isNotNull();

        verify(repository).findAll(any(Specification.class), any(Pageable.class));
        verify(accountService).findByIds(any());
        verify(categoryService).findByIds(any());

    }

}