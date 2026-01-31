package br.com.systec.opusfinancial.financial.core.web.v1.controller;

import br.com.systec.opusfinancial.commons.controller.AbstractController;
import br.com.systec.opusfinancial.commons.controller.RestPath;
import br.com.systec.opusfinancial.commons.exceptions.StandardError;
import br.com.systec.opusfinancial.financial.api.filter.FilterAccount;
import br.com.systec.opusfinancial.financial.api.service.AccountService;
import br.com.systec.opusfinancial.financial.api.vo.AccountVO;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.AccountInfoResponseDTO;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.AccountInputDTO;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.AccountResponseDTO;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.AccountResponseSaveDTO;
import br.com.systec.opusfinancial.financial.core.web.v1.mapper.AccountMapperV1;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping(RestPath.V1 + "/accounts")
@Tag(name = "Contas", description = "Cadastro de Contas")
@SecurityRequirement(name = "Authorization")
public class AccountControllerV1 extends AbstractController {

    private final AccountService service;

    public AccountControllerV1(AccountService service) {
        this.service = service;
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AccountResponseSaveDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<AccountResponseSaveDTO> create(@RequestBody AccountInputDTO account) {
        AccountVO accountToSave = AccountMapperV1.of().toVO(account);
        AccountVO accountAfterSave = service.create(accountToSave);

        return buildSuccessResponse(AccountMapperV1.of().toSaveResponse(accountAfterSave));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AccountInfoResponseDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<AccountInfoResponseDTO> findById(@PathVariable("id") UUID id) {
        AccountVO account = service.findById(id);

        return buildSuccessResponse(AccountMapperV1.of().toInfoDTO(account));
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AccountResponseDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<Page<AccountResponseDTO>> findByFilter(@RequestParam(value = "limit", defaultValue = "30") int limit,
                                                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                                                 @RequestParam(value = "keyword", required = false) String keyword) {
        FilterAccount filter = new FilterAccount(keyword, limit, page);
        Page<AccountVO> result = service.findByFilter(filter);

        return buildSuccessResponse(AccountMapperV1.of().toPageResonse(result));
    }

}
