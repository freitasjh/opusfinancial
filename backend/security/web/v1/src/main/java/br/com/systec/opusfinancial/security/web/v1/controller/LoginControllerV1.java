package br.com.systec.opusfinancial.security.web.v1.controller;

import br.com.systec.opusfinancial.commons.controller.AbstractController;
import br.com.systec.opusfinancial.commons.controller.RestPath;
import br.com.systec.opusfinancial.commons.exceptions.StandardError;
import br.com.systec.opusfinancial.security.api.exceptions.RefreshTokenNotFoundException;
import br.com.systec.opusfinancial.security.api.service.LoginService;
import br.com.systec.opusfinancial.security.api.vo.LoginAuthenticateVO;
import br.com.systec.opusfinancial.security.web.v1.dto.LoginDTO;
import br.com.systec.opusfinancial.security.web.v1.dto.LoginResponseDTO;
import br.com.systec.opusfinancial.security.web.v1.dto.RefreshTokenResponseDTO;
import br.com.systec.opusfinancial.security.web.v1.mapper.LoginMapper;
import br.com.systec.opusfinancial.security.web.v1.util.CookieUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestPath.V1 + "/auth")
@Tag(name = "Authentication", description = "Realiza a authenticação do usuario")
public class LoginControllerV1 extends AbstractController {
    private final LoginService loginService;

    public LoginControllerV1(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Realiza a authenticação do usuario e retorna o Token de acesso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o token de acesso", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = LoginResponseDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Erro ao realizar o login", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
    })
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        LoginAuthenticateVO loginAuthenticateVO = loginService.login(LoginMapper.of().toLoginVO(loginDTO));

        response.addHeader(HttpHeaders.SET_COOKIE, CookieUtil.of().createRefreshToken(loginAuthenticateVO).toString());

        return buildSuccessResponse(LoginMapper.of().toLoginResponse(loginAuthenticateVO));
    }

    @PostMapping(value = "/refresh-token", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o token de acesso", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RefreshTokenResponseDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
    })
    public ResponseEntity<RefreshTokenResponseDTO> refreshToken(HttpServletRequest request,
                                                         HttpServletResponse response){
        String refreshToken = getRefreshToken(request);

        LoginAuthenticateVO loginAuthenticateVO = loginService.refreshToken(refreshToken);

        return buildSuccessResponse(new RefreshTokenResponseDTO(loginAuthenticateVO.getAccessToken()));
    }

    private String getRefreshToken(HttpServletRequest request) {
        String refreshToken = null;
        if (request.getCookies() != null) {
            for (var cookie : request.getCookies()) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                }
            }
        }

        if (refreshToken == null) {
            throw new RefreshTokenNotFoundException();
        }

        return refreshToken;
    }
}
