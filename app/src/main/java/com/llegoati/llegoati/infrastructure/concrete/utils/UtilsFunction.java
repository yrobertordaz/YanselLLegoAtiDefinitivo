package com.llegoati.llegoati.infrastructure.concrete.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;

import com.llegoati.llegoati.smsmodulo.Utils.Constants;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richard on 7/28/2017.
 */

public class UtilsFunction {


    private static boolean CONNECTED = false;
    private static boolean TRY_CONNECTED = false;

    public static boolean isOnline(Context context) {

            try{
                    ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    //NetworkInfo networkInfo = cm.getActiveNetworkInfo();

                    /*            final String dirWeb = "www.google.com";
                                final int puerto = 80;
                                TRY_CONNECTED = false;
                                AsyncTask.execute(new Runnable() {

                                    @Override
                                    public void run() {
                                        try {


                                            Socket s = new Socket(dirWeb, puerto);

                                            if (s.isConnected()) {
                                                CONNECTED = true;
                                                TRY_CONNECTED = true;

                                            }else {
                                                CONNECTED = false;
                                                TRY_CONNECTED = true;
                                            }
                                        }catch (Exception e){
                                            CONNECTED = false;
                                            TRY_CONNECTED = true;
                                        }
                                    }
                                });

                                while (!TRY_CONNECTED)
                                    Thread.sleep(1000);



                                return CONNECTED;
                                //return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
                                //return hasActiveInternetConnection();
        */
                return cm.getActiveNetworkInfo().isConnected();
                    //return false;
                }catch (Exception e)
                {



                    return false;
                }

        }



    public static boolean chekPermission(Context mContext){

        /*<uses-permission android:name="android.permission.GET_ACCOUNTS" />
    <uses-permission android:name="android.permission.READ_PROFILE" />
    <uses-permission android:name="android.permission.READ_CONTACTS" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.CALL_PHONE" />
    <uses-permission android:name="android.permission.RECEIVE_SMS" />
    <uses-permission android:name="android.permission.SEND_SMS" />
    <uses-permission android:name="android.permission.READ_SMS" />*/

        int hasReadPermission = ActivityCompat.checkSelfPermission(mContext,Manifest.permission.READ_EXTERNAL_STORAGE);
        int hasWritePermission = ActivityCompat.checkSelfPermission(mContext,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int hasAccountPermition = ActivityCompat.checkSelfPermission(mContext,Manifest.permission.GET_ACCOUNTS);
        int hasSendSmsPermission = ActivityCompat.checkSelfPermission(mContext,Manifest.permission.SEND_SMS);
        int hasCallPermission = ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE);
        int hasReadContacs = ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_CONTACTS);
        int hasReceiveSms = ActivityCompat.checkSelfPermission(mContext, Manifest.permission.RECEIVE_SMS);
        int hasSendSms = ActivityCompat.checkSelfPermission(mContext, Manifest.permission.SEND_SMS);
        int hasReadSms = ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_SMS);



        if (       hasReadPermission != PackageManager.PERMISSION_GRANTED
                || hasWritePermission != PackageManager.PERMISSION_GRANTED
                || hasSendSmsPermission != PackageManager.PERMISSION_GRANTED
                || hasCallPermission != PackageManager.PERMISSION_GRANTED
                || hasAccountPermition != PackageManager.PERMISSION_GRANTED
                || hasReadContacs != PackageManager.PERMISSION_GRANTED
                || hasReceiveSms != PackageManager.PERMISSION_GRANTED
                || hasSendSms != PackageManager.PERMISSION_GRANTED
                || hasReadSms != PackageManager.PERMISSION_GRANTED
                ) {
            //show your custom dialog regarding why you need this permission
            return false;
        }else {
            return true;
        }
    }

    public static void chekPermissionsToUser(Context mContext){

        /*
        *       <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
                <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
                <uses-permission android:name="android.permission.SEND_SMS" />
                <uses-permission android:name="android.permission.CALL_PHONE" />

        * */
        List<String> mPersmission = new ArrayList<>();

        int hasReadPermission = ActivityCompat.checkSelfPermission(mContext,Manifest.permission.READ_EXTERNAL_STORAGE);
        int hasWritePermission = ActivityCompat.checkSelfPermission(mContext,Manifest.permission.WRITE_EXTERNAL_STORAGE);

        int hasSendSmsPermission = ActivityCompat.checkSelfPermission(mContext,Manifest.permission.SEND_SMS);
        int hasCallPermission = ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE);

        int hasReadContacs = ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_CONTACTS);
        int hasReceiveSms = ActivityCompat.checkSelfPermission(mContext, Manifest.permission.RECEIVE_SMS);
        int hasSendSms = ActivityCompat.checkSelfPermission(mContext, Manifest.permission.SEND_SMS);
        int hasReadSms = ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_SMS);
        int hasAccountPermition = ActivityCompat.checkSelfPermission(mContext,Manifest.permission.GET_ACCOUNTS);


        if (hasReadPermission != PackageManager.PERMISSION_GRANTED)mPersmission.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasWritePermission != PackageManager.PERMISSION_GRANTED)mPersmission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasSendSmsPermission != PackageManager.PERMISSION_GRANTED)mPersmission.add(Manifest.permission.SEND_SMS);
        if (hasCallPermission != PackageManager.PERMISSION_GRANTED)mPersmission.add(Manifest.permission.CALL_PHONE);

        if (hasReadContacs != PackageManager.PERMISSION_GRANTED)mPersmission.add(Manifest.permission.READ_CONTACTS);
        if (hasReceiveSms != PackageManager.PERMISSION_GRANTED)mPersmission.add(Manifest.permission.RECEIVE_SMS);
        if (hasSendSms != PackageManager.PERMISSION_GRANTED)mPersmission.add(Manifest.permission.SEND_SMS);
        if (hasReadSms != PackageManager.PERMISSION_GRANTED)mPersmission.add(Manifest.permission.READ_SMS);
        if (hasAccountPermition != PackageManager.PERMISSION_GRANTED)mPersmission.add(Manifest.permission.GET_ACCOUNTS);




        String[] mPermissionsResponse = new String[mPersmission.size()];



        if(mPersmission.size()>0) {
            int i =0;
            for (String mP : mPersmission
                    ) {
                mPermissionsResponse[i] = mP;
                i++;
            }
            requestPermission(mContext,"Necesitamos estos permisos para continuar",mPermissionsResponse);
        }




    }

    private static void requestPermission(final Context context, String mPermision, final String[] permissions){
        new AlertDialog.Builder(context)
                .setMessage(mPermision)
                .setPositiveButton("DAR PERMISO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                permissions,
                                Constants.CONCEDER_PERMISOS);
                    }
                }).show();

    }

}
