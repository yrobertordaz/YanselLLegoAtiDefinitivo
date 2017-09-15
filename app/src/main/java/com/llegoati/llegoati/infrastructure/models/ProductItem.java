package com.llegoati.llegoati.infrastructure.models;

/**
 * Created by Richard on 17/10/2016.
 */
public class ProductItem {

    //    private int Id;
    private String ProductId;
    private String PhotoUrl;
    private String Photo;
    private float Price;
    private Seller Seller;
    private Califier Califier;
    private String NameSubcategory;
    private String Sku;
    private Double ProductPrice;
    private Subcategory Subcategory;
    private Boolean Messenger;
    private String modifyDate;

    public ProductItem(Boolean messenger) {
//        Id = RemoteRepository.PRODUCT_COUNT++;
        Messenger = messenger;
    }

    public ProductItem(float price, String photo, String productId, Seller seller, Califier califier, String nameSubcategory, Boolean messenger) {
        Price = price;
        Photo = photo;
        this.ProductId = productId;
        this.Seller = seller;
        this.Califier = califier;
        NameSubcategory = nameSubcategory;
        Messenger = messenger;
    }

    public ProductItem(float price, String photo, String productId, Seller seller, Califier califier, String nameSubcategory, Boolean messenger, String modifyDate) {
        Price = price;
        Photo = photo;
        this.ProductId = productId;
        this.Seller = seller;
        this.Califier = califier;
        NameSubcategory = nameSubcategory;
        Messenger = messenger;
        // TODO: 8/4/2017 este parse no lo hace
        this.modifyDate = modifyDate;
    }

    public ProductItem(float price, String photo, String productId, String photoUrl, Seller seller, Califier califier, String nameSubcategory, String sku, Double productPrice, Subcategory subcategory, Boolean messenger) {
        Price = price;
        Photo = photo;
        this.ProductId = productId;
        PhotoUrl = photoUrl;
        this.Seller = seller;
        this.Califier = califier;
        NameSubcategory = nameSubcategory;
        this.Sku = sku;
        ProductPrice = productPrice;
        this.Subcategory = subcategory;
        Messenger = messenger;
    }

    public Boolean getMessenger() {
        return Messenger;
    }

    public void setMessenger(Boolean messenger) {
        Messenger = messenger;
    }

    public Double getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(Double productPrice) {
        ProductPrice = productPrice;
    }

    public String getSku() {
        return Sku;
    }

    public void setSku(String sku) {
        Sku = sku;
    }

    public com.llegoati.llegoati.infrastructure.models.Califier getCalifier() {
        return Califier;
    }

    public void setCalifier(com.llegoati.llegoati.infrastructure.models.Califier califier) {
        Califier = califier;
    }

    public com.llegoati.llegoati.infrastructure.models.Seller getSeller() {
        return Seller;
    }

    public void setSeller(com.llegoati.llegoati.infrastructure.models.Seller seller) {
        Seller = seller;
    }

//    public int getId() {
//        return Id;
//    }
//
//    public void setId(int id) {
//        Id = id;
//    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        this.ProductId = productId;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public String getNameSubcategory() {
        return NameSubcategory;
    }

    public void setNameSubcategory(String nameSubcategory) {
        NameSubcategory = nameSubcategory;
    }

    public String getPhotoUrl() {
        return PhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        PhotoUrl = photoUrl;
    }

    public Subcategory getSubcategory() {
        return Subcategory;
    }
}
