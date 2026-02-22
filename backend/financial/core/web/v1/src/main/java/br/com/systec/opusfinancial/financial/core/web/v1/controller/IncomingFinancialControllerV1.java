package br.com.systec.opusfinancial.financial.core.web.v1.controller;

import br.com.systec.opusfinancial.commons.controller.AbstractController;
import br.com.systec.opusfinancial.commons.controller.RestPath;
import br.com.systec.opusfinancial.commons.exceptions.StandardError;
import br.com.systec.opusfinancial.financial.api.filter.IncomingTransactionFilter;
import br.com.systec.opusfinancial.financial.api.service.IncomingTransactionService;
import br.com.systec.opusfinancial.financial.api.vo.FinancialTransactionVO;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.AccountResponseDTO;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.AccountResponseSaveDTO;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.IncomingFinancialInputDTO;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.IncomingInformationResponseDTO;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.IncomingSaveResponseDTO;
import br.com.systec.opusfinancial.financial.core.web.v1.mapper.IncomingTransactionMapperV1;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(RestPath.V1 + "/incomings")
@Tag(name = "Receitas", description = "Cadastro de receitas")
@SecurityRequirement(name = "Authorization")
public class IncomingFinancialControllerV1 extends AbstractController {

    private final IncomingTransactionService service;

    public IncomingFinancialControllerV1(IncomingTransactionService service) {
        this.service = service;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = IncomingSaveResponseDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<IncomingSaveResponseDTO> save(@RequestBody @Valid IncomingFinancialInputDTO incomingFinancialInputDTO) {
        FinancialTransactionVO financialTransactionToSave = IncomingTransactionMapperV1.of().toVO(incomingFinancialInputDTO);
        FinancialTransactionVO financialTransactionAfterSave = service.save(financialTransactionToSave);

        return buildSuccessResponse(IncomingTransactionMapperV1.of().toSaveResponse(financialTransactionAfterSave));
    }

    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = IncomingSaveResponseDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<IncomingSaveResponseDTO> update(@RequestBody @Valid IncomingFinancialInputDTO incomingFinancialInputDTO) {
        FinancialTransactionVO financialTransactionToSave = IncomingTransactionMapperV1.of().toVO(incomingFinancialInputDTO);
        FinancialTransactionVO financialTransactionAfterSave = service.update(financialTransactionToSave);

        return buildSuccessResponse(IncomingTransactionMapperV1.of().toSaveResponse(financialTransactionAfterSave));
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = IncomingInformationResponseDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<Page<IncomingInformationResponseDTO>> findByFilter(@RequestParam(value = "limit", defaultValue = "30") int limit,
                                                                             @RequestParam(value = "page", defaultValue = "0") int page,
                                                                             @RequestParam(value = "keyword", required = false) String keyword) {
        IncomingTransactionFilter filter = new IncomingTransactionFilter(keyword, limit, page);
        Page<FinancialTransactionVO> result = service.findByFilter(filter);

        return buildSuccessResponse(IncomingTransactionMapperV1.of().toPage(result));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = IncomingInformationResponseDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<IncomingInformationResponseDTO> findById(@PathVariable("id") String id) {
        FinancialTransactionVO financialTransactionReturn = service.findById(UUID.fromString(id));
        return buildSuccessResponse(IncomingTransactionMapperV1.of().toInformationDTO(financialTransactionReturn));
    }
}
