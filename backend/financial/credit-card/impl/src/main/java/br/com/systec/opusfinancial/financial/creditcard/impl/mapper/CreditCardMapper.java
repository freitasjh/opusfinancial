package br.com.systec.opusfinancial.financial.creditcard.impl.mapper;

import br.com.systec.opusfinancial.financial.api.vo.AccountVO;
import br.com.systec.opusfinancial.financial.creditcard.api.domain.CreditCard;
import br.com.systec.opusfinancial.financial.creditcard.impl.entity.CreditCardEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CreditCardMapper {

    private CreditCardMapper() {}

    public static CreditCardMapper of() {
        return new CreditCardMapper();
    }

    public CreditCardEntity toEntity(CreditCard vo) {
        CreditCardEntity creditCard = new CreditCardEntity();
        creditCard.setId(vo.getId());
        creditCard.setName(vo.getName());
        creditCard.setNameCreditCard(vo.getNameCreditCard());
        creditCard.setBrand(vo.getBrand());
        creditCard.setNumber(vo.getNumber());
        creditCard.setCvc(vo.getCvc());
        creditCard.setAvailableLimit(vo.getAvailableLimit());
        creditCard.setAccountId(vo.getAccount().getId());
        creditCard.setStatus(vo.getStatus());

        return creditCard;
    }

    public CreditCard toVO(CreditCardEntity entity, AccountVO account) {
        CreditCard creditCardVO = new CreditCard();
        creditCardVO.setId(entity.getId());
        creditCardVO.setName(entity.getName());
        creditCardVO.setNameCreditCard(entity.getNameCreditCard());
        creditCardVO.setBrand(entity.getBrand());
        creditCardVO.setNumber(entity.getNumber());
        creditCardVO.setCvc(entity.getCvc());
        creditCardVO.setAvailableLimit(entity.getAvailableLimit());

        if (account == null) {
            creditCardVO.setAccount(new AccountVO());
            creditCardVO.getAccount().setId(entity.getAccountId());
        } else {
            creditCardVO.setAccount(account);
        }

        creditCardVO.setStatus(entity.getStatus());

        return creditCardVO;
    }

    public Page<CreditCard> toPage(Page<CreditCardEntity> pageResult, List<AccountVO> listOfAccount) {
        Map<UUID, AccountVO> accountMap = listOfAccount.stream()
                .filter(item -> item.getId() != null)
                .collect(Collectors.toMap(AccountVO::getId, Function.identity(), (existing, replacement) -> existing));

        return pageResult.map(item -> {
            AccountVO account = accountMap.get(item.getAccountId());
            return toVO(item, account);
        });
    }
}
