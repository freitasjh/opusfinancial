package br.com.systec.opusfinancial.financial.catalog.impl.service;

import br.com.systec.opusfinancial.api.service.CategoryService;
import br.com.systec.opusfinancial.api.vo.CategoryVO;
import br.com.systec.opusfinancial.financial.api.exceptions.ExpenseFinancialNotFoundException;
import br.com.systec.opusfinancial.financial.api.filter.ExpenseTransactionFilter;
import br.com.systec.opusfinancial.financial.api.service.AccountService;
import br.com.systec.opusfinancial.financial.api.vo.AccountVO;
import br.com.systec.opusfinancial.financial.api.vo.FinancialTransactionVO;
import br.com.systec.opusfinancial.financial.api.vo.TransactionType;
import br.com.systec.opusfinancial.financial.catalog.impl.entity.FinancialTransaction;
import br.com.systec.opusfinancial.financial.catalog.impl.fake.FinancialTransactionFake;
import br.com.systec.opusfinancial.financial.catalog.impl.filter.ExpenseTransactionSpecification;
import br.com.systec.opusfinancial.financial.catalog.impl.mapper.FinancialTransactionMapper;
import br.com.systec.opusfinancial.financial.catalog.impl.repository.TransactionRepository;
import br.com.systec.opusfinancial.i18n.I18nTranslate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    @InjectMocks
    private ExpenseTransactionServiceImpl service;

    private MockedStatic<FinancialTransactionMapper> mapperStatic;
    private FinancialTransactionMapper mapperMock;

    @Mock
    private ResourceBundleMessageSource messageSource;
    @InjectMocks
    private I18nTranslate i18nTranslate;

    @BeforeEach
    void setUp() {
        mapperStatic = mockStatic(FinancialTransactionMapper.class);
        mapperMock = mock(FinancialTransactionMapper.class);
        mapperStatic.when(FinancialTransactionMapper::of).thenReturn(mapperMock);
    }

    @AfterEach
    void tearDown() {
        mapperStatic.close();
    }

    @Test
    @DisplayName("when_save_withValidData_then_shouldPersistTransactionAndUpdateBalance")
    void when_save_withValidData_then_shouldPersistTransactionAndUpdateBalance() {
        // Arrange
        FinancialTransactionVO transactionToSave = FinancialTransactionFake.createExpenseTransactionVO();
        FinancialTransaction transactionPersisted = FinancialTransactionFake.createExpenseTransaction();
        transactionPersisted.setProcessed(true);

        // Use real mapper logic for toEntity to ensure correct mapping
        when(mapperMock.toEntity(any(), any(), any())).thenCallRealMethod();
        when(mapperMock.toVO(any())).thenCallRealMethod();

        when(repository.save(any(FinancialTransaction.class))).thenReturn(transactionPersisted);
        doNothing().when(accountService).updateBalance(any(UUID.class), any(), any(TransactionType.class));

        // Act
        FinancialTransactionVO result = service.create(transactionToSave);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(transactionPersisted.getId());
        verify(repository).save(any(FinancialTransaction.class));
        verify(accountService).updateBalance(transactionPersisted.getAccountId(), transactionPersisted.getAmount(), TransactionType.EXPENSE);
    }

    @Test
    @DisplayName("when_save_withValidData_then_shouldPersistTransactionAndUpdateBalance")
    void when_save_withValidData_then_shouldPersistTransactionAndNotUpdateBalance() {
        // Arrange
        FinancialTransactionVO transactionToSave = FinancialTransactionFake.createExpenseTransactionVO();
        FinancialTransaction transactionPersisted = FinancialTransactionFake.createExpenseTransaction();
        transactionPersisted.setProcessed(false);

        // Use real mapper logic for toEntity to ensure correct mapping
        when(mapperMock.toEntity(any(), any(), any())).thenCallRealMethod();
        when(mapperMock.toVO(any())).thenCallRealMethod();

        when(repository.save(any(FinancialTransaction.class))).thenReturn(transactionPersisted);

        // Act
        FinancialTransactionVO result = service.create(transactionToSave);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(transactionPersisted.getId());
        verify(repository).save(any(FinancialTransaction.class));
        verify(accountService, never()).updateBalance(transactionPersisted.getAccountId(), transactionPersisted.getAmount(), TransactionType.EXPENSE);
    }

    @Test
    @DisplayName("when_delete_withExistingId_then_shouldDeleteTransactionAndRevertBalance")
    void when_delete_withExistingId_then_shouldDeleteTransactionAndRevertBalance() {
        // Arrange
        UUID transactionId = UUID.randomUUID();
        FinancialTransaction transactionToDelete = FinancialTransactionFake.createExpenseTransaction();
        transactionToDelete.setProcessed(true); // Ensure it's processed to trigger balance update
        
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
        verify(repository, never()).delete(any(FinancialTransaction.class));
        verify(accountService, never()).updateBalance(any(), any(), any());
    }

    @Test
    @DisplayName("when_findById_withExistingId_then_shouldReturnTransaction")
    void when_findById_withExistingId_then_shouldReturnTransaction() {
        // Arrange
        UUID transactionId = UUID.randomUUID();
        FinancialTransaction transactionFound = FinancialTransactionFake.createExpenseTransaction();
        
        // Mock only for search/retrieval as requested
        FinancialTransactionVO transactionResult = FinancialTransactionFake.createExpenseTransactionVO();
        when(repository.findById(transactionId)).thenReturn(Optional.of(transactionFound));
        when(mapperMock.toVO(transactionFound)).thenReturn(transactionResult);

        // Act
        FinancialTransactionVO result = service.findById(transactionId);

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

        FinancialTransaction transaction = FinancialTransactionFake.createExpenseTransaction();
        Page<FinancialTransaction> pageResult = new PageImpl<>(List.of(transaction));

        AccountVO accountVO = new AccountVO();
        accountVO.setId(transaction.getAccountId());
        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setId(transaction.getCategoryId());

        Page<FinancialTransactionVO> finalPage = new PageImpl<>(List.of(FinancialTransactionFake.createExpenseTransactionVO()));

        try (MockedStatic<ExpenseTransactionSpecification> specStatic = mockStatic(ExpenseTransactionSpecification.class)) {
            ExpenseTransactionSpecification specMock = mock(ExpenseTransactionSpecification.class);
            Specification<FinancialTransaction> specification = mock(Specification.class);
            specStatic.when(ExpenseTransactionSpecification::of).thenReturn(specMock);
            when(specMock.filter(filter)).thenReturn(specification);

            when(repository.findAll(specification, pageable)).thenReturn(pageResult);
            when(accountService.findByIds(List.of(transaction.getAccountId()))).thenReturn(List.of(accountVO));
            when(categoryService.findByIds(List.of(transaction.getCategoryId()))).thenReturn(List.of(categoryVO));
            
            // Mock mapper for search/retrieval
            when(mapperMock.toPage(pageResult, List.of(accountVO), List.of(categoryVO))).thenReturn(finalPage);

            // Act
            Page<FinancialTransactionVO> result = service.findByFilter(filter);

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

        Page<FinancialTransaction> emptyPageResult = Page.empty();
        Page<FinancialTransactionVO> emptyFinalPage = Page.empty();

        try (MockedStatic<ExpenseTransactionSpecification> specStatic = mockStatic(ExpenseTransactionSpecification.class)) {
            ExpenseTransactionSpecification specMock = mock(ExpenseTransactionSpecification.class);
            Specification<FinancialTransaction> specification = mock(Specification.class);
            specStatic.when(ExpenseTransactionSpecification::of).thenReturn(specMock);
            when(specMock.filter(filter)).thenReturn(specification);

            when(repository.findAll(specification, pageable)).thenReturn(emptyPageResult);
            
            // Mock mapper for search/retrieval
            when(mapperMock.toPage(emptyPageResult, Collections.emptyList(), Collections.emptyList())).thenReturn(emptyFinalPage);

            // Act
            Page<FinancialTransactionVO> result = service.findByFilter(filter);

            // Assert
            assertThat(result).isNotNull().isEmpty();
        }
    }
}
