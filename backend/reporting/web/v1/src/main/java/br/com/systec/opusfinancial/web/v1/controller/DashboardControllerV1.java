package br.com.systec.opusfinancial.web.v1.controller;

import br.com.systec.opusfinancial.commons.api.tools.controller.RestControllerTool;
import br.com.systec.opusfinancial.commons.api.tools.controller.RestPath;

import br.com.systec.opusfinancial.reporting.api.service.AccountReportingService;
import br.com.systec.opusfinancial.reporting.api.service.ExpenseReportingService;
import br.com.systec.opusfinancial.reporting.api.service.IncomingReportingService;
import br.com.systec.opusfinancial.reporting.vo.AccountBalanceVO;
import br.com.systec.opusfinancial.reporting.vo.ExpenseReportingVO;
import br.com.systec.opusfinancial.reporting.vo.IncomingReportingVO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(RestPath.V1 + "/dashboards")
@Tag(name = "Relatórios - Dashboards", description = "Visões consolidadas, saldos e histórico recente")
@SecurityRequirement(name = "Authorization")
public class DashboardControllerV1 {

    private final AccountReportingService accountReportingService;
    private final ExpenseReportingService expenseReportingService;
    private final IncomingReportingService incomingReportingService;

    public DashboardControllerV1(AccountReportingService accountReportingService, ExpenseReportingService expenseReportingService, IncomingReportingService incomingReportingService) {
        this.accountReportingService = accountReportingService;
        this.expenseReportingService = expenseReportingService;
        this.incomingReportingService = incomingReportingService;
    }

    @GetMapping(value = "/account-summary-balance", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccountBalanceVO>> getSummaryAccountBalance() {
        return RestControllerTool.of().buildSuccessResponse(
                accountReportingService.getSummaryAccountBalance()
        );
    }

    @GetMapping(value = "/last-expenses", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ExpenseReportingVO>> findLastTenExpense() {
        return RestControllerTool.of().buildSuccessResponse(
                expenseReportingService.findLastTenExpense()
        );
    }

    @GetMapping(value = "/last-incoming", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<IncomingReportingVO>> findLastTenIncoming() {
        return RestControllerTool.of().buildSuccessResponse(
                incomingReportingService.findLastTenIncoming()
        );
    }
}
