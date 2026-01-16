package br.com.systec.opusfinancial.core.web.v1.controller;

import br.com.systec.opusfinancial.commons.controller.AbstractController;
import br.com.systec.opusfinancial.commons.controller.RestPath;
import br.com.systec.opusfinancial.identity.api.services.UserAccountService;
import br.com.systec.opusfinancial.identity.api.vo.UserAccountVO;
import br.com.systec.opusfinancial.core.web.v1.dto.UserAccountCreateDTO;
import br.com.systec.opusfinancial.core.web.v1.mapper.UserAccountMapperV1;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestPath.V1 + "/user-accounts")
public class UserAccountControllerV1 extends AbstractController {
    private final UserAccountService accountService;

    public UserAccountControllerV1(UserAccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody UserAccountCreateDTO accountCreate) {
        UserAccountVO accountVO = UserAccountMapperV1.of().toVO(accountCreate);
        accountService.create(accountVO);
        return buildSuccessResponseNoContent();
    }

//    @DeleteMapping("/remove/{tenantId}")
//    public ResponseEntity<Void> remove(@PathVariable("tenantId") UUID tenantId) {
//        return buildSuccessResponseNoContent();
//    }

}
