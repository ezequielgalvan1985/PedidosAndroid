package adaptivex.pedidoscloud.Model;

import adaptivex.pedidoscloud.Core.WorkString;

/**
 * Created by ezequiel on 23/05/2016.
 */
public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private Group group;
    private String logued;
    private String token;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getUserDescription(){
        return getUsername() + " (" + getEmail() + ")";

    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getLogued() {
        return logued;
    }

    public void setLogued(String logued) {
        this.logued = logued;
    }

    }
