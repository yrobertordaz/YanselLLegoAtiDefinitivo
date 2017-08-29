package com.llegoati.llegoati.infrastructure.models;

/**
 * Created by Yansel on 5/4/2017.
 */

public class UserChangePassword {
    String Email;
    String OldPassword;
    String Password;

    public UserChangePassword(String email, String oldPassword, String password) {
        Email = email;
        OldPassword = oldPassword;
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getOldPassword() {
        return OldPassword;
    }

    public void setOldPassword(String oldPassword) {
        OldPassword = oldPassword;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
