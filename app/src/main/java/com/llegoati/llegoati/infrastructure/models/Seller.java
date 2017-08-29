package com.llegoati.llegoati.infrastructure.models;

/**
 * Created by Yansel on 15/11/2016.
 */

public class Seller {
    private String Id;
    private String Name;
    private double LowerPrice;
    private double LowerParameter;

    public Seller(String id, String name, double lowerPrice, double lowerParameter) {
        Id = id;
        Name = name;
        LowerPrice = lowerPrice;
        LowerParameter = lowerParameter;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getLowerPrice() {
        return LowerPrice;
    }

    public void setLowerPrice(double lowerPrice) {
        LowerPrice = lowerPrice;
    }

    public double getLowerParameter() {
        return LowerParameter;
    }

    public void setLowerParameter(double lowerParameter) {
        LowerParameter = lowerParameter;
    }
}
