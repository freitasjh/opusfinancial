package br.com.systec.opusfinancial.financial.catalog.impl.mapper;

import br.com.systec.opusfinancial.api.vo.BankVO;
import br.com.systec.opusfinancial.financial.catalog.impl.domain.Bank;
import org.springframework.data.domain.Page;

import java.util.List;

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

        return bankConverted;
    }

    public Page<BankVO> toPageVO(Page<Bank> result) {
        return result.map(this::toVO);
    }

    public List<BankVO> toList(List<Bank> results) {
        return results.stream().map(this::toVO).toList();
    }
}
