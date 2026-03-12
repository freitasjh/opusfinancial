package br.com.systec.opusfinancial.financial.catalog.impl.mapper;


import br.com.systec.opusfinancial.api.domain.Bank;
import br.com.systec.opusfinancial.financial.catalog.impl.entity.BankEntity;
import br.com.systec.opusfinancial.financial.catalog.impl.fake.BankFake;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BankMapperTest {

    @Test
    void whenConvertBankToBankVO() {
        BankEntity bankToConvert = BankFake.bankFake();

        Bank bankConverted = BankMapper.of().toVO(bankToConvert);

        Assertions.assertThat(bankConverted).isNotNull();
        Assertions.assertThat(bankConverted.getId()).isEqualTo(bankToConvert.getId());
        Assertions.assertThat(bankConverted.getCode()).isEqualTo(bankToConvert.getCode());
        Assertions.assertThat(bankConverted.getName()).isEqualTo(bankToConvert.getName());
        Assertions.assertThat(bankConverted.getLogoUrl()).isEqualTo(bankToConvert.getLogoUrl());

    }
}
