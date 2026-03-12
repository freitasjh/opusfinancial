package br.com.systec.opusfinancial.identity.api.services;

import br.com.systec.opusfinancial.identity.api.exceptions.UserException;
import br.com.systec.opusfinancial.identity.api.domain.User;

import java.util.UUID;

public interface UserService {

    User create(User userVO);

    User update(User userVO);

    User findById(UUID id);

    User findByUsername(String username);

    void saveTenantId(UUID userId, UUID tenantId);
}
