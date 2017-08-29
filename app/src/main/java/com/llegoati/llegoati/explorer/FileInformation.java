package com.llegoati.llegoati.explorer;

/**
 * Created by Richard on 01/09/2016.
 */
public class FileInformation {

    public String mPatch;

    public String getPatch() {
        return mPatch;
    }

    public void setPatch(String patch) {
        this.mPatch = patch;
    }

    public boolean ismIsFolder() {
        return mIsFolder;
    }

    public void setmIsFolder(boolean mIsFolder) {
        this.mIsFolder = mIsFolder;
    }

    public String mName;

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public boolean mIsFolder;


    public  FileInformation(String name,Boolean mIsFolder,String patch){

        this.mName = name;
        this.mPatch = patch;
        this.mIsFolder = mIsFolder;


    }


}
