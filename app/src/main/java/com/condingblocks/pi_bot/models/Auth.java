package com.condingblocks.pi_bot.models;

/**
 * Created by rishabhkhanna on 14/12/17.
 */

public class Auth {
    String email;
    String password;

    public Auth(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
