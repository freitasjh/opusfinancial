package br.com.systec.opusfinancial.financial.catalog.impl.service;

import br.com.systec.opusfinancial.api.service.BankService;
import br.com.systec.opusfinancial.api.vo.BankVO;
import br.com.systec.opusfinancial.financial.api.exceptions.AccountNotFoundException;
import br.com.systec.opusfinancial.financial.api.filter.FilterAccount;
import br.com.systec.opusfinancial.financial.api.service.AccountService;
import br.com.systec.opusfinancial.financial.api.vo.AccountType;
import br.com.systec.opusfinancial.financial.api.vo.AccountVO;
import br.com.systec.opusfinancial.financial.catalog.impl.domain.Account;
import br.com.systec.opusfinancial.financial.catalog.impl.filter.AccountSpecification;
import br.com.systec.opusfinancial.financial.catalog.impl.mapper.AccountMapper;
import br.com.systec.opusfinancial.financial.catalog.impl.repository.AccountRepository;
import br.com.systec.opusfinancial.i18n.I18nTranslate;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;
    private final BankService bankService;

    public AccountServiceImpl(AccountRepository repository, BankService bankService) {
        this.repository = repository;
        this.bankService = bankService;
    }

    @Override
    @Transactional
    public AccountVO create(AccountVO account) {
        Account accountBeforeSave = AccountMapper.of().toEntity(account);
        Account accountAfterSave = repository.save(accountBeforeSave);

        return AccountMapper.of().toVO(accountAfterSave);
    }

    @Override
    @Transactional
    public AccountVO update(AccountVO account) {
        Account accountSaved = repository.findById(account.getId()).orElseThrow(AccountNotFoundException::new);
        Account accountBeforeUpdate = AccountMapper.of().toEntity(account);
        accountBeforeUpdate.setCreateAt(accountSaved.getCreateAt());

        Account accountAfterUpdate = repository.save(accountBeforeUpdate);

        return AccountMapper.of().toVO(accountAfterUpdate);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountVO findById(UUID id) {
        Account accountReturn = repository.findById(id).orElseThrow(AccountNotFoundException::new);
        BankVO bankReturnFind = bankService.findById(accountReturn.getBankId());

        AccountVO accountToReturn = AccountMapper.of().toVO(accountReturn);
        accountToReturn.setBank(bankReturnFind);

        return accountToReturn;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AccountVO> findByFilter(FilterAccount filter) {
        Specification<Account> specification = AccountSpecification.of().filter(filter);
        Page<Account> result = repository.findAll(specification, filter.getPageable());

        List<UUID> bankIds = result.stream().map(Account::getBankId).toList();
        List<BankVO> banks = bankService.findByIds(bankIds).values().stream().toList();

        return AccountMapper.of().toPageVO(result, banks);
    }

    @Transactional
    public void createDefaultAccount(UUID tenantId) {
        Account account = new Account();
        account.setAccountName(I18nTranslate.toLocale("account.name.default"));
        account.setTenantId(tenantId);
        account.setAccountType(AccountType.WALLET);

        BankVO bankVO = bankService.findByCode("1");
        account.setBankId(bankVO.getId());

        repository.save(account);
    }
}
