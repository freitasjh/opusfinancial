package br.com.systec.opusfinancial.financial.catalog.impl.mapper;

import br.com.systec.opusfinancial.api.vo.BankVO;
import br.com.systec.opusfinancial.financial.catalog.impl.domain.Bank;
import org.springframework.data.domain.Page;

public class BankMapper {

    private BankMapper() {}

    public static BankMapper of() {
        return new BankMapper();
    }

    public BankVO toVO(Bank bank) {
        BankVO bankConverted = new BankVO();
        bankConverted.setId(bank.getId());
        bankConverted.setCode(bank.getCode());
        bankConverted.setName(bank.getName());
        bankConverted.setLogoUrl(bank.getLogoUrl());
        bankConverted.setColor(bank.getColor());

        return bankConverted;
    }

    public Page<BankVO> toPageVO(Page<Bank> result) {
        return result.map(this::toVO);
    }
}
