package com.llegoati.llegoati.exceptions;

import java.io.IOException;

/**
 * Created by Yansel on 4/14/2017.
 */

public class UserExistException extends IOException {
    public UserExistException(String message) {
        super(message);
    }
}
