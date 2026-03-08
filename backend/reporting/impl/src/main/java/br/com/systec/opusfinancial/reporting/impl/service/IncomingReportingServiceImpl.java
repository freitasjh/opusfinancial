package br.com.systec.opusfinancial.reporting.impl.service;

import br.com.systec.opusfinancial.reporting.api.service.ExpenseReportingService;
import br.com.systec.opusfinancial.reporting.api.service.IncomingReportingService;
import br.com.systec.opusfinancial.reporting.impl.repository.ExpenseReportingRepository;
import br.com.systec.opusfinancial.reporting.impl.repository.IncomingReportingRepository;
import br.com.systec.opusfinancial.reporting.vo.ExpenseReportingVO;
import br.com.systec.opusfinancial.reporting.vo.IncomingReportingVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IncomingReportingServiceImpl implements IncomingReportingService {

    private final IncomingReportingRepository repository;

    public IncomingReportingServiceImpl(IncomingReportingRepository repository) {
        this.repository = repository;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<IncomingReportingVO> findLastTenIncoming() {
        return repository.findLastTenIncoming();
    }

}
