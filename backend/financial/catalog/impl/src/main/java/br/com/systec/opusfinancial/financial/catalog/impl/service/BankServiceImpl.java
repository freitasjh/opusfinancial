package br.com.systec.opusfinancial.financial.catalog.impl.service;


import br.com.systec.opusfinancial.api.exceptions.BankNotFoundException;
import br.com.systec.opusfinancial.api.filter.FilterBank;
import br.com.systec.opusfinancial.api.service.BankService;
import br.com.systec.opusfinancial.api.vo.BankVO;
import br.com.systec.opusfinancial.financial.catalog.impl.domain.Bank;
import br.com.systec.opusfinancial.financial.catalog.impl.filter.BankSpecification;
import br.com.systec.opusfinancial.financial.catalog.impl.mapper.BankMapper;
import br.com.systec.opusfinancial.financial.catalog.impl.repository.BankRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class BankServiceImpl implements BankService {

    private final BankRepository repository;

    public BankServiceImpl(BankRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public BankVO findById(UUID bankId) {
        Bank bankResultFind = repository.findById(bankId).orElseThrow(BankNotFoundException::new);

        return BankMapper.of().toVO(bankResultFind);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BankVO> findByFilter(FilterBank bankFilter) {
        Specification<Bank> specification = BankSpecification.of().filter(bankFilter);
        Page<Bank> result = repository.findAll(specification, bankFilter.getPageable());

        return BankMapper.of().toPageVO(result);
    }

    @Override
    @Transactional(readOnly = true)
    public BankVO findByCode(String code) {
        Bank bankReturn = repository.findByCode(code).orElseThrow(BankNotFoundException::new);

        return BankMapper.of().toVO(bankReturn);
    }

    @Transactional(readOnly = true)
    public Map<UUID, BankVO> findByIds(Iterable<UUID> ids) {
        List<Bank> banksReturn = repository.findAllById(ids);
        Map<UUID, BankVO> banksMap = new HashMap<>();
        if (banksReturn.isEmpty()) {
            return banksMap;
        }

        for (Bank bank : banksReturn) {
            banksMap.putIfAbsent(bank.getId(), BankMapper.of().toVO(bank));
        }

        return banksMap;
    }

    @Transactional(readOnly = true)
    public List<BankVO> findAll() {
        List<Bank> results = repository.findAll();
        return BankMapper.of().toList(results);
    }

}
