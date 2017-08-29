package com.llegoati.llegoati.infrastructure.models;

/**
 * Created by Yansel on 3/20/2017.
 */

public class UserLogin {
    String Email;
    String Password;


    public UserLogin(String email, String password) {
        Email = email;
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
