package com.llegoati.llegoati.smsmodulo.Controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.llegoati.llegoati.R;
import com.llegoati.llegoati.smsmodulo.Infraestructure.concrete.Repository;

import com.llegoati.llegoati.smsmodulo.Utils.Constants;
import com.llegoati.llegoati.smsmodulo.Utils.Utils;
import com.llegoati.llegoati.smsmodulo.Utils.UtilsFunctions;
import com.llegoati.llegoati.smsmodulo.View.SmsMainActivity;


/**
 * Created by Richard on 14/11/2016.
 */
public class ControlSms {

    String mPhoneNumber;
    String mMessage;
    Long mTyme;
    Context mContext;
    String[] mContent;


    public ControlSms(String mBody, String mPhoneNumber, Long mTyme, Context mContext) {
        this.mMessage = mBody;
        this.mPhoneNumber = mPhoneNumber;
        this.mTyme = mTyme;
        this.mContext = mContext;
      //  Log.e("ControlSms Cosntructor","estoy aki");
    }

    public static ControlSms getInstance(Bundle mBundel,Context mContext){
        return new ControlSms(mBundel.getString("message"),mBundel.getString("phoneNumber"),mBundel.getLong("time"),mContext);
    }
    Repository mRepository;
    public void init(){
        mRepository = new Repository(mContext);
        //mMessage = Utils.smsDecipher(mMessage);
        if(mMessage.contains(Constants.KEY_SMS1) && mMessage.contains(Constants.KEY_SMS2) && mMessage.contains(Constants.KEY_SMS3)){
            mMessage = mMessage.replace(Constants.KEY_SMS1,"");
            mMessage = mMessage.replace(Constants.KEY_SMS2,"");
            mMessage = mMessage.replace(Constants.KEY_SMS3,"");
          //  mContent = mMessage.split(Constants.SEPARATOR);
            goToActivity(SmsMainActivity.class);

        }

    }

    private void goToActivity(Class mClass) {

        final Intent mIntent = new Intent(mContext, mClass);



        int mIdSms = 0;
        String contenido = mMessage;
        char[] mContent = contenido.toCharArray();
        int mIDFlujo = Integer.parseInt(String.valueOf(mContent[Constants.POSITION_FLUJO]));
        //// TODO: 4/1/2017 ver como coje el id mio
        String idFrom = "";
        String message = "";
        String idMessage = "";
        String mIdTo = "1233";
        if (mIDFlujo == Constants.FLUJO_CLIENTE_VENDEDOR) {
            message = contenido.substring(Constants.FLUJO_CLIENTE_VENDEDOR_START_SMS, contenido.length());
            idMessage = contenido.substring(1, 7);
            idFrom = idMessage.substring(0, 4);
            mIdSms = (int) mRepository.insertNewSms(idMessage, message, idFrom, mIdTo);
        }else if (mIDFlujo == Constants.FLUJO_VENDEDOR_CLIENTE){
            message = contenido.substring(Constants.FLUJO_VENDEDOR_CLIENTE_START_SMS, contenido.length());
            idMessage = contenido.substring(1, 5);
            idFrom = contenido.substring(1, 5);
            mIdSms = (int) mRepository.insertNewSms(idMessage, message, mIdTo, idFrom);
        }

        //mIdSms = (int) mRepository.insertNewSms(idMessage, message, idFrom, mIdTo);

        mIntent.putExtra(Constants.EXTRA_IS_NEW,true);
        mIntent.putExtra("sms_id",mIdSms);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // TODO: 16/11/2016 guardar el id de notificacion y luego irlo incrementando

        Utils.makeNotification(UtilsFunctions.randomNumber(), R.mipmap.ic_launcher,R.drawable.notification_alarm,mContext.getString(R.string.title_alert),mContext.getString(R.string.body_alert),mContext.getApplicationContext(),mIntent,mClass);
        
    }


}
