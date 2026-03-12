package br.com.systec.opusfinancial.financial.creditcard.web.internal.v1.controller;

import br.com.systec.opusfinancial.commons.api.tools.controller.RestControllerTool;
import br.com.systec.opusfinancial.commons.api.tools.controller.RestPath;
import br.com.systec.opusfinancial.commons.api.tools.exceptions.StandardError;
import br.com.systec.opusfinancial.financial.creditcard.api.domain.CreditCard;
import br.com.systec.opusfinancial.financial.creditcard.api.filter.FilterCreditCard;
import br.com.systec.opusfinancial.financial.creditcard.api.service.CreditCardService;
import br.com.systec.opusfinancial.financial.creditcard.web.internal.v1.dto.CreditCardInfoDTO;
import br.com.systec.opusfinancial.financial.creditcard.web.internal.v1.dto.CreditCardInputDTO;
import br.com.systec.opusfinancial.financial.creditcard.web.internal.v1.dto.CreditCardSaveResponseDTO;
import br.com.systec.opusfinancial.financial.creditcard.web.internal.v1.mapper.CreditCardMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(RestPath.V1 + "/credit-cards")
@Tag(name = "Financeiro - Cartões", description = "Gestão de cartões de crédito e faturas")
public class CreditCardControllerInternalV1 {

    private final CreditCardService service;
    private final CreditCardMapper mapper;

    public CreditCardControllerInternalV1(CreditCardService service, CreditCardMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Cadastra", description = "Cria novo cartão de credito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CreditCardSaveResponseDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<CreditCardSaveResponseDTO> create(@RequestBody @Valid CreditCardInputDTO creditCardInputDTO) {
        CreditCard creditCardToSave = mapper.toDomain(creditCardInputDTO);
        CreditCard creditCard = service.create(creditCardToSave);

        return RestControllerTool.of().buildSuccessResponse(
                mapper.toResponse(creditCard)
        );
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualiza", description = "Atualiza cartão de credito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CreditCardSaveResponseDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<CreditCardSaveResponseDTO> update(@RequestBody @Valid CreditCardInputDTO creditCardInputDTO) {
        CreditCard creditCardToSave = mapper.toDomain(creditCardInputDTO);
        CreditCard creditCard = service.update(creditCardToSave);

        return RestControllerTool.of().buildSuccessResponse(
                mapper.toResponse(creditCard)
        );
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Pesuisa por ID", description = "Busca cartão por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CreditCardInputDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<CreditCardInputDTO> findById(@PathVariable("id") UUID creditCardId) {
        var creditCard = service.findById(creditCardId);

        return RestControllerTool.of().buildSuccessResponse(
                mapper.toInputDTO(creditCard)
        );
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Busca por filtro", description = "Busca cartões por filtro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CreditCardInfoDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<Page<CreditCardInfoDTO>> findByFilter(@RequestParam(value = "keyword", required = false) String keyword,
                                                                @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                @RequestParam(value = "limit", required = false, defaultValue = "30") int limit,
                                                                @RequestParam(value = "accountId", required = false) UUID accountId) {
        FilterCreditCard filter = new FilterCreditCard(keyword, limit, page);
        filter.setAccountId(accountId);

        Page<CreditCard> pageResult = service.findByFilter(filter);

        return RestControllerTool.of().buildSuccessResponse(mapper.toPage(pageResult));
    }
}
