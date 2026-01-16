package br.com.systec.opusfinancial.identity.api.services;

import br.com.systec.opusfinancial.commons.exceptions.BaseException;
import br.com.systec.opusfinancial.identity.api.vo.UserAccountVO;

public interface UserAccountService {

    void create(UserAccountVO accountVO) throws BaseException;
}
