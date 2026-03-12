package br.com.systec.opusfinancial.core.web.v1.mapper;

import br.com.systec.opusfinancial.core.web.v1.dto.UserProfileDTO;
import br.com.systec.opusfinancial.identity.api.domain.User;

public class UserMapperV1 {

    private UserMapperV1() {}

    public static UserMapperV1 of() {
        return new UserMapperV1();
    }

    public UserProfileDTO toUserProfile(User userVO) {
        UserProfileDTO userProfile = new UserProfileDTO();
        userProfile.setName(userVO.getName());
        userProfile.setEmail(userVO.getEmail());
        userProfile.setProfile("Admin"); //TODO por enquanto

        return userProfile;
    }
}
