package br.com.systec.opusfinancial.financial.catalog.impl.service;

import br.com.systec.opusfinancial.api.exceptions.BankNotFoundException;
import br.com.systec.opusfinancial.api.filter.FilterBank;
import br.com.systec.opusfinancial.api.vo.BankVO;
import br.com.systec.opusfinancial.financial.catalog.impl.domain.Bank;
import br.com.systec.opusfinancial.financial.catalog.impl.fake.BankFake;
import br.com.systec.opusfinancial.financial.catalog.impl.repository.BankRepository;
import br.com.systec.opusfinancial.i18n.I18nTranslate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class BankServiceImplTest {

    @Mock
    private BankRepository repository;

    @InjectMocks
    private BankServiceImpl service;

    @Mock
    private ResourceBundleMessageSource messageSource; // Mock do messageSource
    @InjectMocks
    private I18nTranslate i18nTranslate;

    @Test
    void whenFindById_thenReturnBankVOSuccess() {
        Bank bankToReturn = BankFake.bankFake();

        Mockito.doReturn(Optional.of(bankToReturn)).when(repository).findById(Mockito.any(UUID.class));

        BankVO bankReturnFind = service.findById(UUID.randomUUID());

        Assertions.assertThat(bankReturnFind).isNotNull();
        Assertions.assertThat(bankReturnFind.getId()).isEqualTo(bankToReturn.getId());
        Assertions.assertThat(bankReturnFind.getCode()).isEqualTo(bankToReturn.getCode());
        Assertions.assertThat(bankReturnFind.getName()).isEqualTo(bankToReturn.getName());
        Assertions.assertThat(bankReturnFind.getLogoUrl()).isEqualTo(bankToReturn.getLogoUrl());
        Assertions.assertThat(bankReturnFind.getColor()).isEqualTo(bankToReturn.getColor());

        Mockito.verify(repository).findById(Mockito.any(UUID.class));
    }

    @Test
    void whenFindById_thenReturnBankNotFoundException() {
        Mockito.doReturn(Optional.empty()).when(repository).findById(Mockito.any(UUID.class));

        Assertions.assertThatThrownBy(() -> service.findById(UUID.randomUUID()))
                .isInstanceOf(BankNotFoundException.class);
    }

    @Test
    void whenFindByFilter_thenReturnPageBankVO() {
        FilterBank filterBank = new FilterBank("", 30, 0);

        Bank bankToReturn = BankFake.bankFake();
        Page<Bank> pageToReturn = new PageImpl<>(List.of(bankToReturn));

        Mockito.doReturn(pageToReturn).when(repository).findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));

        Page<BankVO> pageReturnFind = service.findByFilter(filterBank);

        Assertions.assertThat(pageReturnFind).isNotNull();
        Assertions.assertThat(pageReturnFind.getTotalElements()).isEqualTo(1);

        Mockito.verify(repository).findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));
    }

    @Test
    void whenFindByCode_thenReturnBankVO() {
        Bank bankToReturn = BankFake.bankFake();

        Mockito.doReturn(Optional.of(bankToReturn)).when(repository).findByCode(Mockito.anyString());

        BankVO bankReturnFind = service.findByCode("123");

        Assertions.assertThat(bankReturnFind).isNotNull();
        Assertions.assertThat(bankReturnFind.getId()).isEqualTo(bankToReturn.getId());
        Assertions.assertThat(bankReturnFind.getCode()).isEqualTo(bankToReturn.getCode());
        Assertions.assertThat(bankReturnFind.getName()).isEqualTo(bankToReturn.getName());
        Assertions.assertThat(bankReturnFind.getLogoUrl()).isEqualTo(bankToReturn.getLogoUrl());
        Assertions.assertThat(bankReturnFind.getColor()).isEqualTo(bankToReturn.getColor());

        Mockito.verify(repository).findByCode(Mockito.anyString());
    }

}
