package br.com.systec.opusfinancial.financial.creditcard.impl.mapper;

import br.com.systec.opusfinancial.financial.api.domain.Account;
import br.com.systec.opusfinancial.financial.creditcard.api.domain.CreditCard;
import br.com.systec.opusfinancial.financial.creditcard.impl.entity.CreditCardEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CreditCardDomainMapper {

    CreditCardDomainMapper INSTANCE = Mappers.getMapper(CreditCardDomainMapper.class);

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    CreditCardEntity toEntity(CreditCard creditCard);

    @Mapping(target = "account", expression = "java(mapAccount(entity, account))")
    CreditCard toDomain(CreditCardEntity entity, @Context Account account);

    @Mapping(target = "accountId", source = "creditCard.account.id")
    @Mapping(target = "name", source = "creditCard.name")
    @Mapping(target = "number", source = "creditCard.number", conditionExpression = "java(br.com.systec.opusfinancial.commons.api.utils.MaskUtil.isNotMasked(creditCard.getNumber()))")
    @Mapping(target = "cvv", source = "creditCard.cvv", conditionExpression = "java(br.com.systec.opusfinancial.commons.api.utils.MaskUtil.isNotMasked(creditCard.getCvv()))")
    @Mapping(target = "limit", source = "creditCard.limit")
    @Mapping(target = "availableLimit", source = "creditCard.availableLimit")
    @Mapping(target = "status", source = "creditCard.status")
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    void updateEntityFormDomain(@MappingTarget CreditCardEntity entity, CreditCard creditCard);

    default Account mapAccount(CreditCardEntity entity, Account account) {
        if (account != null) {
            return account;
        }
        Account newAccount = new Account();
        newAccount.setId(entity.getAccountId());
        return newAccount;
    }

    default Page<CreditCard> toPage(Page<CreditCardEntity> pageResult, List<Account> listOfAccount) {
        Map<UUID, Account> accountMap = listOfAccount.stream()
                .filter(item -> item.getId() != null)
                .collect(Collectors.toMap(Account::getId, Function.identity(), (existing, replacement) -> existing));

        return pageResult.map(item -> {
            Account account = accountMap.get(item.getAccountId());
            return toDomain(item, account);
        });
    }
}
