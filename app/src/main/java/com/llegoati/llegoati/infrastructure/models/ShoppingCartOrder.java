package com.llegoati.llegoati.infrastructure.models;

import java.util.Iterator;

/**
 * Created by Yansel on 7/6/2017.
 */

public class ShoppingCartOrder {
    public String getNameContact() {
        return nameContact;
    }

    public void setNameContact(String nameContact) {
        this.nameContact = nameContact;
    }

    String nameContact;
    String clientId;
    Double consumedCuc;
    String loteryId;
    Double pointEquivalent;
    Iterator<ShoppingCartItem> items;
    private Double messengerPrice;
    private Double lowerVip;
    private String phoneContact;
    private String addressContact;

    public ShoppingCartOrder(Double consumedCuc, String loteryId, Double pointEquivalent, Iterator<ShoppingCartItem> items, Double messengerPrice, Double lowerVip, String phoneContact, String addressContact) {
        this.consumedCuc = consumedCuc;
        this.loteryId = loteryId;
        this.pointEquivalent = pointEquivalent;
        this.items = items;
        this.messengerPrice = messengerPrice;
        this.lowerVip = lowerVip;
        this.phoneContact = phoneContact;
        this.addressContact = addressContact;
    }

    public ShoppingCartOrder(String clientId, Double consumedCuc, String loteryId, Double pointEquivalent, Iterator<ShoppingCartItem> items, Double messengerPrice, Double lowerVip) {
        this.clientId = clientId;
        this.consumedCuc = consumedCuc;
        this.loteryId = loteryId;
        this.pointEquivalent = pointEquivalent;
        this.items = items;
        this.messengerPrice = messengerPrice;
        this.lowerVip = lowerVip;
    }

    public ShoppingCartOrder(Double consumedCuc, String loteryId, Double pointEquivalent, Iterator<ShoppingCartItem> items, Double messengerPrice, Double lowerVip, String phoneContact, String addressContact, String nameContact) {
        this.consumedCuc = consumedCuc;
        this.loteryId = loteryId;
        this.pointEquivalent = pointEquivalent;
        this.items = items;
        this.messengerPrice = messengerPrice;
        this.lowerVip = lowerVip;
        this.phoneContact = phoneContact;
        this.addressContact = addressContact;
        this.nameContact = nameContact;
    }

    public String getPhoneContact() {
        return phoneContact;
    }

    public void setPhoneContact(String phoneContact) {
        this.phoneContact = phoneContact;
    }

    public String getAddressContact() {
        return addressContact;
    }

    public void setAddressContact(String addressContact) {
        this.addressContact = addressContact;
    }

    public String getClientId() {
        return clientId;
    }


    public Double getConsumedCuc() {
        return consumedCuc;
    }

    public String getLoteryId() {
        return loteryId;
    }

    public Double getPointEquivalent() {
        return pointEquivalent;
    }

    public Iterator<ShoppingCartItem> getItems() {
        return items;
    }

    public Double getMessengerPrice() {
        return messengerPrice;
    }

    public Double getLowerVip() {
        return lowerVip;
    }
}
