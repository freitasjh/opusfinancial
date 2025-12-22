package br.com.systec.opusfinancial.identity.impl.fake;

import br.com.systec.opusfinancial.identity.api.vo.UserVO;
import br.com.systec.opusfinancial.identity.impl.entities.User;

import java.util.UUID;

public class UserFake {

    private static final UUID ownerID = UUID.randomUUID();
    private static final UUID tenantID = UUID.randomUUID();

    public static User toFake() {
        User user = new User();
        user.setId(ownerID);
        user.setName("Usuario teste");
        user.setUsername("teste");
        user.setPassword("teste");
        user.setTenantId(tenantID);
        user.setLastName("teste");
        user.setEmail("email@teste.com.br");
        user.setDocument("1234");
        user.setEnabled(true);

        return user;
    }

    public static UserVO toFakeVO() {
        UserVO user = new UserVO();
        user.setId(ownerID);
        user.setName("Usuario teste");
        user.setPassword("teste");
        user.setTenantId(tenantID);
        user.setLastName("teste");
        user.setEmail("email@teste.com.br");
        user.setDocument("1234");
        user.setEnabled(true);
        user.setUsername("teste");

        return user;
    }
}
