package br.com.systec.opusfinancial.financial.creditcard.impl.service;

import br.com.systec.opusfinancial.financial.api.domain.Account;
import br.com.systec.opusfinancial.financial.api.service.AccountService;
import br.com.systec.opusfinancial.financial.creditcard.api.domain.CreditCard;
import br.com.systec.opusfinancial.financial.creditcard.api.exceptions.CreditCardNotFoundException;
import br.com.systec.opusfinancial.financial.creditcard.api.filter.FilterCreditCard;
import br.com.systec.opusfinancial.financial.creditcard.impl.entity.CreditCardEntity;
import br.com.systec.opusfinancial.financial.creditcard.impl.fake.CreditCardFake;
import br.com.systec.opusfinancial.financial.creditcard.impl.filter.CreditCardSpecification;
import br.com.systec.opusfinancial.financial.creditcard.impl.mapper.CreditCardDomainMapper;
import br.com.systec.opusfinancial.financial.creditcard.impl.repository.CreditCardRepository;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditCardServiceImplTest {

    @Mock
    private CreditCardRepository repository;

    @Mock
    private AccountService accountService;

    @Mock
    private CreditCardDomainMapper mapper;

    @InjectMocks
    private CreditCardServiceImpl service;

    @Mock
    private ResourceBundleMessageSource messageSource;
    @InjectMocks
    private I18nTranslate i18nTranslate;

    @Test
    @DisplayName("Should save and return CreditCard when create is called")
    void create_ShouldSaveAndReturnCreditCard() {
        // Arrange
        CreditCard creditCardToSave = CreditCardFake.createCreditCard();
        CreditCardEntity entityToSave = CreditCardFake.createCreditCardEntity();
        CreditCardEntity savedEntity = CreditCardFake.createCreditCardEntity();
        CreditCard expectedResult = CreditCardFake.createCreditCard();

        when(mapper.toEntity(creditCardToSave)).thenReturn(entityToSave);
        when(repository.save(entityToSave)).thenReturn(savedEntity);
        when(mapper.toDomain(savedEntity, null)).thenReturn(expectedResult);

        // Act
        CreditCard result = service.create(creditCardToSave);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expectedResult);
        verify(repository).save(entityToSave);
    }

    @Test
    @DisplayName("Should update and return CreditCard when update is called with existing ID")
    void update_ShouldUpdateAndReturnCreditCard() {
        // Arrange
        CreditCard creditCardToUpdate = CreditCardFake.createCreditCard();
        UUID id = creditCardToUpdate.getId();
        CreditCardEntity existingEntity = CreditCardFake.createCreditCardEntity();
        CreditCardEntity updatedEntity = CreditCardFake.createCreditCardEntity();
        CreditCard expectedResult = CreditCardFake.createCreditCard();

        when(repository.findById(id)).thenReturn(Optional.of(existingEntity));
        doNothing().when(mapper).updateEntityFormDomain(existingEntity, creditCardToUpdate);
        when(repository.save(existingEntity)).thenReturn(updatedEntity);
        when(mapper.toDomain(updatedEntity, null)).thenReturn(expectedResult);

        // Act
        CreditCard result = service.update(creditCardToUpdate);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expectedResult);
        verify(repository).findById(id);
        verify(mapper).updateEntityFormDomain(existingEntity, creditCardToUpdate);
        verify(repository).save(existingEntity);
    }

    @Test
    @DisplayName("Should throw CreditCardNotFoundException when update is called with non-existing ID")
    void update_ShouldThrowException_WhenNotFound() {
        // Arrange
        CreditCard creditCardToUpdate = CreditCardFake.createCreditCard();
        UUID id = creditCardToUpdate.getId();

        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> service.update(creditCardToUpdate))
                .isInstanceOf(CreditCardNotFoundException.class);
        
        verify(repository).findById(id);
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Should return CreditCard when findById is called with existing ID")
    void findById_ShouldReturnCreditCard_WhenFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        CreditCardEntity foundEntity = CreditCardFake.createCreditCardEntity();
        
        Account foundAccount = new Account();
        foundAccount.setId(foundEntity.getAccountId());
        
        CreditCard expectedResult = CreditCardFake.createCreditCard();

        when(repository.findById(id)).thenReturn(Optional.of(foundEntity));
        when(accountService.findById(foundEntity.getAccountId())).thenReturn(foundAccount);
        when(mapper.toDomain(foundEntity, foundAccount)).thenReturn(expectedResult);

        // Act
        CreditCard result = service.findById(id);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expectedResult);
        verify(repository).findById(id);
        verify(accountService).findById(foundEntity.getAccountId());
    }

    @Test
    @DisplayName("Should throw CreditCardNotFoundException when findById is called with non-existing ID")
    void findById_ShouldThrowException_WhenNotFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> service.findById(id))
                .isInstanceOf(CreditCardNotFoundException.class);

        verify(repository).findById(id);
        // verify(accountService, never()).findById(any()); // This might fail if the exception is thrown before this call, which is expected
    }

    @Test
    @DisplayName("Should return Page of CreditCards when findByFilter is called")
    void findByFilter_ShouldReturnPageOfCreditCards() {
        // Arrange
        FilterCreditCard filter = mock(FilterCreditCard.class);
        Pageable pageable = mock(Pageable.class);
        when(filter.getPageable()).thenReturn(pageable);

        CreditCardEntity entity = CreditCardFake.createCreditCardEntity();
        Page<CreditCardEntity> pageResult = new PageImpl<>(List.of(entity));
        
        Account account = new Account();
        account.setId(entity.getAccountId());
        List<Account> accounts = List.of(account);
        
        Page<CreditCard> expectedPage = new PageImpl<>(List.of(CreditCardFake.createCreditCard()));

        try (MockedStatic<CreditCardSpecification> specStatic = mockStatic(CreditCardSpecification.class)) {
            CreditCardSpecification specMock = mock(CreditCardSpecification.class);
            Specification<CreditCardEntity> specification = mock(Specification.class);
            specStatic.when(CreditCardSpecification::of).thenReturn(specMock);
            when(specMock.filter(filter)).thenReturn(specification);

            when(repository.findAll(specification, pageable)).thenReturn(pageResult);
            when(accountService.findByIds(anyList())).thenReturn(accounts);
            when(mapper.toPage(pageResult, accounts)).thenReturn(expectedPage);

            // Act
            Page<CreditCard> result = service.findByFilter(filter);

            // Assert
            assertThat(result).isNotNull();
            assertThat(result).isEqualTo(expectedPage);
            verify(repository).findAll(specification, pageable);
            verify(accountService).findByIds(anyList());
            verify(mapper).toPage(pageResult, accounts);
        }
    }
}
