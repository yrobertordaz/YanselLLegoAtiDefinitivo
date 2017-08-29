package com.llegoati.llegoati.smsmodulo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.llegoati.llegoati.smsmodulo.Controllers.ControlSms;


public class SmsProcess extends BroadcastReceiver {
    public SmsProcess() {

    }
    private static final String TAG = SmsProcess.class.getSimpleName();



    public void onReceive(Context context, Intent intent) {

        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                final Object[] pdus = (Object[]) bundle.get("pdus");
                SmsMessage[] messages = new SmsMessage[pdus.length];

                for (int i = 0; i < messages.length; i++) {

                    // Handle deprecated API
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        String format = bundle.getString("format");
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                    } else {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    }

                    SmsMessage currentMessage = messages[i];
                    Bundle args = new Bundle();
                    args.putString("phoneNumber", currentMessage.getDisplayOriginatingAddress());
                    args.putLong("time", currentMessage.getTimestampMillis());
                    args.putString("message", currentMessage.getDisplayMessageBody());
                    args.putString("type", "sms");

                    ControlSms.getInstance(args,context).init();



                }
            }

        } catch (Exception e) {
            Log.e("SmsReceiver Error", "Exception smsReceiver" + e.getMessage());

        }

    }


}
