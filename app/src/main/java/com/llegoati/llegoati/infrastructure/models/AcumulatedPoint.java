package com.llegoati.llegoati.infrastructure.models;

/**
 * Created by Yansel on 5/4/2017.
 */

public class AcumulatedPoint {
    private Double UserPoints;
    private Double AcumulatedPointInVipInterval;
    private boolean IsVip;
    private Double OneCucEquivalentTo;
    private Double CucAcumulatedByPoints;
    private String DatePointCaduce;
    private String DateToVip;
    private Integer LowerVip;

    public AcumulatedPoint(Double userPoints, Double acumulatedPointInVipInterval, boolean isVip, Double oneCucEquivalentTo, Double cucAcumulatedByPoints, String datePointCaduce, String dateToVip, Integer lowerVip) {
        UserPoints = userPoints;
        AcumulatedPointInVipInterval = acumulatedPointInVipInterval;
        IsVip = isVip;
        OneCucEquivalentTo = oneCucEquivalentTo;
        CucAcumulatedByPoints = cucAcumulatedByPoints;
        DatePointCaduce = datePointCaduce;
        DateToVip = dateToVip;
        LowerVip = lowerVip;
    }

    public Double getUserPoints() {
        return UserPoints;
    }

    public void setUserPoints(Double userPoints) {
        UserPoints = userPoints;
    }

    public Double getAcumulatedPointInVipInterval() {
        return AcumulatedPointInVipInterval;
    }

    public void setAcumulatedPointInVipInterval(Double acumulatedPointInVipInterval) {
        AcumulatedPointInVipInterval = acumulatedPointInVipInterval;
    }

    public boolean isVip() {
        return IsVip;
    }

    public void setVip(boolean vip) {
        IsVip = vip;
    }

    public Double getOneCucEquivalentTo() {
        return OneCucEquivalentTo;
    }

    public void setOneCucEquivalentTo(Double oneCucEquivalentTo) {
        OneCucEquivalentTo = oneCucEquivalentTo;
    }

    public Double getCucAcumulatedByPoints() {
        return CucAcumulatedByPoints;
    }

    public void setCucAcumulatedByPoints(Double cucAcumulatedByPoints) {
        CucAcumulatedByPoints = cucAcumulatedByPoints;
    }

    public String getDatePointCaduce() {
        return DatePointCaduce;
    }

    public void setDatePointCaduce(String datePointCaduce) {
        DatePointCaduce = datePointCaduce;
    }

    public String getDateToVip() {
        return DateToVip;
    }

    public void setDateToVip(String dateToVip) {
        DateToVip = dateToVip;
    }

    public Integer getLowerVip() {
        return LowerVip;
    }

    public void setLowerVip(Integer lowerVip) {
        LowerVip = lowerVip;
    }
}
