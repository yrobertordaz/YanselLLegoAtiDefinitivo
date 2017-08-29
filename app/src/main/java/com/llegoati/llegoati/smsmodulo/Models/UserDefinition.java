package com.llegoati.llegoati.smsmodulo.Models;

/**
 * Created by Richard on 06/01/2017.
 */
public class UserDefinition {

    String mName;
    String mLastName;
    String mEmail;
    int mType; // 0:cliente 1:driver

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmLastName() {
        return mLastName;
    }

    public void setmLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    public UserDefinition(String mEmail, String mLastName, String mName, int mType) {
        this.mEmail = mEmail;
        this.mLastName = mLastName;
        this.mName = mName;
        this.mType = mType;
    }
}

