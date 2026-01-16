package br.com.systec.opusfinancial.identity.impl.mapper;

import br.com.systec.opusfinancial.identity.api.vo.UserAccountVO;
import br.com.systec.opusfinancial.identity.api.vo.UserVO;
import br.com.systec.opusfinancial.identity.impl.entities.User;

public class UserMapper {

    private UserMapper(){}

    public static UserMapper of() {
        return new UserMapper();
    }

    public User toEntity(UserVO userVO) {
        User user = new User();
        user.setId(userVO.getId());
        user.setName(userVO.getName());
        user.setDocument(userVO.getDocument());
        user.setEmail(userVO.getEmail());
        user.setEnabled(true);
        user.setName(userVO.getName());
        user.setLastName(userVO.getLastName());
        user.setTenantId(userVO.getTenantId());
        user.setUsername(userVO.getUsername());
        user.setPassword(userVO.getPassword());
        user.setTenantId(userVO.getTenantId());

        return user;
    }

    public UserVO toVO(User user) {
        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setName(user.getName());
        userVO.setLastName(user.getLastName());
        userVO.setDocument(user.getDocument());
        userVO.setEnabled(user.isEnabled());
        userVO.setTenantId(user.getTenantId());
        userVO.setUsername(user.getUsername());
        userVO.setPassword(user.getPassword());
        userVO.setTenantId(user.getTenantId());
        userVO.setEmail(user.getEmail());

        return userVO;
    }

    public UserVO accountToUserVO(UserAccountVO accountVO) {
        UserVO userVO = new UserVO();
        userVO.setUsername(accountVO.getUsername());
        userVO.setPassword(accountVO.getPassword());
        userVO.setName(accountVO.getName());
        userVO.setDocument(accountVO.getDocument());
        userVO.setEmail(accountVO.getEmail());

        return userVO;
    }
}
