package br.com.systec.opusfinancial.reporting.impl.service;

import br.com.systec.opusfinancial.reporting.api.service.ExpenseReportingService;
import br.com.systec.opusfinancial.reporting.impl.repository.ExpenseReportingRepository;
import br.com.systec.opusfinancial.reporting.vo.ExpenseReportingVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExpenseReportingServiceImpl implements ExpenseReportingService {

    private final ExpenseReportingRepository repository;

    public ExpenseReportingServiceImpl(ExpenseReportingRepository repository) {
        this.repository = repository;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<ExpenseReportingVO> findLastTenExpense() {
        return repository.findLastTenExpenses();
    }

}
