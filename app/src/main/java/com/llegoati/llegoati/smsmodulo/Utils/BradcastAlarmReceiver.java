package com.llegoati.llegoati.smsmodulo.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;


import com.llegoati.llegoati.R;
import com.llegoati.llegoati.smsmodulo.View.SmsMainActivity;

public class BradcastAlarmReceiver extends BroadcastReceiver {
    public BradcastAlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        long idEvent = intent.getLongExtra(Constants.EXTRA_ID_EVENT,-2);
        Intent mIntent = new Intent(context,SmsMainActivity.class);
        mIntent.putExtra(Constants.EXTRA_ID_EVENT,idEvent);
        if(idEvent!=-2){
            try {
                UtilsFunctions.makeNotification(idEvent, R.mipmap.ic_launcher, R.drawable.notification_alarm, "Nuevo Mensaje", "Usted ha recivido un mensaje", context, mIntent, SmsMainActivity.class);
                Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(2000);
            }catch (Exception e){

            }
        }

    }
}
