package com.llegoati.llegoati.infrastructure.models;

/**
 * Created by Yansel on 3/27/2017.
 */

public class User {
    String Id;
    String Token;
    String Name;
    String LastName;
    String Email;
    String Phone;
    String Sex;
    Double AcumulatedPoint;
    Double AcumulatedPointInIntervalVip;
    String Specification;
    Contact[] Contacts;

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    String Password;

    String Image;
    Boolean Vip;

    public User(String id, String token, String name, String lastName, String email, String phone, String sex, Double acumulatedPoint, Double acumulatedPointInIntervalVip, String specification, Contact[] contacts, String password, String image, Boolean vip) {
        Id = id;
        Token = token;
        Name = name;
        LastName = lastName;
        Email = email;
        Phone = phone;
        Sex = sex;
        AcumulatedPoint = acumulatedPoint;
        AcumulatedPointInIntervalVip = acumulatedPointInIntervalVip;
        Specification = specification;
        Contacts = contacts;
        Password = password;
        Image = image;
        Vip = vip;
    }


    public Contact[] getContacts() {
        return Contacts;
    }

    public void setContacts(Contact[] contacts) {
        Contacts = contacts;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public Boolean getVip() {
        return Vip;
    }

    public void setVip(Boolean vip) {
        Vip = vip;
    }

    public String getSpecification() {
        return Specification;
    }

    public void setSpecification(String specification) {
        Specification = specification;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public Double getAcumulatedPoint() {
        return AcumulatedPoint;
    }

    public void setAcumulatedPoint(Double acumulatedPoint) {
        AcumulatedPoint = acumulatedPoint;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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
