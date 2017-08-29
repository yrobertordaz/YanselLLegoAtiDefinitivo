package com.llegoati.llegoati.infrastructure.models;

/**
 * Created by Yansel on 30/11/2016.
 */

public class ProductDetail {
    private String Id;
    private String Sku;
    private String Photo1;
    private String Photo2;
    private String Photo3;
    private String Photo4;
    private String Photo5;
    private double UnitPrice;
    private float ValueRanking;
    private Seller Seller;
    private AttributesProductInfo[] AttributesProduct;
    private Comment[] Comments;
    private int CountShop;
    private com.llegoati.llegoati.infrastructure.models.Subcategory Subcategory;
    private Califier Califier;
    private Double PointByUnit;
    private Boolean Messenger;


    public Comment[] getComments() {
        return Comments;
    }

    public void setComments(Comment[] comments) {
        Comments = comments;
    }



    public ProductDetail(String id, String sku, String photo1, String photo2, String photo3, String photo4, String photo5, double unitPrice, float valueRanking, com.llegoati.llegoati.infrastructure.models.Seller seller, AttributesProductInfo[] attributesProduct, int countShop, com.llegoati.llegoati.infrastructure.models.Subcategory subCategory, Califier califier, Double pointByUnit, Boolean messenger) {
        Id = id;
        Sku = sku;
        Photo1 = photo1;
        Photo2 = photo2;
        Photo3 = photo3;
        Photo4 = photo4;
        Photo5 = photo5;
        UnitPrice = unitPrice;
        ValueRanking = valueRanking;
        Seller = seller;
        AttributesProduct = attributesProduct;
        CountShop = countShop;
        this.Subcategory = subCategory;
        Califier = califier;
        PointByUnit = pointByUnit;
        Messenger = messenger;
    }

    public Double getPointByUnit() {
        return PointByUnit;
    }

    public void setPointByUnit(Double pointByUnit) {
        PointByUnit = pointByUnit;
    }

    public AttributesProductInfo[] getAttributesProduct() {
        return AttributesProduct;
    }

    public void setAttributesProduct(AttributesProductInfo[] attributesProduct) {
        AttributesProduct = attributesProduct;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getSku() {
        return Sku;
    }

    public void setSku(String sku) {
        Sku = sku;
    }

    public String getPhoto1() {
        return Photo1;
    }

    public void setPhoto1(String photo1) {
        Photo1 = photo1;
    }

    public String getPhoto2() {
        return Photo2;
    }

    public void setPhoto2(String photo2) {
        Photo2 = photo2;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        UnitPrice = unitPrice;
    }

    public float getValueRanking() {
        return ValueRanking;
    }

    public void setValueRanking(float valueRanking) {
        ValueRanking = valueRanking;
    }

    public com.llegoati.llegoati.infrastructure.models.Seller getSeller() {
        return Seller;
    }

    public void setSeller(com.llegoati.llegoati.infrastructure.models.Seller seller) {
        Seller = seller;
    }

    public String getPhoto3() {
        return Photo3;
    }

    public void setPhoto3(String photo3) {
        Photo3 = photo3;
    }

    public String getPhoto4() {
        return Photo4;
    }

    public void setPhoto4(String photo4) {
        Photo4 = photo4;
    }

    public String getPhoto5() {
        return Photo5;
    }

    public void setPhoto5(String photo5) {
        Photo5 = photo5;
    }

    public int getCountShop() {
        return CountShop;
    }

    public void setCountShop(int countShop) {
        CountShop = countShop;
    }

    public com.llegoati.llegoati.infrastructure.models.Subcategory getSubCategory() {
        return Subcategory;
    }

    public void setSubCategory(com.llegoati.llegoati.infrastructure.models.Subcategory subCategory) {
        this.Subcategory = subCategory;
    }

    public com.llegoati.llegoati.infrastructure.models.Califier getCalifier() {
        return Califier;
    }

    public void setCalifier(com.llegoati.llegoati.infrastructure.models.Califier califier) {
        Califier = califier;
    }

    public Boolean getMessenger() {
        return Messenger;
    }

    public void setMessenger(Boolean messenger) {
        Messenger = messenger;
    }
}
