package br.com.systec.opusfinancial.security.web.v1.util;

import br.com.systec.opusfinancial.security.api.vo.LoginAuthenticateVO;
import org.springframework.http.ResponseCookie;

import java.time.Duration;

public class CookieUtil {

    public static CookieUtil of() {
        return new CookieUtil();
    }


    public ResponseCookie createRefreshToken(LoginAuthenticateVO loginAuthenticate) {
        return ResponseCookie.from("refreshToken", loginAuthenticate.getRefreshToken())
                .httpOnly(true)    // Impede acesso via JavaScript (mitiga XSS)
                .secure(false)     // Apenas enviado sobre HTTPS (essencial em produção)
                .path("/") // O cookie só será enviado para os endpoints de autenticação
                .maxAge(Duration.ofMillis(604800)) // Duração do cookie
                .build();
    }

    public ResponseCookie revokeRefreshToken() {
        return ResponseCookie.from("refreshToken", "")
                .httpOnly(true)    // Impede acesso via JavaScript (mitiga XSS)
                .secure(false)     // Apenas enviado sobre HTTPS (essencial em produção)
                .path("/") // O cookie só será enviado para os endpoints de autenticação
                .maxAge(Duration.ofMillis(0)) // Duração do cookie
                .build();
    }
}