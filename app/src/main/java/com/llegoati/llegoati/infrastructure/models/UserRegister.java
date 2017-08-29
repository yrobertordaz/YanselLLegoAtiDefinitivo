package com.llegoati.llegoati.infrastructure.models;

/**
 * Created by Yansel on 3/9/2017.
 */

public class UserRegister {
    String Name;
    String Email;
    String Phone;
    String Sex;
    String Password;

    public UserRegister(String name, String email, String phone, String sex, String password) {
        Name = name;
        Email = email;
        Phone = phone;
        Sex = sex;
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
