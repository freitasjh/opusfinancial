package br.com.systec.opusfinancial.financial.catalog.impl.service;

import br.com.systec.opusfinancial.financial.api.exceptions.IncomingFinancialNotFoundException;
import br.com.systec.opusfinancial.financial.api.filter.IncomingTransactionFilter;
import br.com.systec.opusfinancial.financial.api.service.AccountService;
import br.com.systec.opusfinancial.financial.api.vo.AccountVO;
import br.com.systec.opusfinancial.financial.api.vo.FinancialTransactionVO;
import br.com.systec.opusfinancial.financial.catalog.impl.domain.FinancialTransaction;
import br.com.systec.opusfinancial.financial.catalog.impl.fake.FinancialTransactionFake;
import br.com.systec.opusfinancial.financial.catalog.impl.filter.IncomingTransactionSpecification;
import br.com.systec.opusfinancial.financial.catalog.impl.mapper.IncomingTransactionMapper;
import br.com.systec.opusfinancial.financial.catalog.impl.repository.TransactionRepository;
import br.com.systec.opusfinancial.i18n.I18nTranslate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IncomingTransactionServiceImplTest {

    @Mock
    private TransactionRepository repository;

    @Mock
    private AccountService accountService;

    @Mock
    private ResourceBundleMessageSource messageSource;
    @InjectMocks
    private I18nTranslate i18nTranslate;

    @InjectMocks
    private IncomingTransactionServiceImpl service;

    private FinancialTransactionVO inputVO;
    private FinancialTransaction entityBeforeSave;
    private FinancialTransaction entityAfterSave;
    private FinancialTransactionVO outputVO;
    private IncomingTransactionMapper mapperMock;
    private MockedStatic<IncomingTransactionMapper> mapperStatic;

    @BeforeEach
    void setUp() {
        inputVO = mock(FinancialTransactionVO.class);
        entityBeforeSave = mock(FinancialTransaction.class);
        entityAfterSave = mock(FinancialTransaction.class);
        outputVO = mock(FinancialTransactionVO.class);

        mapperStatic = mockStatic(IncomingTransactionMapper.class);
        mapperMock = mock(IncomingTransactionMapper.class);
        mapperStatic.when(IncomingTransactionMapper::of).thenReturn(mapperMock);
    }

    @AfterEach
    void tearDown() {
        mapperStatic.close();
    }

    @Test
    @DisplayName("Should save transaction successfully")
    void save_shouldPersistTransaction() {
        // Arrange
        when(mapperMock.toEntity(inputVO)).thenReturn(entityBeforeSave);
        when(repository.save(entityBeforeSave)).thenReturn(entityAfterSave);
        when(mapperMock.toVO(entityAfterSave)).thenReturn(outputVO);

        // Act
        var result = service.save(inputVO);

        // Assert
        assertThat(result).isNotNull().isEqualTo(outputVO);
        verify(repository).save(entityBeforeSave);
    }

    @Test
    @DisplayName("Should update transaction successfully")
    @Disabled
    void update_shouldUpdateTransaction() {
        FinancialTransactionVO financialTransactionToUpdate = FinancialTransactionFake.toFakeVO();
        FinancialTransaction financialTransactionFindToReturn = FinancialTransactionFake.toFake();

        doReturn(financialTransactionFindToReturn).when(repository).findById(any());

    }

    @Test
    @DisplayName("Should find transaction by ID when exists")
    void findById_shouldReturnTransaction_whenExists() {
        // Arrange
        var id = UUID.randomUUID();
        var entity = mock(FinancialTransaction.class);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapperMock.toVO(entity)).thenReturn(outputVO);

        // Act
        var result = service.findById(id);

        // Assert
        assertThat(result).isEqualTo(outputVO);
    }

    @Test
    @DisplayName("Should throw exception when transaction ID not found")
    void findById_shouldThrowException_whenNotFound() {
        // Arrange
        var id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> service.findById(id))
                .isInstanceOf(IncomingFinancialNotFoundException.class);

        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Should find transactions by filter and enrich with account data")
    void findByFilter_shouldReturnPage() {
        // Arrange
        var filter = mock(IncomingTransactionFilter.class);
        var pageable = mock(Pageable.class);
        when(filter.getPageable()).thenReturn(pageable);

        Specification<FinancialTransaction> spec = mock(Specification.class);

        var transactionEntity = mock(FinancialTransaction.class);
        var accountId = UUID.randomUUID();
        when(transactionEntity.getAccountId()).thenReturn(accountId);

        Page<FinancialTransaction> pageResult = new PageImpl<>(List.of(transactionEntity));

        var accountVO = mock(AccountVO.class);
        List<AccountVO> accounts = List.of(accountVO);

        @SuppressWarnings("unchecked")
        Page<FinancialTransactionVO> outputPage = mock(Page.class);

        try (MockedStatic<IncomingTransactionSpecification> specStatic = mockStatic(IncomingTransactionSpecification.class)) {
            var specFactory = mock(IncomingTransactionSpecification.class);
            specStatic.when(IncomingTransactionSpecification::of).thenReturn(specFactory);
            when(specFactory.filter(filter)).thenReturn(spec);

            when(repository.findAll(spec, pageable)).thenReturn(pageResult);
            when(accountService.findByIds(List.of(accountId))).thenReturn(accounts);
            when(mapperMock.toPage(pageResult, accounts)).thenReturn(outputPage);

            // Act
            var result = service.findByFilter(filter);

            // Assert
            assertThat(result).isEqualTo(outputPage);
            verify(accountService).findByIds(List.of(accountId));
        }
    }
}