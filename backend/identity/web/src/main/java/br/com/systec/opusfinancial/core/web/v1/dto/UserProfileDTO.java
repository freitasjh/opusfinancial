package br.com.systec.opusfinancial.core.web.v1.dto;

import java.io.Serial;
import java.io.Serializable;

public class UserProfileDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1577047207625292811L;

    private String name;
    private String email;
    private String profile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
