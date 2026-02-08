package br.com.systec.opusfinancial.api.service;


import br.com.systec.opusfinancial.api.filter.FilterBank;
import br.com.systec.opusfinancial.api.vo.BankVO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface BankService {

    BankVO findById(UUID bankId);

    Page<BankVO> findByFilter(FilterBank bankFilter);

    BankVO findByCode(String code);

    Map<UUID, BankVO> findByIds(Iterable<UUID> ids);

    List<BankVO> findAll();
}
