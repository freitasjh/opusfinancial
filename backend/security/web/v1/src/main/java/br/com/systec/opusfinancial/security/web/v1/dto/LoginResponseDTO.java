package br.com.systec.opusfinancial.security.web.v1.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class LoginResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 5153161264914033526L;
    private UUID userId;
    private String accessToken;
    private String type;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
