package br.com.systec.opusfinancial.financial.catalog.impl.mapper;

import br.com.systec.opusfinancial.api.domain.Bank;
import br.com.systec.opusfinancial.financial.catalog.impl.entity.BankEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public class BankMapper {

    private BankMapper() {}

    public static BankMapper of() {
        return new BankMapper();
    }

    public Bank toVO(BankEntity bank) {
        Bank bankConverted = new Bank();
        bankConverted.setId(bank.getId());
        bankConverted.setCode(bank.getCode());
        bankConverted.setName(bank.getName());
        bankConverted.setLogoUrl(bank.getLogoUrl());

        return bankConverted;
    }

    public Page<Bank> toPageVO(Page<BankEntity> result) {
        return result.map(this::toVO);
    }

    public List<Bank> toList(List<BankEntity> results) {
        return results.stream().map(this::toVO).toList();
    }
}
