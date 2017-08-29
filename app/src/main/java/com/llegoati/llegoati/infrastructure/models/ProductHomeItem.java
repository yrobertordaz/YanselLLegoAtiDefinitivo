package com.llegoati.llegoati.infrastructure.models;

import com.llegoati.llegoati.adapter.ProductHomeRecyclerViewAdapter;

/**
 * Created by Yansel on 1/21/2017.
 */

public class ProductHomeItem extends HomeItem {
    private String productId;
    private String name;
    private String nameSeller;
    private double productPrice;

    public ProductHomeItem(String photo, String name, ProductHomeRecyclerViewAdapter.ViewType type, String productId, String nameSeller, double productPrice) {
        super(photo, type);
        this.name = name;
        this.productId = productId;
        this.nameSeller = nameSeller;
        this.productPrice = productPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameSeller() {
        return nameSeller;
    }

    public void setNameSeller(String nameSeller) {
        this.nameSeller = nameSeller;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
}
