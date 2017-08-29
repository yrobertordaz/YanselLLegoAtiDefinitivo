package com.llegoati.llegoati.infrastructure.models;

import com.llegoati.llegoati.infrastructure.concrete.RemoteRepository;

import java.util.List;

/**
 * Created by Yansel on 09/10/2016.
 */
public class Category {

    private int categoryId;
    private String Id;
    private String CategoryName;
    private List<Subcategory> Subcategories;


    public Category() {
        categoryId = RemoteRepository.CATEGORY_COUNT++;
    }

    public Category(String id, String categoryName, List<Subcategory> subcategories) {

        Id = id;
        CategoryName = categoryName;
        Subcategories = subcategories;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public List<Subcategory> getSubcategories() {
        return Subcategories;
    }

    public void setSubcategories(List<Subcategory> subcategories) {
        Subcategories = subcategories;
    }
}
