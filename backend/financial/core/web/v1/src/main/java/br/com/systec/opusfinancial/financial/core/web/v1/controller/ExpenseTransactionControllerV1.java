package br.com.systec.opusfinancial.financial.core.web.v1.controller;

import br.com.systec.opusfinancial.commons.controller.AbstractController;
import br.com.systec.opusfinancial.commons.controller.RestPath;
import br.com.systec.opusfinancial.commons.exceptions.StandardError;
import br.com.systec.opusfinancial.financial.api.filter.ExpenseTransactionFilter;
import br.com.systec.opusfinancial.financial.api.service.ExpenseTransactionService;
import br.com.systec.opusfinancial.financial.api.vo.FinancialTransactionVO;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.ExpenseTransactionInfoDTO;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.ExpenseTransactionInputDTO;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.ExpenseTransactionSaveResponseDTO;
import br.com.systec.opusfinancial.financial.core.web.v1.mapper.ExpenseTransactionMapperV1;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(RestPath.V1 + "/exepenses")
@Tag(name = "Despesas", description = "Cadastro de despesas")
@SecurityRequirement(name = "Authorization")
public class ExpenseTransactionControllerV1 extends AbstractController {

    private final ExpenseTransactionService service;

    public ExpenseTransactionControllerV1(ExpenseTransactionService service) {
        this.service = service;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Salva uma nova despesa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExpenseTransactionSaveResponseDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<ExpenseTransactionSaveResponseDTO> create(@RequestBody @Valid ExpenseTransactionInputDTO expenseTransaction) {
        FinancialTransactionVO financialTransaction = ExpenseTransactionMapperV1.of().toVO(expenseTransaction);

        FinancialTransactionVO financialTransactionAfterSave = service.create(financialTransaction);

        return buildSuccessResponse(ExpenseTransactionMapperV1.of().toSaveResponse(financialTransactionAfterSave));
    }

    @DeleteMapping("/{expenseTransactionId}")
    @Operation(description = "Deleta a despesa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<Void> delete(UUID expenseTransactionId) {
        service.delete(expenseTransactionId);

        return buildSuccessResponseNoContent();
    }

    @GetMapping(value = "/{expenseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExpenseTransactionInputDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<ExpenseTransactionInputDTO> findById(UUID expenseId) {
        FinancialTransactionVO expenseTransaction = service.findById(expenseId);

        return buildSuccessResponse(ExpenseTransactionMapperV1.of().toDTO(expenseTransaction));
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Pesquisa as despess com filtros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExpenseTransactionInfoDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<Page<ExpenseTransactionInfoDTO>> findByFilter(@RequestParam(value = "limit", defaultValue = "30") int limit,
                                                                        @RequestParam(value = "page", defaultValue = "0") int page,
                                                                        @RequestParam(value = "keyword", required = false) String keyword) {
        ExpenseTransactionFilter filter = new ExpenseTransactionFilter(keyword, limit, page);

        Page<FinancialTransactionVO> pageResult = service.findByFilter(filter);

        return buildSuccessResponse(ExpenseTransactionMapperV1.of().toPageDTO(pageResult));
    }
}
