package com.llegoati.llegoati.models;

/**
 * Created by Yansel on 5/12/2017.
 */

public class UserModify {
    String Id;
    String Name;
    String Email;
    String Phone;
    String Sex;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public UserModify(String id, String name, String email, String phone, String sex) {
        Id = id;
        Name = name;
        Email = email;
        Phone = phone;
        Sex = sex;
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
}
