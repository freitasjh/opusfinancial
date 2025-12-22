package br.com.systec.opusfinancial.security.web.v1.dto;

import java.io.Serial;
import java.io.Serializable;

public class RefreshTokenResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7041596417072736973L;
    private String accessToken;

    public RefreshTokenResponseDTO() {
    }

    public RefreshTokenResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
