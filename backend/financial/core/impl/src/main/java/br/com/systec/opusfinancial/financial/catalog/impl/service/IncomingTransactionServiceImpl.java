package br.com.systec.opusfinancial.financial.catalog.impl.service;

import br.com.systec.opusfinancial.api.service.CategoryService;
import br.com.systec.opusfinancial.api.vo.CategoryVO;
import br.com.systec.opusfinancial.financial.api.exceptions.IncomingFinancialNotFoundException;
import br.com.systec.opusfinancial.financial.api.filter.IncomingTransactionFilter;
import br.com.systec.opusfinancial.financial.api.service.AccountService;
import br.com.systec.opusfinancial.financial.api.service.IncomingTransactionService;
import br.com.systec.opusfinancial.financial.api.vo.AccountVO;
import br.com.systec.opusfinancial.financial.api.vo.FinancialTransactionVO;
import br.com.systec.opusfinancial.financial.catalog.impl.domain.FinancialTransaction;
import br.com.systec.opusfinancial.financial.catalog.impl.filter.IncomingTransactionSpecification;
import br.com.systec.opusfinancial.financial.catalog.impl.mapper.IncomingTransactionMapper;
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
    public FinancialTransactionVO save(FinancialTransactionVO transaction) {
        FinancialTransaction transactionBeforeSave = IncomingTransactionMapper.of().toEntity(transaction);
        FinancialTransaction transactionAfterSave = repository.save(transactionBeforeSave);

        return IncomingTransactionMapper.of().toVO(transactionAfterSave);
    }

    @Override
    @Transactional
    public FinancialTransactionVO update(FinancialTransactionVO transaction) {
        FinancialTransaction financialTransactionSaved = repository.findById(transaction.getId())
                .orElseThrow(IncomingFinancialNotFoundException::new);

        FinancialTransaction financialTransactionBeforeSave = IncomingTransactionMapper.of().updateEntityFromVO(financialTransactionSaved, transaction);
        FinancialTransaction financialTransactionAfterSave = repository.save(financialTransactionBeforeSave);

        return IncomingTransactionMapper.of().toVO(financialTransactionAfterSave);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FinancialTransactionVO> findByFilter(IncomingTransactionFilter filter) {
        Specification<FinancialTransaction> specification = IncomingTransactionSpecification.of().filter(filter);
        Page<FinancialTransaction> pageResult = repository.findAll(specification, filter.getPageable());

        List<UUID> listOfAccountId = pageResult.stream().map(FinancialTransaction::getAccountId)
                .filter(Objects::nonNull).distinct().toList();
        List<UUID> listOfCategoryId = pageResult.stream().map(FinancialTransaction::getCategoryId)
                .filter(Objects::nonNull)
                .distinct().toList();

        List<AccountVO> listOfAccount = accountService.findByIds(listOfAccountId);
        List<CategoryVO> listOfCategory = categoryService.findByIds(listOfCategoryId);

        return IncomingTransactionMapper.of().toPage(pageResult, listOfAccount, listOfCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public FinancialTransactionVO findById(UUID id) {
        FinancialTransaction financialTransaction = repository.findById(id).orElseThrow(IncomingFinancialNotFoundException::new);

        return IncomingTransactionMapper.of().toVO(financialTransaction);
    }
}
