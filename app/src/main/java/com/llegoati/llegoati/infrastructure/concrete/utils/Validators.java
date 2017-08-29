package com.llegoati.llegoati.infrastructure.concrete.utils;

import android.text.TextUtils;
import android.util.Patterns;

/**
 * Created by Yansel on 3/13/2017.
 */

public class Validators {
    public static boolean emailValid(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean phoneValid(String phone) {
        return Patterns.PHONE.matcher(phone).matches();
    }

    public static boolean passwordValid(String password) {
        return !TextUtils.isEmpty(password);
    }

    public static boolean passwordConfirmationValid(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
}
