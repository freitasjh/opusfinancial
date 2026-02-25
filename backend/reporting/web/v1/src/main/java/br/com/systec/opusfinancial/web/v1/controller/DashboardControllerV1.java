package br.com.systec.opusfinancial.web.v1.controller;

import br.com.systec.opusfinancial.commons.controller.AbstractController;
import br.com.systec.opusfinancial.commons.controller.RestPath;
import br.com.systec.opusfinancial.reporting.api.service.AccountReportingService;
import br.com.systec.opusfinancial.reporting.vo.AccountBalanceVO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(RestPath.V1 + "/dashboards")
public class DashboardControllerV1 extends AbstractController {

    private final AccountReportingService accountReportingService;

    public DashboardControllerV1(AccountReportingService accountReportingService) {
        this.accountReportingService = accountReportingService;
    }

    @GetMapping(value = "/account-summary-balance", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccountBalanceVO>> getSummaryAccountBalance() {
        return buildSuccessResponse(accountReportingService.getSummaryAccountBalance());
    }
}
