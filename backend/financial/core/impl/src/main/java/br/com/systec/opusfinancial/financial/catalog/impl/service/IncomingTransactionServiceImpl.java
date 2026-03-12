package br.com.systec.opusfinancial.financial.catalog.impl.service;

import br.com.systec.opusfinancial.api.domain.Category;
import br.com.systec.opusfinancial.api.service.CategoryService;
import br.com.systec.opusfinancial.financial.api.domain.Account;
import br.com.systec.opusfinancial.financial.api.domain.CategoryTransactionType;
import br.com.systec.opusfinancial.financial.api.domain.FinancialTransaction;
import br.com.systec.opusfinancial.financial.api.domain.TransactionType;
import br.com.systec.opusfinancial.financial.api.exceptions.IncomingFinancialNotFoundException;
import br.com.systec.opusfinancial.financial.api.filter.IncomingTransactionFilter;
import br.com.systec.opusfinancial.financial.api.service.AccountService;
import br.com.systec.opusfinancial.financial.api.service.IncomingTransactionService;
import br.com.systec.opusfinancial.financial.catalog.impl.entity.FinancialTransactionEntity;
import br.com.systec.opusfinancial.financial.catalog.impl.filter.IncomingTransactionSpecification;
import br.com.systec.opusfinancial.financial.catalog.impl.mapper.FinancialTransactionDomainMapper;
import br.com.systec.opusfinancial.financial.catalog.impl.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class IncomingTransactionServiceImpl implements IncomingTransactionService {

    private final TransactionRepository repository;
    private final AccountService accountService;
    private final CategoryService categoryService;

    public IncomingTransactionServiceImpl(TransactionRepository repository, AccountService accountService, CategoryService categoryService) {
        this.repository = repository;
        this.accountService = accountService;
        this.categoryService = categoryService;
    }
    @Override
    @Transactional
    public FinancialTransaction save(FinancialTransaction transaction) {
        FinancialTransactionEntity transactionBeforeSave = FinancialTransactionDomainMapper.INSTANCE.toEntity(
                transaction,
                TransactionType.INCOMING,
                CategoryTransactionType.RECEIVER
        );
        FinancialTransactionEntity transactionAfterSave = repository.save(transactionBeforeSave);

        accountService.updateBalance(transactionAfterSave.getAccountId(), transactionAfterSave.getAmount(), transactionAfterSave.getTransactionType());

        return FinancialTransactionDomainMapper.INSTANCE.toVO(transactionAfterSave);
    }

    @Override
    @Transactional
    public FinancialTransaction update(FinancialTransaction transaction) {
        FinancialTransactionEntity financialTransactionSaved = repository.findById(transaction.getId())
                .orElseThrow(IncomingFinancialNotFoundException::new);

        FinancialTransactionDomainMapper.INSTANCE.updateEntityFromVO(financialTransactionSaved, transaction);
        FinancialTransactionEntity financialTransactionAfterSave = repository.save(financialTransactionSaved);

        return FinancialTransactionDomainMapper.INSTANCE.toVO(financialTransactionAfterSave);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FinancialTransaction> findByFilter(IncomingTransactionFilter filter) {
        Specification<FinancialTransactionEntity> specification = IncomingTransactionSpecification.of().filter(filter);
        Page<FinancialTransactionEntity> pageResult = repository.findAll(specification, filter.getPageable());

        List<UUID> listOfAccountId = pageResult.stream().map(FinancialTransactionEntity::getAccountId)
                .filter(Objects::nonNull).distinct().toList();
        List<UUID> listOfCategoryId = pageResult.stream().map(FinancialTransactionEntity::getCategoryId)
                .filter(Objects::nonNull)
                .distinct().toList();

        List<Account> listOfAccount = accountService.findByIds(listOfAccountId);
        List<Category> listOfCategory = categoryService.findByIds(listOfCategoryId);

        return FinancialTransactionDomainMapper.INSTANCE.toPage(pageResult, listOfAccount, listOfCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public FinancialTransaction findById(UUID id) {
        FinancialTransactionEntity financialTransaction = repository.findById(id).orElseThrow(IncomingFinancialNotFoundException::new);

        return FinancialTransactionDomainMapper.INSTANCE.toVO(financialTransaction);
    }
}
