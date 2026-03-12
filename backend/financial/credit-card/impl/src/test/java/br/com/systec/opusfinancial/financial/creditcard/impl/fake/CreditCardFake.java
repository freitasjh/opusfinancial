package br.com.systec.opusfinancial.financial.creditcard.impl.fake;

import br.com.systec.opusfinancial.financial.api.domain.Account;
import br.com.systec.opusfinancial.financial.creditcard.api.domain.BrandType;
import br.com.systec.opusfinancial.financial.creditcard.api.domain.CreditCard;
import br.com.systec.opusfinancial.financial.creditcard.api.domain.CreditCardStatusType;
import br.com.systec.opusfinancial.financial.creditcard.impl.entity.CreditCardEntity;

import java.math.BigDecimal;
import java.util.UUID;

public class CreditCardFake {

    public static CreditCard createCreditCard() {
        CreditCard creditCard = new CreditCard();
        creditCard.setId(UUID.randomUUID());
        creditCard.setName("My Platinum Card");
        creditCard.setNumber("1234-5678-9012-3456");
        creditCard.setCvv("123");
        creditCard.setNameCreditCard("JOHN DOE");
        creditCard.setLimit(BigDecimal.valueOf(5000.00));
        creditCard.setAvailableLimit(BigDecimal.valueOf(2500.00));
        creditCard.setDueDay("10");
        creditCard.setClosingDate("03");
        creditCard.setBrand(BrandType.MASTERCARD);
        creditCard.setStatus(CreditCardStatusType.ACTIVE);
        
        Account account = new Account();
        account.setId(UUID.randomUUID());
        creditCard.setAccount(account);
        
        return creditCard;
    }

    public static CreditCardEntity createCreditCardEntity() {
        CreditCardEntity entity = new CreditCardEntity();
        entity.setId(UUID.randomUUID());
        entity.setName("My Platinum Card");
        entity.setNumber("1234-5678-9012-3456");
        entity.setCvv("123");
        entity.setNameCreditCard("JOHN DOE");
        entity.setLimit(BigDecimal.valueOf(5000.00));
        entity.setAvailableLimit(BigDecimal.valueOf(2500.00));
        entity.setDueDay("10");
        entity.setClosingDate("03");
        entity.setBrand(BrandType.MASTERCARD);
        entity.setStatus(CreditCardStatusType.ACTIVE);
        entity.setAccountId(UUID.randomUUID());
        
        return entity;
    }
}
