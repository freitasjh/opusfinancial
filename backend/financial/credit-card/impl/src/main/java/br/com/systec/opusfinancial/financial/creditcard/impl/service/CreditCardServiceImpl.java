package br.com.systec.opusfinancial.financial.creditcard.impl.service;

import br.com.systec.opusfinancial.financial.api.domain.Account;
import br.com.systec.opusfinancial.financial.api.service.AccountService;
import br.com.systec.opusfinancial.financial.creditcard.api.domain.CreditCard;
import br.com.systec.opusfinancial.financial.creditcard.api.exceptions.CreditCardNotFoundException;
import br.com.systec.opusfinancial.financial.creditcard.api.filter.FilterCreditCard;
import br.com.systec.opusfinancial.financial.creditcard.api.service.CreditCardService;
import br.com.systec.opusfinancial.financial.creditcard.impl.entity.CreditCardEntity;
import br.com.systec.opusfinancial.financial.creditcard.impl.filter.CreditCardSpecification;
import br.com.systec.opusfinancial.financial.creditcard.impl.mapper.CreditCardDomainMapper;
import br.com.systec.opusfinancial.financial.creditcard.impl.repository.CreditCardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CreditCardServiceImpl implements CreditCardService {
    private final CreditCardRepository repository;
    private final AccountService accountService;
    private final CreditCardDomainMapper mapper;

    public CreditCardServiceImpl(CreditCardRepository repository, AccountService accountService, CreditCardDomainMapper mapper) {
        this.repository = repository;
        this.accountService = accountService;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public CreditCard create(CreditCard creditCard) {
        CreditCardEntity creditCardBeforeSave = mapper.toEntity(creditCard);
        CreditCardEntity creditCardSaved = repository.save(creditCardBeforeSave);

        return mapper.toDomain(creditCardSaved, null);
    }

    @Override
    @Transactional
    public CreditCard update(CreditCard creditCard) {
        CreditCardEntity creditCardSaved = repository.findById(creditCard.getId()).orElseThrow(CreditCardNotFoundException::new);
        mapper.updateEntityFormDomain(creditCardSaved, creditCard);

        CreditCardEntity creditCardUpdated = repository.save(creditCardSaved);
        return mapper.toDomain(creditCardUpdated, null);
    }

    @Override
    @Transactional(readOnly = true)
    public CreditCard findById(UUID creditCardId) {
        CreditCardEntity creditCard = repository.findById(creditCardId)
                .orElseThrow(CreditCardNotFoundException::new);

        Account accountVO = accountService.findById(creditCard.getAccountId());

        return mapper.toDomain(creditCard, accountVO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CreditCard> findByFilter(FilterCreditCard filter) {
        Specification<CreditCardEntity> specification = CreditCardSpecification.of().filter(filter);
        Page<CreditCardEntity> pageResult = repository.findAll(specification, filter.getPageable());

        List<UUID> listOfAccount = pageResult.stream().map(CreditCardEntity::getAccountId).toList();
        List<Account> listOfAccountVO = accountService.findByIds(listOfAccount);

        return mapper.toPage(pageResult, listOfAccountVO);
    }

}
