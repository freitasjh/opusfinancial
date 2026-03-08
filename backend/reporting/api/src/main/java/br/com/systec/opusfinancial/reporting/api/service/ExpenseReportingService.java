package br.com.systec.opusfinancial.reporting.api.service;

import br.com.systec.opusfinancial.reporting.vo.ExpenseReportingVO;

import java.util.List;

public interface ExpenseReportingService {

    List<ExpenseReportingVO> findLastTenExpense();
}
