package com.llegoati.llegoati.smsmodulo.Utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.llegoati.llegoati.R;
import com.llegoati.llegoati.smsmodulo.View.SmsMainActivity;

import java.util.Calendar;


/**
 * Created by Richard on 08/11/2016.
 */
public class Utils {


    public static String smsCipher(String text) {
        Log.e("TExt", text);

        text = Base64.encodeToString(text.getBytes(), Constants.PUBLIC_CIPHER_KEY);

        return text;
    }

    public static String smsDecipher(String text) {
        Log.e("TExt", text);
        text = new String(Base64.decode(text, Constants.PUBLIC_CIPHER_KEY));

        return text;
    }

    public static void makeNotification(int mId, int mIconId, int mIconIdSmall, String mTitle, String mContent, Context mContext, Intent mIntent, Class mClass) {
        final NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);

        // A�adir actividad padre
        stackBuilder.addParentStack(mClass);

        // Referenciar Intent para la notificaci�n
        stackBuilder.addNextIntent(mIntent);

        // Obtener PendingIntent resultante de la pila
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT);

        // Asignaci�n del pending intent
        //mBuilder.setContentIntent(resultPendingIntent);

        // Remover notificacion al interactuar con ella


        final NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(mContext)
                        .setSmallIcon(mIconIdSmall,1)
                        .setSmallIcon(mIconIdSmall,2)
                        .setSmallIcon(mIconIdSmall,0)
                        .setLargeIcon(BitmapFactory.decodeResource(
                                mContext.getResources(),
                                mIconId
                                )
                        )
                        .setContentTitle(mTitle)
                        .setContentText(mContent)
                        .setContentIntent(resultPendingIntent)
                        .setAutoCancel(true);

        mNotificationManager.notify(mId, mBuilder.build());
    }



    private static int hour;
    private static int minute;

    public static void selectTime(final TextView mAdd, final Context mActivity, View clickView) {
        //showDate(year, month+1, day);
        final int mMinute;
        final TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int min) {
                hour = hourOfDay;
                minute = min;
                String mRHour = String.valueOf(hour);
                String mRMinute = String.valueOf(hour);

                if (hour<10)
                    mRHour = "0"+hour;

                if (minute<10)
                    mRMinute = "0"+minute;



                mAdd.setText(mRHour + ":" + mRMinute);

            }
        };

        clickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new TimePickerDialog(mActivity, timeSetListener, hour, minute, false)).show();
            }
        });
    }


    public Bitmap redimensionarImagenMaximo(Bitmap mBitmap, float newWidth, float newHeigth) {
        //Redimensionamos
        final int width = mBitmap.getWidth();
        final int height = mBitmap.getHeight();

        final float scaleWidth = ((float) newWidth) / width;
        final float scaleHeight = ((float) newHeigth) / height;

        final Matrix matrix = new Matrix();

        matrix.postScale(scaleWidth, scaleHeight);

        return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
    }

    public static void sendSms(Context mContext, String mPhone, String mSms) {
        try {

			/*
			 * SmsManager smsManager = SmsManager.getDefault();
			 * smsManager.sendTextMessage(phoneNo, null, sms, null, null);
			 */
            String n = mSms;
            if (n.isEmpty() || n.toCharArray()[0] == ' ') {
                Toast.makeText(
                        mContext,
                        R.string.error_data,
                        Toast.LENGTH_LONG).show();

            } else {

                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", mPhone);
                smsIntent.putExtra("sms_body", mSms);
                mContext.startActivity(smsIntent);

            }

        } catch (Exception e) {
            Toast.makeText(mContext, R.string.error_network,
                    Toast.LENGTH_LONG).show();
            // TODO: 14/11/2016 levantar una exception de error de red 
            e.printStackTrace();
        }
    }

    private void sendEmail(Context mContext, String[] to, String[] cc, String asunto,
                           String mensaje) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        // String[] to = direccionesEmail;
        // String[] cc = copias;
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_CC, cc);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, asunto);
        emailIntent.putExtra(Intent.EXTRA_TEXT, mensaje);
        emailIntent.setType("message/rfc822");
        mContext.startActivity(Intent.createChooser(emailIntent, "Email "));
    }


    public void NotificationTask(Context mContext, Activity mActivityBroadCast) {
        Intent intent = new Intent(mContext, SmsMainActivity.class);
        PendingIntent sender = PendingIntent.getBroadcast(mActivityBroadCast,
                0, intent, 0);

// We want the alarm to go off 30 seconds from now.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 30);

// Schedule the alarm!
        AlarmManager am = (AlarmManager) mContext.getSystemService(Activity.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

    }

    public static void directSms(String finalContactPhone, String description, Context mContext) {
        PendingIntent sentIntent = PendingIntent.getBroadcast(mContext, 0, new Intent("SMS_SENT"), 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(finalContactPhone, null, description, sentIntent, null);

    }

    public static void callSomeOne(Context mContext, String mPhone) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + mPhone));
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mContext.startActivity(callIntent);
    }

}
