package br.com.systec.opusfinancial.identity.api.services;

import br.com.systec.opusfinancial.commons.exceptions.BaseException;
import br.com.systec.opusfinancial.identity.api.vo.AccountVO;

public interface AccountService {

    void create(AccountVO accountVO) throws BaseException;
}
