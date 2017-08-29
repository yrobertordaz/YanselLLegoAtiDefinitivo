package com.llegoati.llegoati.models;

/**
 * Author: Yansel
 * Created by: ModelGenerator on 7/13/2017
 */
public class Adds {
    private String Id;    // 14519fb0_d85c_4
    private String IdSubcateg;    // 00000000_0000_0
    private Price Price;
    private PhoneNumber PhoneNumber;
    private String Title;    // Anuncio vacio p
    private String Url;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getIdSubcateg() {
        return IdSubcateg;
    }

    public void setIdSubcateg(String idSubcateg) {
        IdSubcateg = idSubcateg;
    }

    public com.llegoati.llegoati.models.Price getPrice() {
        return Price;
    }

    public void setPrice(com.llegoati.llegoati.models.Price price) {
        Price = price;
    }

    public com.llegoati.llegoati.models.PhoneNumber getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(com.llegoati.llegoati.models.PhoneNumber phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}