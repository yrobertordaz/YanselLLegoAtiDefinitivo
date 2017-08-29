package com.llegoati.llegoati.models;

/**
 * Author: Yansel
 * Created by: ModelGenerator on 7/13/2017
 */
public class Price {
// TODO: complemented needed maybe.

    double mPrice;
    double mRebaja;
    double mTotal;

    public Price(double mPrice, double mRebaja) {
        this.mPrice = mPrice;
        this.mRebaja = mRebaja;
        this.mTotal = getmPrice() - getmRebaja();
    }

    public double getmPrice() {
        return mPrice;
    }

    public void setmPrice(double mPrice) {
        this.mPrice = mPrice;
    }

    public double getmRebaja() {
        return mRebaja;
    }

    public void setmRebaja(double mRebaja) {
        this.mRebaja = mRebaja;
    }

    public double getmTotal() {
        return mTotal;
    }

    public void setmTotal(double mTotal) {
        this.mTotal = mTotal;
    }
}