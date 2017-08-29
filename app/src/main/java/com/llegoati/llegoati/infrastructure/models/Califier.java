package com.llegoati.llegoati.infrastructure.models;

/**
 * Created by Yansel on 22/11/2016.
 */

public class Califier {
    String Id;
    String CalifierName;
    int Code;
    String CalifierImage;

    public Califier(String id, String califierName, int code, String califierImage) {
        Id = id;
        CalifierName = califierName;
        Code = code;
        CalifierImage = califierImage;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCalifierName() {
        return CalifierName;
    }

    public void setCalifierName(String califierName) {
        CalifierName = califierName;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getCalifierImage() {
        return CalifierImage;
    }

    public void setCalifierImage(String califierImage) {
        CalifierImage = califierImage;
    }
}
