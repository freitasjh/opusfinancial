package br.com.systec.opusfinancial.core.web.v1.dto;

import jakarta.validation.constraints.NotEmpty;

import java.io.Serial;
import java.io.Serializable;

public class UserAccountCreateDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -3396254207304243246L;

    @NotEmpty(message = "{account.name.not.blank}")
    private String accountName;
    @NotEmpty(message = "{account.name.user.not.blank}")
    private String name;
    @NotEmpty(message = "{account.document.not.blank}")
    private String document;
    @NotEmpty(message = "{account.email.not.blank}")
    private String email;
    @NotEmpty(message = "{account.username.not.blank}")
    private String username;
    @NotEmpty(message = "{account.password.not.blank}")
    private String password;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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
