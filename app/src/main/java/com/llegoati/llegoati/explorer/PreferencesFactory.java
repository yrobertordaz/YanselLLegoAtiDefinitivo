package com.llegoati.llegoati.explorer;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.io.File;


/**
 * Created by Richard on 06/01/2017.
 */
public class PreferencesFactory {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    public static PreferencesFactory getInstance(Context mActivity){ return new PreferencesFactory(mActivity);}

    public PreferencesFactory(Context mActivity) {
        this.mPreferences = mActivity.getSharedPreferences(PREFERENCES_ID,Context.MODE_PRIVATE);
        this.mEditor = mPreferences.edit();
    }

    private static final String PREFERENCES_ID = "com.llegoati.taxi.preferences";
    private static final String USER_NAME = "USER_NAME";
    private static final String USER_LAST_NAME = "USER_LAST_NAME";
    private static final String USER_EMAIL = "USER_EMAIL";
    private static final String USER_TYPE = "USER_TYPE";
    private static final String USER_ALREDY_EXIST = "USER_EXIST";
    private static final String ID_PETITION = "ID_PETITION";
    private static final String TRAVEL_STATE = "TRAVEL_STATE";
    private static final String DB_PATH = "DB_PATH";
    private static final String DB_PATH_OLD = "DB_PATH_OLD";
    private static final String DB_FACTORIE = "DB_FACTORIE";


    public void setTravelState(boolean mState){
        mEditor.putBoolean(this.TRAVEL_STATE,mState);
        mEditor.apply();
        mEditor.commit();
    }

    public boolean getTravelState(){
        return mPreferences.getBoolean(this.TRAVEL_STATE,false);
    }

    public void  addIdPetition(int mIdPetition){
        mEditor.putInt(this.ID_PETITION,mIdPetition);
        mEditor.apply();
        mEditor.commit();
    }

    public int getIdPetition(){
        return mPreferences.getInt(this.ID_PETITION,-1);
    }

    public boolean userAlreadyExist(){
        return mPreferences.getBoolean(this.USER_ALREDY_EXIST,false);
    }


    public String getDbPatch(){
        return mPreferences.getString(DB_PATH,null);
    }

    public void setDbPath(String path){

        mEditor.putString(DB_PATH_OLD,getDbPatch());
        mEditor.putString(DB_PATH,path);
        mEditor.apply();
        mEditor.commit();
    }


    public boolean isNewPath() {
        return
                getDbPatch().equals(
                        mPreferences.getString(DB_PATH_OLD,null)
                );
    }


    public boolean existDb(){

        if(getDbPatch() == null)
            return false;
        else {
            File mF = new File(getDbPatch());
            return mF.exists();
        }
    }


    public void addDataBase(LLegoDataBaseFactory mDataBaseElement){

        LLegoDataBaseFactory[] existLLegoDataBaseFactory = getDataBaseFactories();

        if (!alreadyExist(existLLegoDataBaseFactory,mDataBaseElement)) {

            Gson mGson = new Gson();
            String mRsult;
            LLegoDataBaseFactory[] temp;
            if (existLLegoDataBaseFactory != null) {
                temp = new LLegoDataBaseFactory[existLLegoDataBaseFactory.length + 1];
                int i = 0;
                for (i = 0; i < existLLegoDataBaseFactory.length; i++)
                    temp[i] = existLLegoDataBaseFactory[i];

                temp[i] = mDataBaseElement;
            } else
                temp = new LLegoDataBaseFactory[]{mDataBaseElement};


            mRsult = mGson.toJson(temp);
            mEditor.putString(DB_FACTORIE, mRsult);
            mEditor.apply();
            mEditor.commit();
        }


    }

    private boolean alreadyExist(LLegoDataBaseFactory[] existLLegoDataBaseFactory, LLegoDataBaseFactory mDataBaseElement) {

        for (LLegoDataBaseFactory mDb:  existLLegoDataBaseFactory)
            if (mDb.getmName().equals(mDataBaseElement.getmName()) && mDb.getmUbication().equals(mDataBaseElement.getmUbication()))
                return true;

        return false;
    }


    public LLegoDataBaseFactory[] getDataBaseFactories(){
        String mjSon = mPreferences.getString(DB_FACTORIE,null);
        Gson mGson = new Gson();
        if (mjSon!=null){
            return mGson.fromJson(mjSon,LLegoDataBaseFactory[].class);
        }else return new LLegoDataBaseFactory[0];


    }
}
