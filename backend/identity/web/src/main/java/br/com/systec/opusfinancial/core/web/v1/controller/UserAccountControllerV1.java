package br.com.systec.opusfinancial.core.web.v1.controller;

import br.com.systec.opusfinancial.commons.api.tools.controller.RestControllerTool;
import br.com.systec.opusfinancial.commons.api.tools.controller.RestPath;
import br.com.systec.opusfinancial.core.web.v1.dto.UserAccountCreateDTO;
import br.com.systec.opusfinancial.core.web.v1.mapper.UserAccountMapperV1;
import br.com.systec.opusfinancial.identity.api.domain.UserAccount;
import br.com.systec.opusfinancial.identity.api.services.UserAccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestPath.V1 + "/user-accounts")
@Tag(name = "Identidade - Registro", description = "Criação de novas contas de acesso")
public class UserAccountControllerV1 {
    private final UserAccountService accountService;

    public UserAccountControllerV1(UserAccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody @Valid UserAccountCreateDTO accountCreate) {
        UserAccount accountVO = UserAccountMapperV1.of().toVO(accountCreate);
        accountService.create(accountVO);
        return RestControllerTool.of().buildSuccessResponseNoContent();
    }
}
