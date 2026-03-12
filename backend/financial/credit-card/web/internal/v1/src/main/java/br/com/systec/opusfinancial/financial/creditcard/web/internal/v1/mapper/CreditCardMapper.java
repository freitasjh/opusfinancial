package br.com.systec.opusfinancial.financial.creditcard.web.internal.v1.mapper;

import br.com.systec.opusfinancial.financial.creditcard.api.domain.CreditCard;
import br.com.systec.opusfinancial.financial.creditcard.web.internal.v1.dto.CreditCardInputDTO;
import br.com.systec.opusfinancial.financial.creditcard.web.internal.v1.dto.CreditCardSaveResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CreditCardMapper {

    CreditCardMapper INSTANCE = Mappers.getMapper(CreditCardMapper.class);

    @Mapping(source = "accountID", target = "account.id")
    CreditCard toDomain(CreditCardInputDTO inputDTO);

    CreditCardSaveResponseDTO toResponse(CreditCard creditCard);
}
