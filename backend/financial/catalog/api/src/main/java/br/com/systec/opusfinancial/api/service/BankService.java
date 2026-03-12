package br.com.systec.opusfinancial.api.service;


import br.com.systec.opusfinancial.api.filter.FilterBank;
import br.com.systec.opusfinancial.api.domain.Bank;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface BankService {

    Bank findById(UUID bankId);

    Page<Bank> findByFilter(FilterBank bankFilter);

    Bank findByCode(String code);

    Map<UUID, Bank> findByIds(Iterable<UUID> ids);

    List<Bank> findAll();
}
