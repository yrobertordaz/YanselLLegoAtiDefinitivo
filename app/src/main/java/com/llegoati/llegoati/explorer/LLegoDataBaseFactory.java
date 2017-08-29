package com.llegoati.llegoati.explorer;

/**
 * Created by Richard on 8/15/2017.
 */

public class LLegoDataBaseFactory {

    String mName;
    String mType;
    String mUbication;

    public LLegoDataBaseFactory(String mName, String mType, String mUbication) {
        this.mName = mName;
        this.mType = mType;
        this.mUbication = mUbication;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public String getmUbication() {
        return mUbication;
    }

    public void setmUbication(String mUbication) {
        this.mUbication = mUbication;
    }


}
