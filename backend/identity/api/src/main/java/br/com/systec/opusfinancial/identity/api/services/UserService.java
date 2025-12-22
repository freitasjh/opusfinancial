package br.com.systec.opusfinancial.identity.api.services;

import br.com.systec.opusfinancial.commons.exceptions.BaseException;
import br.com.systec.opusfinancial.identity.api.exceptions.UserException;
import br.com.systec.opusfinancial.identity.api.vo.UserVO;

import java.util.UUID;

public interface UserService {

    UserVO create(UserVO userVO) throws UserException;

    UserVO update(UserVO userVO) throws UserException;

    UserVO findById(UUID id) throws UserException;

    UserVO findByUsername(String username) throws BaseException;
}
