package br.com.systec.opusfinancial.financial.catalog.impl.service;

import br.com.systec.opusfinancial.api.service.CategoryService;
import br.com.systec.opusfinancial.api.domain.Category;
import br.com.systec.opusfinancial.financial.api.exceptions.ExpenseFinancialNotFoundException;
import br.com.systec.opusfinancial.financial.api.filter.ExpenseTransactionFilter;
import br.com.systec.opusfinancial.financial.api.service.AccountService;
import br.com.systec.opusfinancial.financial.api.domain.Account;
import br.com.systec.opusfinancial.financial.api.domain.FinancialTransaction;
import br.com.systec.opusfinancial.financial.api.domain.TransactionType;
import br.com.systec.opusfinancial.financial.catalog.impl.entity.FinancialTransactionEntity;
import br.com.systec.opusfinancial.financial.catalog.impl.fake.FinancialTransactionFake;
import br.com.systec.opusfinancial.financial.catalog.impl.filter.ExpenseTransactionSpecification;
import br.com.systec.opusfinancial.financial.catalog.impl.mapper.FinancialTransactionDomainMapper;
import br.com.systec.opusfinancial.financial.catalog.impl.repository.TransactionRepository;
import br.com.systec.opusfinancial.i18n.I18nTranslate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseTransactionServiceImplTest {

    @Mock
    private TransactionRepository repository;

    @Mock
    private AccountService accountService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private FinancialTransactionDomainMapper mapper; // Injecting mock directly

    @InjectMocks
    private ExpenseTransactionServiceImpl service;

    @Mock
    private ResourceBundleMessageSource messageSource;
    @InjectMocks
    private I18nTranslate i18nTranslate;

    @Test
    @DisplayName("when_save_withValidData_then_shouldPersistTransactionAndUpdateBalance")
    void when_save_withValidData_then_shouldPersistTransactionAndUpdateBalance() {
        // Arrange
        FinancialTransaction transactionToSave = FinancialTransactionFake.createExpenseTransactionVO();
        FinancialTransactionEntity transactionToPersist = FinancialTransactionFake.createExpenseTransaction();
        FinancialTransactionEntity transactionPersisted = FinancialTransactionFake.createExpenseTransaction();
        FinancialTransaction transactionResult = FinancialTransactionFake.createExpenseTransactionVO();

        transactionToPersist.setProcessed(true);
        transactionPersisted.setProcessed(true);

        when(mapper.toEntity(any(), any(), any())).thenReturn(transactionToPersist);
        when(mapper.toVO(any())).thenReturn(transactionResult);
        when(repository.save(any(FinancialTransactionEntity.class))).thenReturn(transactionPersisted);
        doNothing().when(accountService).updateBalance(any(UUID.class), any(), any(TransactionType.class));

        // Act
        FinancialTransaction result = service.create(transactionToSave);

        // Assert
        assertThat(result).isNotNull();
        // Since we are mocking the return of toVO, we check if the result is equal to what we mocked
        assertThat(result).isEqualTo(transactionResult);
        verify(repository).save(transactionToPersist);
        verify(accountService).updateBalance(transactionPersisted.getAccountId(), transactionPersisted.getAmount(), TransactionType.EXPENSE);
    }

    @Test
    @DisplayName("when_save_withValidData_then_shouldPersistTransactionAndNotUpdateBalance")
    void when_save_withValidData_then_shouldPersistTransactionAndNotUpdateBalance() {
        // Arrange
        FinancialTransaction transactionToSave = FinancialTransactionFake.createExpenseTransactionVO();
        FinancialTransactionEntity transactionToPersist = FinancialTransactionFake.createExpenseTransaction();
        FinancialTransactionEntity transactionPersisted = FinancialTransactionFake.createExpenseTransaction();
        FinancialTransaction transactionResult = FinancialTransactionFake.createExpenseTransactionVO();

        transactionToPersist.setProcessed(false);
        transactionPersisted.setProcessed(false);

        when(mapper.toEntity(any(), any(), any())).thenReturn(transactionToPersist);
        when(mapper.toVO(any())).thenReturn(transactionResult);
        when(repository.save(any(FinancialTransactionEntity.class))).thenReturn(transactionPersisted);

        // Act
        FinancialTransaction result = service.create(transactionToSave);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(transactionResult);
        verify(repository).save(transactionToPersist);
        verify(accountService, never()).updateBalance(any(), any(), any());
    }

    @Test
    @DisplayName("when_delete_withExistingId_then_shouldDeleteTransactionAndRevertBalance")
    void when_delete_withExistingId_then_shouldDeleteTransactionAndRevertBalance() {
        // Arrange
        UUID transactionId = UUID.randomUUID();
        FinancialTransactionEntity transactionToDelete = FinancialTransactionFake.createExpenseTransaction();
        transactionToDelete.setProcessed(true); 
        
        when(repository.findById(transactionId)).thenReturn(Optional.of(transactionToDelete));
        doNothing().when(repository).delete(transactionToDelete);
        doNothing().when(accountService).updateBalance(any(UUID.class), any(), any(TransactionType.class));

        // Act
        service.delete(transactionId);

        // Assert
        verify(repository).findById(transactionId);
        verify(repository).delete(transactionToDelete);
        verify(accountService).updateBalance(transactionToDelete.getAccountId(), transactionToDelete.getAmount(), TransactionType.INCOMING);
    }

    @Test
    @DisplayName("when_delete_withNonExistingId_then_shouldThrowException")
    void when_delete_withNonExistingId_then_shouldThrowException() {
        // Arrange
        UUID transactionId = UUID.randomUUID();
        when(repository.findById(transactionId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> service.delete(transactionId))
                .isInstanceOf(ExpenseFinancialNotFoundException.class);

        verify(repository).findById(transactionId);
        verify(repository, never()).delete(any(FinancialTransactionEntity.class));
        verify(accountService, never()).updateBalance(any(), any(), any());
    }

    @Test
    @DisplayName("when_findById_withExistingId_then_shouldReturnTransaction")
    void when_findById_withExistingId_then_shouldReturnTransaction() {
        // Arrange
        UUID transactionId = UUID.randomUUID();
        FinancialTransactionEntity transactionFound = FinancialTransactionFake.createExpenseTransaction();
        FinancialTransaction transactionResult = FinancialTransactionFake.createExpenseTransactionVO();

        when(repository.findById(transactionId)).thenReturn(Optional.of(transactionFound));
        when(mapper.toVO(transactionFound)).thenReturn(transactionResult);

        // Act
        FinancialTransaction result = service.findById(transactionId);

        // Assert
        assertThat(result).isNotNull().isEqualTo(transactionResult);
        verify(repository).findById(transactionId);
    }

    @Test
    @DisplayName("when_findById_withNonExistingId_then_shouldThrowException")
    void when_findById_withNonExistingId_then_shouldThrowException() {
        // Arrange
        UUID transactionId = UUID.randomUUID();
        when(repository.findById(transactionId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> service.findById(transactionId))
                .isInstanceOf(ExpenseFinancialNotFoundException.class);

        verify(repository).findById(transactionId);
    }

    @Test
    @DisplayName("when_findByFilter_then_shouldReturnPageWithEnrichedData")
    void when_findByFilter_then_shouldReturnPageWithEnrichedData() {
        // Arrange
        ExpenseTransactionFilter filter = mock(ExpenseTransactionFilter.class);
        Pageable pageable = mock(Pageable.class);
        when(filter.getPageable()).thenReturn(pageable);

        FinancialTransactionEntity transaction = FinancialTransactionFake.createExpenseTransaction();
        Page<FinancialTransactionEntity> pageResult = new PageImpl<>(List.of(transaction));

        Account accountVO = new Account();
        accountVO.setId(transaction.getAccountId());
        Category categoryVO = new Category();
        categoryVO.setId(transaction.getCategoryId());

        Page<FinancialTransaction> finalPage = new PageImpl<>(List.of(FinancialTransactionFake.createExpenseTransactionVO()));

        try (MockedStatic<ExpenseTransactionSpecification> specStatic = mockStatic(ExpenseTransactionSpecification.class)) {
            ExpenseTransactionSpecification specMock = mock(ExpenseTransactionSpecification.class);
            Specification<FinancialTransactionEntity> specification = mock(Specification.class);
            specStatic.when(ExpenseTransactionSpecification::of).thenReturn(specMock);
            when(specMock.filter(filter)).thenReturn(specification);

            when(repository.findAll(specification, pageable)).thenReturn(pageResult);
            when(accountService.findByIds(List.of(transaction.getAccountId()))).thenReturn(List.of(accountVO));
            when(categoryService.findByIds(List.of(transaction.getCategoryId()))).thenReturn(List.of(categoryVO));
            
            // Mock mapper
            when(mapper.toPage(pageResult, List.of(accountVO), List.of(categoryVO))).thenReturn(finalPage);

            // Act
            Page<FinancialTransaction> result = service.findByFilter(filter);

            // Assert
            assertThat(result).isNotNull().isEqualTo(finalPage);
            verify(repository).findAll(specification, pageable);
            verify(accountService).findByIds(List.of(transaction.getAccountId()));
            verify(categoryService).findByIds(List.of(transaction.getCategoryId()));
        }
    }

    @Test
    @DisplayName("when_findByFilter_withNoResults_then_shouldReturnEmptyPage")
    void when_findByFilter_withNoResults_then_shouldReturnEmptyPage() {
        // Arrange
        ExpenseTransactionFilter filter = mock(ExpenseTransactionFilter.class);
        Pageable pageable = mock(Pageable.class);
        when(filter.getPageable()).thenReturn(pageable);

        Page<FinancialTransactionEntity> emptyPageResult = Page.empty();
        Page<FinancialTransaction> emptyFinalPage = Page.empty();

        try (MockedStatic<ExpenseTransactionSpecification> specStatic = mockStatic(ExpenseTransactionSpecification.class)) {
            ExpenseTransactionSpecification specMock = mock(ExpenseTransactionSpecification.class);
            Specification<FinancialTransactionEntity> specification = mock(Specification.class);
            specStatic.when(ExpenseTransactionSpecification::of).thenReturn(specMock);
            when(specMock.filter(filter)).thenReturn(specification);

            when(repository.findAll(specification, pageable)).thenReturn(emptyPageResult);
            
            // Mock mapper
            when(mapper.toPage(emptyPageResult, Collections.emptyList(), Collections.emptyList())).thenReturn(emptyFinalPage);

            // Act
            Page<FinancialTransaction> result = service.findByFilter(filter);

            // Assert
            assertThat(result).isNotNull().isEmpty();
        }
    }
}
