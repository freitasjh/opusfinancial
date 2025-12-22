package br.com.systec.opusfinancial.security.web.v1.dto;

import java.io.Serial;
import java.io.Serializable;

public class LoginDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -8740825485820182749L;

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
