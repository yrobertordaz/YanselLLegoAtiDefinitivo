package com.llegoati.llegoati.infrastructure.models;

/**
 * Created by Yansel on 1/5/2017.
 */


public class ShoppingCartItem {
    private Long id;
    String photo;
    int quantity;
    String productId;
    double price;
    private String name;
    private Seller seller;
    private Double PointByUnit;

    public ShoppingCartItem() {
    }

    public ShoppingCartItem(String photo, int quantity, String productId, double price, String name, Seller seller, Double pointByUnit) {

        this.photo = photo;
        this.quantity = quantity;
        this.productId = productId;
        this.price = price;
        this.name = name;
        this.seller = seller;
        PointByUnit = pointByUnit;
    }

    public ShoppingCartItem(String photo, int quantity, String productId, double price, String name, Double pointByUnit) {
        this.photo = photo;
        this.quantity = quantity;
        this.productId = productId;
        this.price = price;
        this.name = name;
        PointByUnit = pointByUnit;
    }

    public ShoppingCartItem(Long id, String photo, int quantity, String productId, double price, String name) {
        this.id = id;
        this.photo = photo;
        this.quantity = quantity;
        this.productId = productId;
        this.price = price;
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Double getPointByUnit() {
        return PointByUnit;
    }

    public void setPointByUnit(Double pointByUnit) {
        PointByUnit = pointByUnit;
    }
}
