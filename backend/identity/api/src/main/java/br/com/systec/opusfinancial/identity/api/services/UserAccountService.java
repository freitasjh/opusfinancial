package br.com.systec.opusfinancial.identity.api.services;

import br.com.systec.opusfinancial.commons.api.exceptions.BaseException;
import br.com.systec.opusfinancial.identity.api.domain.UserAccount;

public interface UserAccountService {

    void create(UserAccount accountVO) throws BaseException;
}
