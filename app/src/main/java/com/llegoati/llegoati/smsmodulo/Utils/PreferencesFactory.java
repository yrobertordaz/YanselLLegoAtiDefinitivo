package com.llegoati.llegoati.smsmodulo.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.llegoati.llegoati.smsmodulo.Models.UserDefinition;


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
    private static final String USER_STATE = "USER_STATE";



    public void setTravelState(boolean mState){
        mEditor.putBoolean(this.TRAVEL_STATE,mState);
        mEditor.apply();
        mEditor.commit();
    }

    public void setUserState(int state){
        mEditor.putInt(USER_STATE,state);
        mEditor.apply();
        mEditor.commit();
    }

    public int getUserState(){return mPreferences.getInt(USER_STATE,Constants.USER_STATE_INITIATE);}

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

    public void addUserInfo(UserDefinition mUser){

        mEditor.putString(this.USER_NAME,mUser.getmName());
        mEditor.putString(this.USER_LAST_NAME,mUser.getmLastName());
        mEditor.putString(this.USER_EMAIL,mUser.getmEmail());
        mEditor.putInt(this.USER_TYPE,mUser.getmType());
        mEditor.putBoolean(this.USER_ALREDY_EXIST,true);
        mEditor.apply();
        mEditor.commit();



    }

    public boolean userAlreadyExist(){
        return mPreferences.getBoolean(this.USER_ALREDY_EXIST,false);
    }

    public UserDefinition getUser(){
        if(userAlreadyExist())
            return new UserDefinition(
                    mPreferences.getString(this.USER_EMAIL,"User"),
                    mPreferences.getString(this.USER_LAST_NAME,"User"),
                    mPreferences.getString(this.USER_NAME,"User"),
                    mPreferences.getInt(this.USER_TYPE,-1)
            );
            else
        return null;
    }





}
