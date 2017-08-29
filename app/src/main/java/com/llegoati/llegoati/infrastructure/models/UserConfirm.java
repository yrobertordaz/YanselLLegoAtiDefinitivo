package com.llegoati.llegoati.infrastructure.models;

/**
 * Created by Yansel on 3/20/2017.
 */

public class UserConfirm {
    String Email;
    String ConfirmationCode;

    public UserConfirm(String email, String confirmationCode) {
        Email = email;
        ConfirmationCode = confirmationCode;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getConfirmationCode() {
        return ConfirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        ConfirmationCode = confirmationCode;
    }
}
