package com.llegoati.llegoati.infrastructure.models;

/**
 * Created by Yansel on 6/3/2017.
 */

public class RequestProductSubcategory {
    private String Id;
    private String NameSubcategory;
    private String Image;

    public RequestProductSubcategory(String id, String nameSubcategory, String image) {
        Id = id;
        NameSubcategory = nameSubcategory;
        Image = image;
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
