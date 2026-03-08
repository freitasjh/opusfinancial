package br.com.systec.opusfinancial.reporting.api.service;

import br.com.systec.opusfinancial.reporting.vo.IncomingReportingVO;

import java.util.List;

public interface IncomingReportingService {

    List<IncomingReportingVO> findLastTenIncoming();
}
