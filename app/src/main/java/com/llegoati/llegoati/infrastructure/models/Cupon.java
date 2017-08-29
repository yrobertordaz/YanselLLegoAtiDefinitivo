package com.llegoati.llegoati.infrastructure.models;

/**
 * Created by Yansel on 5/12/2017.
 */

public class Cupon {
    String IdCoupon;
    String Code;
    String Category;
    Double Efective;
    String DateConsumedByClient;

    public Cupon(String idCoupon, String code, String category, Double efective, String dateConsumedByClient) {
        IdCoupon = idCoupon;
        Code = code;
        Category = category;
        Efective = efective;
        DateConsumedByClient = dateConsumedByClient;
    }

    public String getDateConsumedByClient() {
        return DateConsumedByClient;
    }

    public void setDateConsumedByClient(String dateConsumedByClient) {
        DateConsumedByClient = dateConsumedByClient;
    }

    public Double getEfective() {
        return Efective;
    }

    public void setEfective(Double efective) {
        Efective = efective;
    }

    public String getIdCoupon() {
        return IdCoupon;
    }

    public void setIdCoupon(String idCoupon) {
        IdCoupon = idCoupon;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
