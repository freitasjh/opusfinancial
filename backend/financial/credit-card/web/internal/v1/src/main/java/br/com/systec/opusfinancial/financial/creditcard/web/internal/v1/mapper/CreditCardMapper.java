package br.com.systec.opusfinancial.financial.creditcard.web.internal.v1.mapper;

import br.com.systec.opusfinancial.financial.creditcard.api.domain.CreditCard;
import br.com.systec.opusfinancial.financial.creditcard.web.internal.v1.dto.CreditCardInfoDTO;
import br.com.systec.opusfinancial.financial.creditcard.web.internal.v1.dto.CreditCardInputDTO;
import br.com.systec.opusfinancial.financial.creditcard.web.internal.v1.dto.CreditCardSaveResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface CreditCardMapper {

    CreditCardMapper INSTANCE = Mappers.getMapper(CreditCardMapper.class);

    @Mapping(source = "accountId", target = "account.id")
    CreditCard toDomain(CreditCardInputDTO inputDTO);

    CreditCardSaveResponseDTO toResponse(CreditCard creditCard);

    @Mapping(source = "account.accountName", target = "account")
    CreditCardInfoDTO toInfoDTO(CreditCard creditCard);

    @Mapping(source = "account.id", target = "accountId")
    CreditCardInputDTO toInputDTO(CreditCard creditCard);

    default Page<CreditCardInfoDTO> toPage(Page<CreditCard> pageResult) {
        return pageResult.map(this::toInfoDTO);
    }
}
