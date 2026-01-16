package br.com.systec.opusfinancial.financial.catalog.impl.mapper;


import br.com.systec.opusfinancial.api.vo.BankVO;
import br.com.systec.opusfinancial.financial.catalog.impl.domain.Bank;
import br.com.systec.opusfinancial.financial.catalog.impl.fake.BankFake;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BankMapperTest {

    @Test
    void whenConvertBankToBankVO() {
        Bank bankToConvert = BankFake.bankFake();

        BankVO bankConverted = BankMapper.of().toVO(bankToConvert);

        Assertions.assertThat(bankConverted).isNotNull();
        Assertions.assertThat(bankConverted.getId()).isEqualTo(bankToConvert.getId());
        Assertions.assertThat(bankConverted.getCode()).isEqualTo(bankToConvert.getCode());
        Assertions.assertThat(bankConverted.getName()).isEqualTo(bankToConvert.getName());
        Assertions.assertThat(bankConverted.getLogoUrl()).isEqualTo(bankToConvert.getLogoUrl());
        Assertions.assertThat(bankConverted.getColor()).isEqualTo(bankToConvert.getColor());

    }
}
