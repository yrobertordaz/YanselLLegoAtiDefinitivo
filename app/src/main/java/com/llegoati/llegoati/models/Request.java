package com.llegoati.llegoati.models;


import com.llegoati.llegoati.infrastructure.models.RequestProductItem;
import com.llegoati.llegoati.infrastructure.models.User;

/**
 * Created by Yansel on 5/19/2017.
 */

public class Request {

    String OrderDate;
    String Id;
    String GeneratedOrderIdentifier;
    Double TotalPrice;
    Float AcumulatedPoints;
    Integer State;
    RequestProductItem[] Products;
    Double MessengerPrice;
    Double LowerVip;

    Double LowerSeller;
    Double LowerPoints;
    Double LowerLotery;
    User Client;
    String AddresContact;
    String PhoneContact;
    String MovilContact;
    String Enabled;


    public Request(String orderDate, String id, String generatedOrderIdentifier, Double totalPrice, Float acumulatedPoints, Integer state,
                   RequestProductItem[] products, Double messengerPrice, Double lowerVip, Double lowerSeller, Double lowerPoints, Double lowerLotery, User client,
                   String addresContact,
                   String phoneContact,
                   String movilContact,
                   String enabled) {
        OrderDate = orderDate;
        Id = id;
        GeneratedOrderIdentifier = generatedOrderIdentifier;
        TotalPrice = totalPrice;
        AcumulatedPoints = acumulatedPoints;
        State = state;
        Products = products;
        MessengerPrice = messengerPrice;
        LowerVip = lowerVip;
        LowerSeller = lowerSeller;
        LowerPoints = lowerPoints;
        LowerLotery = lowerLotery;
        Client = client;
        AddresContact = addresContact;
        PhoneContact = phoneContact;
        MovilContact = movilContact;
        Enabled = enabled;
    }

    public String getEnabled() {
        return Enabled;
    }

    public void setEnabled(String enabled) {
        Enabled = enabled;
    }

    public String getAddresContact() {
        return AddresContact;
    }

    public void setAddresContact(String addresContact) {
        AddresContact = addresContact;
    }

    public String getPhoneContact() {
        return PhoneContact;
    }

    public void setPhoneContact(String phoneContact) {
        PhoneContact = phoneContact;
    }

    public String getMovilContact() {
        return MovilContact;
    }

    public void setMovilContact(String movilContact) {
        MovilContact = movilContact;
    }

    public User getClient() {
        return Client;
    }

    public void setClient(User client) {
        Client = client;
    }

    public Double getLowerPoints() {
        return LowerPoints;
    }

    public void setLowerPoints(Double lowerPoints) {
        LowerPoints = lowerPoints;
    }

    public Double getLowerLotery() {
        return LowerLotery;
    }

    public void setLowerLotery(Double lowerLotery) {
        LowerLotery = lowerLotery;
    }

    public Double getLowerSeller() {
        return LowerSeller;
    }

    public void setLowerSeller(Double lowerSeller) {
        LowerSeller = lowerSeller;
    }

    public Double getLowerVip() {
        return LowerVip;
    }

    public void setLowerVip(Double lowerVip) {
        LowerVip = lowerVip;
    }

    public Double getMessengerPrice() {
        return MessengerPrice;
    }

    public void setMessengerPrice(Double messengerPrice) {
        MessengerPrice = messengerPrice;
    }

    public RequestProductItem[] getProducts() {
        return Products;
    }

    public void setProducts(RequestProductItem[] products) {
        Products = products;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getGeneratedOrderIdentifier() {
        return GeneratedOrderIdentifier;
    }

    public void setGeneratedOrderIdentifier(String generatedOrderIdentifier) {
        GeneratedOrderIdentifier = generatedOrderIdentifier;
    }

    public Double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        TotalPrice = totalPrice;
    }

    public Float getAcumulatedPoints() {
        return AcumulatedPoints;
    }

    public void setAcumulatedPoints(Float acumulatedPoints) {
        AcumulatedPoints = acumulatedPoints;
    }

    public Integer getState() {
        return State;
    }

    public void setState(Integer state) {
        State = state;
    }
}
