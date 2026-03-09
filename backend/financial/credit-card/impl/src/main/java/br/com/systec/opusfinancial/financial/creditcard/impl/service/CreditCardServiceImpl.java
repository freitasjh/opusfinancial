package br.com.systec.opusfinancial.financial.creditcard.impl.service;

import br.com.systec.opusfinancial.financial.api.service.AccountService;
import br.com.systec.opusfinancial.financial.api.vo.AccountVO;
import br.com.systec.opusfinancial.financial.creditcard.api.domain.CreditCard;
import br.com.systec.opusfinancial.financial.creditcard.api.exceptions.CreditCardNotFoundException;
import br.com.systec.opusfinancial.financial.creditcard.api.filter.FilterCreditCard;
import br.com.systec.opusfinancial.financial.creditcard.api.service.CreditCardService;
import br.com.systec.opusfinancial.financial.creditcard.impl.entity.CreditCardEntity;
import br.com.systec.opusfinancial.financial.creditcard.impl.filter.CreditCardSpecification;
import br.com.systec.opusfinancial.financial.creditcard.impl.mapper.CreditCardMapper;
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

    public CreditCardServiceImpl(CreditCardRepository repository, AccountService accountService) {
        this.repository = repository;
        this.accountService = accountService;
    }

    @Transactional
    public CreditCard create(CreditCard creditCard) {
        CreditCardEntity creditCardBeforeSave = CreditCardMapper.of().toEntity(creditCard);
        CreditCardEntity creditCardSaved = repository.save(creditCardBeforeSave);

        return CreditCardMapper.of().toVO(creditCardSaved, null);
    }

    @Transactional(readOnly = true)
    public CreditCard findById(UUID creditCardId) {
        CreditCardEntity creditCard =  repository.findById(creditCardId)
                .orElseThrow(CreditCardNotFoundException::new);

        AccountVO accountVO = accountService.findById(creditCard.getAccountId());

        return CreditCardMapper.of().toVO(creditCard, accountVO);
    }

    @Transactional(readOnly = true)
    public Page<CreditCard> findByFilter(FilterCreditCard filter) {
        Specification<CreditCardEntity> specification = CreditCardSpecification.of().filter(filter);
        Page<CreditCardEntity> pageResult = repository.findAll(specification, filter.getPageable());

        List<UUID> listOfAccount = pageResult.stream().map(CreditCardEntity::getAccountId).toList();
        List<AccountVO> listOfAccountVO = accountService.findByIds(listOfAccount);

        return CreditCardMapper.of().toPage(pageResult, listOfAccountVO);
    }

}
