package adaptivex.pedidoscloud.Config;

import adaptivex.pedidoscloud.Model.User;

public class UserSessionLogin {
    private static UserSessionLogin INSTANCIA;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static UserSessionLogin getINSTANCIA() {
        if (INSTANCIA == null) {
            INSTANCIA = new UserSessionLogin();
        }
        return INSTANCIA;
    }
}

