package br.com.systec.opusfinancial.financial.catalog.impl.mapper;

import br.com.systec.opusfinancial.api.domain.Category;
import br.com.systec.opusfinancial.financial.api.domain.Account;
import br.com.systec.opusfinancial.financial.api.domain.CategoryTransactionType;
import br.com.systec.opusfinancial.financial.api.domain.FinancialTransaction;
import br.com.systec.opusfinancial.financial.api.domain.TransactionType;
import br.com.systec.opusfinancial.financial.catalog.impl.entity.FinancialTransactionEntity;
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
public interface FinancialTransactionDomainMapper {

    FinancialTransactionDomainMapper INSTANCE = Mappers.getMapper(FinancialTransactionDomainMapper.class);

    @Mapping(target = "transactionType", source = "transactionType")
    @Mapping(target = "categoryTransactionType", source = "categoryTransactionType")
    @Mapping(target = "accountId", source = "transaction.account.id")
    @Mapping(target = "categoryId", source = "transaction.category.id")
    @Mapping(target = "id", source = "transaction.id")
    @Mapping(target = "description", source = "transaction.description")
    @Mapping(target = "processed", source = "transaction.processed")
    @Mapping(target = "processedAt", source = "transaction.processedAt")
    @Mapping(target = "dueDate", source = "transaction.dueDate")
    @Mapping(target = "amount", source = "transaction.amount")
    @Mapping(target = "paymentAt", source = "transaction.paymentAt")
    FinancialTransactionEntity toEntity(FinancialTransaction transaction,
                                        TransactionType transactionType,
                                        CategoryTransactionType categoryTransactionType
    );

    @Mapping(target = "account.id", source = "accountId")
    @Mapping(target = "category.id", source = "categoryId")
    FinancialTransaction toVO(FinancialTransactionEntity transaction);

    @Mapping(target = "accountId", source = "transaction.account.id")
    @Mapping(target = "categoryId", source = "transaction.category.id")
    @Mapping(target = "description", source = "transaction.description")
    @Mapping(target = "processed", source = "transaction.processed")
    @Mapping(target = "processedAt", source = "transaction.processedAt")
    @Mapping(target = "dueDate", source = "transaction.dueDate")
    @Mapping(target = "amount", source = "transaction.amount")
    @Mapping(target = "paymentAt", source = "transaction.paymentAt")
    void updateEntityFromVO(@MappingTarget FinancialTransactionEntity transactionEntity,
                            FinancialTransaction transaction
    );

    default Page<FinancialTransaction> toPage(Page<FinancialTransactionEntity> pageResult, List<Account> listOfAccount,
                                             List<Category> listOfCategory) {
        Map<UUID, Account> accountMap = listOfAccount.stream()
                .filter(item -> item.getId() != null)
                .collect(Collectors.toMap(Account::getId, Function.identity(), (existing, replacement) -> existing));
        Map<UUID, Category> categoryMap = listOfCategory.stream()
                .filter(item -> item.getId() != null)
                .collect(Collectors.toMap(Category::getId, Function.identity(), (existing, replacement) -> existing));

        return pageResult.map(item -> {
            Account account = accountMap.get(item.getAccountId());
            Category categoryVO = categoryMap.get(item.getCategoryId());

            FinancialTransaction financialTransactionVO = toVO(item);
            if (account != null) {
                financialTransactionVO.setAccount(account);
            }
            if (categoryVO != null) {
                financialTransactionVO.setCategory(categoryVO);
            }

            return financialTransactionVO;
        });
    }
}
