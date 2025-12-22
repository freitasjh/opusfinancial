package br.com.systec.opusfinancial.web.v1.controller;

import br.com.systec.opusfinancial.commons.controller.AbstractController;
import br.com.systec.opusfinancial.commons.controller.RestPath;
import br.com.systec.opusfinancial.identity.api.services.AccountService;
import br.com.systec.opusfinancial.identity.api.vo.AccountVO;
import br.com.systec.opusfinancial.web.v1.dto.AccountCreateDTO;
import br.com.systec.opusfinancial.web.v1.mapper.AccountControllerMapperV1;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(RestPath.V1+"/accounts")
public class AccountControllerV1 extends AbstractController {
    private final AccountService accountService;

    public AccountControllerV1(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody AccountCreateDTO accountCreate) {
        AccountVO accountVO = AccountControllerMapperV1.of().toVO(accountCreate);
        accountService.create(accountVO);
        return buildSuccessResponseNoContent();
    }

//    @DeleteMapping("/remove/{tenantId}")
//    public ResponseEntity<Void> remove(@PathVariable("tenantId") UUID tenantId) {
//        return buildSuccessResponseNoContent();
//    }

}
