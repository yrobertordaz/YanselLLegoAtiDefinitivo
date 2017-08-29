package com.llegoati.llegoati.infrastructure.models;

/**
 * Created by Yansel on 04/12/2016.
 */

public class AttributesProductInfo {
    private String AttributeName;
    private String AttributeProduct;
    private String Id;

    public AttributesProductInfo(String attributeName, String attributeProduct, String id) {
        AttributeName = attributeName;
        AttributeProduct = attributeProduct;
        Id = id;
    }

    public String getAttributeName() {
        return AttributeName;
    }

    public void setAttributeName(String attributeName) {
        AttributeName = attributeName;
    }

    public String getAttributeProduct() {
        return AttributeProduct;
    }

    public void setAttributeProduct(String attributeProduct) {
        AttributeProduct = attributeProduct;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
