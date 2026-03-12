package br.com.systec.opusfinancial.financial.catalog.impl.service;

import br.com.systec.opusfinancial.api.service.CategoryService;
import br.com.systec.opusfinancial.api.domain.Category;
import br.com.systec.opusfinancial.financial.api.exceptions.ExpenseFinancialNotFoundException;
import br.com.systec.opusfinancial.financial.api.filter.ExpenseTransactionFilter;
import br.com.systec.opusfinancial.financial.api.service.AccountService;
import br.com.systec.opusfinancial.financial.api.service.ExpenseTransactionService;
import br.com.systec.opusfinancial.financial.api.domain.Account;
import br.com.systec.opusfinancial.financial.api.domain.CategoryTransactionType;
import br.com.systec.opusfinancial.financial.api.domain.FinancialTransaction;
import br.com.systec.opusfinancial.financial.api.domain.TransactionType;
import br.com.systec.opusfinancial.financial.catalog.impl.entity.FinancialTransactionEntity;
import br.com.systec.opusfinancial.financial.catalog.impl.filter.ExpenseTransactionSpecification;
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
public class ExpenseTransactionServiceImpl implements ExpenseTransactionService {

    private final TransactionRepository repository;
    private final AccountService accountService;
    private final CategoryService categoryService;
    private final FinancialTransactionDomainMapper mapper;

    public ExpenseTransactionServiceImpl(TransactionRepository repository, AccountService accountService,
                                         CategoryService categoryService, FinancialTransactionDomainMapper mapper) {
        this.repository = repository;
        this.accountService = accountService;
        this.categoryService = categoryService;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public FinancialTransaction create(FinancialTransaction financialTransaction) {
        FinancialTransactionEntity expenseTransaction = mapper.toEntity(
                financialTransaction,
                TransactionType.EXPENSE,
                CategoryTransactionType.PAYMENT
        );

        FinancialTransactionEntity expenseTransactionAfterSave = repository.save(expenseTransaction);

        if (expenseTransactionAfterSave.getProcessed() == true) {
            accountService.updateBalance(expenseTransactionAfterSave.getAccountId(), expenseTransactionAfterSave.getAmount(), TransactionType.EXPENSE);
        }


        return mapper.toVO(expenseTransactionAfterSave);
    }

    @Override
    @Transactional
    public void delete(UUID expenseTransactionId) {
        FinancialTransactionEntity expenseTransaction = repository.findById(expenseTransactionId).orElseThrow(ExpenseFinancialNotFoundException::new);

        repository.delete(expenseTransaction);

        if(expenseTransaction.getProcessed() == true) {
            accountService.updateBalance(expenseTransaction.getAccountId(), expenseTransaction.getAmount(), TransactionType.INCOMING);
        }

    }

    @Override
    @Transactional(readOnly = true)
    public Page<FinancialTransaction> findByFilter(ExpenseTransactionFilter filter) {
        Specification<FinancialTransactionEntity> specification = ExpenseTransactionSpecification.of().filter(filter);
        Page<FinancialTransactionEntity> pageResult = repository.findAll(specification, filter.getPageable());

        List<UUID> listOfAccountId = pageResult.stream().map(FinancialTransactionEntity::getAccountId)
                .filter(Objects::nonNull).distinct().toList();
        List<UUID> listOfCategoryId = pageResult.stream().map(FinancialTransactionEntity::getCategoryId)
                .filter(Objects::nonNull)
                .distinct().toList();

        List<Account> listOfAccount = accountService.findByIds(listOfAccountId);
        List<Category> listOfCategory = categoryService.findByIds(listOfCategoryId);

        return mapper.toPage(pageResult, listOfAccount, listOfCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public FinancialTransaction findById(UUID expenseId) {
        FinancialTransactionEntity expenseTransaction = repository.findById(expenseId).orElseThrow(ExpenseFinancialNotFoundException::new);

        return mapper.toVO(expenseTransaction);
    }
}
