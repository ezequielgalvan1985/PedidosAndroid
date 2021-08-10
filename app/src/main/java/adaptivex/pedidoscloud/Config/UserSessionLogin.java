package adaptivex.pedidoscloud.Config;

import adaptivex.pedidoscloud.Entity.UserEntity;

public class UserSessionLogin {
    private static UserSessionLogin INSTANCIA;
    private UserEntity user;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public static UserSessionLogin getInstancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new UserSessionLogin();
        }
        return INSTANCIA;
    }
}

