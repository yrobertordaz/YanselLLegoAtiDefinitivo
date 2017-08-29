package com.llegoati.llegoati.infrastructure.models;

/**
 * Created by Yansel on 5/6/2017.
 */

public class CheckoutLoteryCode {
    String Id;
    Double Rebaja;
    String ActiveDateFrom;
    String ExpireDate;

    Cupon Cupon;

    public CheckoutLoteryCode(Double rebaja, String activeDateFrom, String expireDate, com.llegoati.llegoati.infrastructure.models.Cupon cupon, String id) {
        Rebaja = rebaja;
        ActiveDateFrom = activeDateFrom;
        ExpireDate = expireDate;
        Cupon = cupon;
        Id = id;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Double getRebaja() {
        return Rebaja;
    }

    public void setRebaja(Double rebaja) {
        Rebaja = rebaja;
    }

    public String getActiveDateFrom() {
        return ActiveDateFrom;
    }

    public void setActiveDateFrom(String activeDateFrom) {
        ActiveDateFrom = activeDateFrom;
    }

    public String getExpireDate() {
        return ExpireDate;
    }

    public void setExpireDate(String expireDate) {
        ExpireDate = expireDate;
    }

    public com.llegoati.llegoati.infrastructure.models.Cupon getCupon() {
        return Cupon;
    }

    public void setCupon(com.llegoati.llegoati.infrastructure.models.Cupon cupon) {
        Cupon = cupon;
    }
}
