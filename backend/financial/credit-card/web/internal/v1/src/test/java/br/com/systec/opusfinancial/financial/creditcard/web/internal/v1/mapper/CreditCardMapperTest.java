package br.com.systec.opusfinancial.financial.creditcard.web.internal.v1.mapper;

import br.com.systec.opusfinancial.financial.creditcard.api.domain.CreditCard;
import br.com.systec.opusfinancial.financial.creditcard.web.internal.v1.dto.CreditCardInputDTO;
import br.com.systec.opusfinancial.financial.creditcard.web.internal.v1.dto.CreditCardSaveResponseDTO;
import br.com.systec.opusfinancial.financial.creditcard.web.internal.v1.fake.CreditCardFake;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CreditCardMapperTest {

    private final CreditCardMapper mapper = CreditCardMapper.INSTANCE;

    @Test
    void shouldMapInputDTOToDomain() {
        CreditCardInputDTO inputDTO = CreditCardFake.createInputDTO();

        CreditCard result = mapper.toDomain(inputDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(inputDTO.id(), result.getId());
        Assertions.assertEquals(inputDTO.name(), result.getName());
        Assertions.assertEquals(inputDTO.number(), result.getNumber());
        Assertions.assertEquals(inputDTO.cvc(), result.getCvv());
        Assertions.assertEquals(inputDTO.nameCreditCard(), result.getNameCreditCard());
        Assertions.assertEquals(inputDTO.totalLimit(), result.getLimit());
        Assertions.assertEquals(inputDTO.availableLimit(), result.getAvailableLimit());
        Assertions.assertEquals(inputDTO.dueDay(), result.getDueDay());
        Assertions.assertEquals(inputDTO.closingDate(), result.getClosingDate());
        Assertions.assertEquals(inputDTO.brand(), result.getBrand());
        Assertions.assertEquals(inputDTO.status(), result.getStatus());
        
        Assertions.assertNotNull(result.getAccount());
        Assertions.assertEquals(inputDTO.accountID(), result.getAccount().getId());
    }

    @Test
    void shouldMapInputDTOToDomainWithNullAccount() {
        CreditCardInputDTO baseDTO = CreditCardFake.createInputDTO();
        CreditCardInputDTO inputDTO = new CreditCardInputDTO(
                baseDTO.id(),
                baseDTO.name(),
                baseDTO.number(),
                baseDTO.cvc(),
                baseDTO.nameCreditCard(),
                baseDTO.totalLimit(),
                baseDTO.availableLimit(),
                baseDTO.dueDay(),
                baseDTO.closingDate(),
                baseDTO.brand(),
                baseDTO.status(),
                null
        );

        CreditCard result = mapper.toDomain(inputDTO);

        Assertions.assertNotNull(result);
        if (result.getAccount() != null) {
            Assertions.assertNull(result.getAccount().getId());
        } else {
             Assertions.assertNull(result.getAccount());
        }
    }

    @Test
    void shouldMapCreditCardToResponseSaveDTO() {
        CreditCard creditCard = CreditCardFake.creditCard();

        CreditCardSaveResponseDTO response = mapper.toResponse(creditCard);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(creditCard.getId(), response.id());
    }
}
