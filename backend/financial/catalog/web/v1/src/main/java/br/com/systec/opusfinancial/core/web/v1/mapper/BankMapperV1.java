package br.com.systec.opusfinancial.core.web.v1.mapper;

import br.com.systec.opusfinancial.api.vo.BankVO;
import br.com.systec.opusfinancial.core.web.v1.dto.BankFindResponseDTO;
import org.springframework.data.domain.Page;

public class BankMapperV1 {

    private BankMapperV1() {}

    public static BankMapperV1 of() {
        return new BankMapperV1();
    }

    public BankFindResponseDTO toFindResponse(BankVO bankVO) {
        BankFindResponseDTO bankFindResponseDTO = new BankFindResponseDTO();
        bankFindResponseDTO.setId(bankVO.getId());
        bankFindResponseDTO.setCode(bankVO.getCode());
        bankFindResponseDTO.setName(bankVO.getName());
        bankFindResponseDTO.setLogoUrl(bankVO.getLogoUrl());

        return bankFindResponseDTO;
    }

    public Page<BankFindResponseDTO> toPageFindResponse(Page<BankVO> result) {
        return result.map(this::toFindResponse);
    }
}
