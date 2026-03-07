package br.com.systec.opusfinancial.financial.catalog.impl.service;

import br.com.systec.opusfinancial.api.service.CategoryService;
import br.com.systec.opusfinancial.api.vo.CategoryVO;
import br.com.systec.opusfinancial.financial.api.exceptions.ExpenseFinancialNotFoundException;
import br.com.systec.opusfinancial.financial.api.filter.ExpenseTransactionFilter;
import br.com.systec.opusfinancial.financial.api.service.AccountService;
import br.com.systec.opusfinancial.financial.api.service.ExpenseTransactionService;
import br.com.systec.opusfinancial.financial.api.vo.AccountVO;
import br.com.systec.opusfinancial.financial.api.vo.CategoryTransactionType;
import br.com.systec.opusfinancial.financial.api.vo.FinancialTransactionVO;
import br.com.systec.opusfinancial.financial.api.vo.TransactionType;
import br.com.systec.opusfinancial.financial.catalog.impl.entity.FinancialTransaction;
import br.com.systec.opusfinancial.financial.catalog.impl.filter.ExpenseTransactionSpecification;
import br.com.systec.opusfinancial.financial.catalog.impl.mapper.FinancialTransactionMapper;
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

    public ExpenseTransactionServiceImpl(TransactionRepository repository, AccountService accountService, CategoryService categoryService) {
        this.repository = repository;
        this.accountService = accountService;
        this.categoryService = categoryService;
    }

    @Override
    @Transactional
    public FinancialTransactionVO create(FinancialTransactionVO financialTransaction) {
        FinancialTransaction expenseTransaction = FinancialTransactionMapper.of().toEntity(
                financialTransaction,
                TransactionType.EXPENSE,
                CategoryTransactionType.PAYMENT
        );

        FinancialTransaction expenseTransactionAfterSave = repository.save(expenseTransaction);

        if (expenseTransactionAfterSave.getProcessed() == true) {
            accountService.updateBalance(expenseTransactionAfterSave.getAccountId(), expenseTransactionAfterSave.getAmount(), TransactionType.EXPENSE);
        }


        return FinancialTransactionMapper.of().toVO(expenseTransactionAfterSave);
    }

    @Override
    @Transactional
    public void delete(UUID expenseTransactionId) {
        FinancialTransaction expenseTransaction = repository.findById(expenseTransactionId).orElseThrow(ExpenseFinancialNotFoundException::new);

        repository.delete(expenseTransaction);

        if(expenseTransaction.getProcessed() == true) {
            accountService.updateBalance(expenseTransaction.getAccountId(), expenseTransaction.getAmount(), TransactionType.INCOMING);
        }

    }

    @Override
    @Transactional(readOnly = true)
    public Page<FinancialTransactionVO> findByFilter(ExpenseTransactionFilter filter) {
        Specification<FinancialTransaction> specification = ExpenseTransactionSpecification.of().filter(filter);
        Page<FinancialTransaction> pageResult = repository.findAll(specification, filter.getPageable());

        List<UUID> listOfAccountId = pageResult.stream().map(FinancialTransaction::getAccountId)
                .filter(Objects::nonNull).distinct().toList();
        List<UUID> listOfCategoryId = pageResult.stream().map(FinancialTransaction::getCategoryId)
                .filter(Objects::nonNull)
                .distinct().toList();

        List<AccountVO> listOfAccount = accountService.findByIds(listOfAccountId);
        List<CategoryVO> listOfCategory = categoryService.findByIds(listOfCategoryId);

        return FinancialTransactionMapper.of().toPage(pageResult, listOfAccount, listOfCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public FinancialTransactionVO findById(UUID expenseId) {
        FinancialTransaction expenseTransaction = repository.findById(expenseId).orElseThrow(ExpenseFinancialNotFoundException::new);

        return FinancialTransactionMapper.of().toVO(expenseTransaction);
    }
}
