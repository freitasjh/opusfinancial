package br.com.systec.opusfinancial.identity.api.vo;

import java.io.Serial;
import java.io.Serializable;

public class AccountVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -6265259819744464451L;
    private String accountName;
    private String name;
    private String username;
    private String password;
    private String email;
    private String document;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
