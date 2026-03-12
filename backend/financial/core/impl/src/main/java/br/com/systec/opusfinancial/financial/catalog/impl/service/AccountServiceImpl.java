package br.com.systec.opusfinancial.financial.catalog.impl.service;

import br.com.systec.opusfinancial.api.service.BankService;
import br.com.systec.opusfinancial.api.domain.Bank;
import br.com.systec.opusfinancial.financial.api.exceptions.AccountNotFoundException;
import br.com.systec.opusfinancial.financial.api.filter.FilterAccount;
import br.com.systec.opusfinancial.financial.api.service.AccountService;
import br.com.systec.opusfinancial.financial.api.domain.AccountType;
import br.com.systec.opusfinancial.financial.api.domain.Account;
import br.com.systec.opusfinancial.financial.api.domain.TransactionType;
import br.com.systec.opusfinancial.financial.catalog.impl.entity.AccountEntity;
import br.com.systec.opusfinancial.financial.catalog.impl.filter.AccountSpecification;
import br.com.systec.opusfinancial.financial.catalog.impl.mapper.AccountMapper;
import br.com.systec.opusfinancial.financial.catalog.impl.repository.AccountRepository;
import br.com.systec.opusfinancial.i18n.I18nTranslate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
    private static final String BANK_OTHER = "999";

    private final AccountRepository repository;
    private final BankService bankService;

    public AccountServiceImpl(AccountRepository repository, BankService bankService) {
        this.repository = repository;
        this.bankService = bankService;
    }

    @Override
    @Transactional
    public Account create(Account account) {
        AccountEntity accountBeforeSave = AccountMapper.of().toEntity(account);
        AccountEntity accountAfterSave = repository.save(accountBeforeSave);

        return AccountMapper.of().toVO(accountAfterSave);
    }

    @Override
    @Transactional
    public Account update(Account account) {
        AccountEntity accountSaved = repository.findById(account.getId()).orElseThrow(AccountNotFoundException::new);
        AccountEntity accountBeforeUpdate = AccountMapper.of().toEntity(account);
        accountBeforeUpdate.setCreateAt(accountSaved.getCreateAt());

        AccountEntity accountAfterUpdate = repository.save(accountBeforeUpdate);

        return AccountMapper.of().toVO(accountAfterUpdate);
    }

    @Override
    @Transactional(readOnly = true)
    public Account findById(UUID id) {
        AccountEntity accountReturn = repository.findById(id).orElseThrow(AccountNotFoundException::new);
        Bank bankReturnFind = bankService.findById(accountReturn.getBankId());

        Account accountToReturn = AccountMapper.of().toVO(accountReturn);
        accountToReturn.setBank(bankReturnFind);

        return accountToReturn;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Account> findByFilter(FilterAccount filter) {
        Specification<AccountEntity> specification = AccountSpecification.of().filter(filter);
        Page<AccountEntity> result = repository.findAll(specification, filter.getPageable());

        List<UUID> bankIds = result.stream().map(AccountEntity::getBankId).toList();
        List<Bank> banks = bankService.findByIds(bankIds).values().stream().toList();

        return AccountMapper.of().toPageVO(result, banks);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> findByIds(List<UUID> listOfAccountId) {
        if (listOfAccountId == null || listOfAccountId.isEmpty()) {
            return new ArrayList<>();
        }

        List<AccountEntity> result = repository.findAllById(listOfAccountId);

        return AccountMapper.of().toList(result);
    }

    @Override
    @Transactional
    public void createDefaultAccount(UUID tenantId) {
        log.warn("@@@ Criando Conta default para o tenant {} @@@", tenantId);

        AccountEntity account = new AccountEntity();
        account.setAccountName(I18nTranslate.toLocale("account.name.default"));
        account.setTenantId(tenantId);
        account.setAccountType(AccountType.WALLET);

        Bank bankVO = bankService.findByCode(BANK_OTHER);
        account.setBankId(bankVO.getId());

        repository.save(account);
    }

    @Override
    @Transactional
    public void updateBalance(UUID accountId, BigDecimal amount, TransactionType transactionType) {
        AccountEntity accountToUpdate = repository.findById(accountId).orElseThrow(AccountNotFoundException::new);

        BigDecimal balanceOld = accountToUpdate.getBalance();
        BigDecimal balanceNew;

        if (transactionType == TransactionType.INCOMING) {
            balanceNew = balanceOld.add(amount);
        } else {
            balanceNew = balanceOld.subtract(amount);
        }

        accountToUpdate.setBalance(balanceNew);
        repository.save(accountToUpdate);
    }
}
