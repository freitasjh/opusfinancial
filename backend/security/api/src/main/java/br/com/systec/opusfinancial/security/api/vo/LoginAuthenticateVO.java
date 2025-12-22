package br.com.systec.opusfinancial.security.api.vo;


import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class LoginAuthenticateVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String accessToken;
    private String type;
    private String role;
    private UUID userId;
    private final String refreshToken;

    public LoginAuthenticateVO(UUID userId, String accessToken, String bearer, String role, String refreshToken) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.type = bearer;
        this.role = role;
        this.refreshToken = refreshToken;
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setToken(String token) {
        this.accessToken = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
