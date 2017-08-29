package com.llegoati.llegoati.infrastructure.models;

/**
 * Created by Yansel on 1/19/2017.
 */

public class MessengerPrice {
    String Id;
    double Value;
    DestineInformation Destine;

    public MessengerPrice(String id, double value, DestineInformation destine) {
        Id = id;
        Value = value;
        Destine = destine;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public double getValue() {
        return Value;
    }

    public void setValue(double value) {
        Value = value;
    }

    public DestineInformation getDestine() {
        return Destine;
    }

    public void setDestine(DestineInformation destine) {
        Destine = destine;
    }
}
