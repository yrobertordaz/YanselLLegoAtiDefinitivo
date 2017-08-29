package com.llegoati.llegoati.infrastructure.models;

import com.llegoati.llegoati.adapter.ProductHomeRecyclerViewAdapter;

/**
 * Created by Yansel on 1/6/2017.
 */

public class HomeItem {
    private String photo;
    private ProductHomeRecyclerViewAdapter.ViewType type;

    public HomeItem(String photo, ProductHomeRecyclerViewAdapter.ViewType type) {
        this.photo = photo;
        this.type = type;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public ProductHomeRecyclerViewAdapter.ViewType getType() {
        return type;
    }

    public void setType(ProductHomeRecyclerViewAdapter.ViewType type) {
        this.type = type;
    }

}
