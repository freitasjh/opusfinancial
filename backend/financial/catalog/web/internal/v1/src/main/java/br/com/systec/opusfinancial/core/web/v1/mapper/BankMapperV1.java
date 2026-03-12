package br.com.systec.opusfinancial.core.web.v1.mapper;

import br.com.systec.opusfinancial.api.domain.Bank;
import br.com.systec.opusfinancial.core.web.v1.dto.BankFindResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public class BankMapperV1 {

    private BankMapperV1() {}

    public static BankMapperV1 of() {
        return new BankMapperV1();
    }

    public BankFindResponseDTO toFindResponse(Bank bankVO) {
        BankFindResponseDTO bankFindResponseDTO = new BankFindResponseDTO();
        bankFindResponseDTO.setId(bankVO.getId());
        bankFindResponseDTO.setCode(bankVO.getCode());
        bankFindResponseDTO.setName(bankVO.getName());
        bankFindResponseDTO.setLogoUrl(bankVO.getLogoUrl());

        return bankFindResponseDTO;
    }

    public Page<BankFindResponseDTO> toPageFindResponse(Page<Bank> result) {
        return result.map(this::toFindResponse);
    }

    public List<BankFindResponseDTO> toList(List<Bank> results) {
        return results.stream().map(this::toFindResponse).toList();
    }
}
