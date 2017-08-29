package com.llegoati.llegoati.infrastructure.models;

/**
 * Created by Yansel on 09/10/2016.
 */
public class Subcategory {
    private int subCategoryId;
    private String Id;
    private String NameSubcategory;
    private String Image;


    public Subcategory(String id, String nameSubcategory, String image) {
        Id = id;
        NameSubcategory = nameSubcategory;
        Image = image;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNameSubcategory() {
        return NameSubcategory;
    }

    public void setNameSubcategory(String nameSubcategory) {
        NameSubcategory = nameSubcategory;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
