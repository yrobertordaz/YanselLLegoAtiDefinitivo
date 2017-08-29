package com.llegoati.llegoati.infrastructure.models;

/**
 * Created by Yansel on 6/3/2017.
 */

public class RequestProductItem {

    private String IdProduct;
    private String Photo;
    private Seller Seller;
    private Califier Califier;
    private String NameSubcategory;
    private String Sku;
    private Double ProductPrice;
    private Integer ProductCant;
    RequestProductSubcategory Subcategory;
    String ProductImage;

    public RequestProductItem(String idProduct, String photo, com.llegoati.llegoati.infrastructure.models.Seller seller,
                              com.llegoati.llegoati.infrastructure.models.Califier califier, String nameSubcategory,
                              String sku, Double productPrice, Integer productCant, RequestProductSubcategory requestProductSubcategory, String productImage) {
        IdProduct = idProduct;
        Photo = photo;
        Seller = seller;
        Califier = califier;
        NameSubcategory = nameSubcategory;
        Sku = sku;
        ProductPrice = productPrice;
        ProductCant = productCant;
        Subcategory = requestProductSubcategory;
        ProductImage = productImage;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    public RequestProductSubcategory getSubcategory() {
        return Subcategory;
    }

    public void setSubcategory(RequestProductSubcategory subcategory) {
        Subcategory = subcategory;
    }

    public Integer getProductCant() {
        return ProductCant;
    }

    public void setProductCant(Integer productCant) {
        ProductCant = productCant;
    }

    public String getIdProduct() {
        return IdProduct;
    }

    public void setIdProduct(String idProduct) {
        IdProduct = idProduct;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public com.llegoati.llegoati.infrastructure.models.Seller getSeller() {
        return Seller;
    }

    public void setSeller(com.llegoati.llegoati.infrastructure.models.Seller seller) {
        Seller = seller;
    }

    public com.llegoati.llegoati.infrastructure.models.Califier getCalifier() {
        return Califier;
    }

    public void setCalifier(com.llegoati.llegoati.infrastructure.models.Califier califier) {
        Califier = califier;
    }

    public String getNameSubcategory() {
        return NameSubcategory;
    }

    public void setNameSubcategory(String nameSubcategory) {
        NameSubcategory = nameSubcategory;
    }

    public String getSku() {
        return Sku;
    }

    public void setSku(String sku) {
        Sku = sku;
    }

    public Double getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(Double productPrice) {
        ProductPrice = productPrice;
    }


    public boolean getHaveComments() {
        return false;
    }
}
