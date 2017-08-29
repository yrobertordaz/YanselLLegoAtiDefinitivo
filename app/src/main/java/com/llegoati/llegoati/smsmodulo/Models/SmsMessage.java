package com.llegoati.llegoati.smsmodulo.Models;

/**
 * Created by Richard on 3/19/2017.
 */

public class SmsMessage {

    String mBody;
    String idSms;
    int mId;

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    String idME;
    String idSeller;
    String NextSms;
    //int responce;
    String date;
    boolean isNew;
    boolean isDelete;
    boolean isResponce;


    public SmsMessage(String id, String idClient, String idVendedor, String mBody) {
        this.idSms = id;
        this.idME = idClient;
        this.idSeller = idVendedor;
        this.mBody = mBody;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIdME() {
        return idME;
    }

    public void setIdME(String idME) {
        this.idME = idME;
    }

    public String getIdSms() {
        return idSms;
    }

    public void setIdSms(String idSms) {
        this.idSms = idSms;
    }

    public String getIdSeller() {
        return idSeller;
    }

    public void setIdSeller(String idSeller) {
        this.idSeller = idSeller;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public boolean isResponce() {
        return isResponce;
    }

    public void setResponce(boolean responce) {
        isResponce = responce;
    }

    public String getmBody() {
        return mBody;
    }

    public void setmBody(String mBody) {
        this.mBody = mBody;
    }

    public String getNextSms() {
        return NextSms;
    }

    public void setNextSms(String nextSms) {
        NextSms = nextSms;
    }
}
