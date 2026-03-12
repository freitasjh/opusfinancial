package br.com.systec.opusfinancial.financial.creditcard.web.internal.v1.controller;

import br.com.systec.opusfinancial.commons.api.tools.controller.RestPath;
import br.com.systec.opusfinancial.commons.api.tools.exceptions.StandardError;
import br.com.systec.opusfinancial.financial.creditcard.api.domain.CreditCard;
import br.com.systec.opusfinancial.financial.creditcard.api.service.CreditCardService;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestPath.V1 + "/credit-cards")
@Tag(name = "Financeiro - Cartões", description = "Gestão de cartões de crédito e faturas")
public class CreditCardControllerInternalV1 {

    private final CreditCardService service;

    public CreditCardControllerInternalV1(CreditCardService service) {
        this.service = service;
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Carastra um novo cartão de credito", description = "Carastra cartão de credito")
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
        CreditCard creditCardToSave = CreditCardMapper.INSTANCE.toDomain(creditCardInputDTO);
        CreditCard creditCard = service.create(creditCardToSave);

        return ResponseEntity.ok(CreditCardMapper.INSTANCE.toResponse(creditCard));
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualiza um cartão de credito", description = "Atualiza cartão de credito")
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
        CreditCard creditCardToSave = CreditCardMapper.INSTANCE.toDomain(creditCardInputDTO);
        CreditCard creditCard = service.update(creditCardToSave);

        return ResponseEntity.ok(CreditCardMapper.INSTANCE.toResponse(creditCard));
    }
}
