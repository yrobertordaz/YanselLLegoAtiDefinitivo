package com.llegoati.llegoati.smsmodulo.Utils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;


import com.llegoati.llegoati.R;

import java.util.Calendar;

/**
 * Created by Richard on 06/12/2016.
 */
public class UtilsFunctions {

    public static void createAlarma(Context mContext, int YEAR, int MONTH, int DAY, int HOUR, int MINUTE, long idEvent){
        AlarmManager objAlarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Calendar objCalendar = Calendar.getInstance();

        objCalendar.set(Calendar.YEAR, YEAR);
        objCalendar.set(Calendar.MONTH, MONTH - 1);
        objCalendar.set(Calendar.DAY_OF_MONTH, DAY);
        objCalendar.set(Calendar.HOUR_OF_DAY, HOUR);
        objCalendar.set(Calendar.MINUTE, MINUTE);
        objCalendar.set(Calendar.SECOND, 0);
        objCalendar.set(Calendar.MILLISECOND, 0);

        //objCalendar.set(Calendar.AM_PM, Calendar.AM);
        //Intent mIntent = new Intent(mContext,JCalendarAlarmaActivity.class);

        Intent mIntent= new Intent(mContext, BradcastAlarmReceiver.class);
        mIntent.putExtra(Constants.EXTRA_ID_EVENT,idEvent);

        //PendingIntent alarmPendingIntent = PendingIntent.getActivity(mContext,0,mIntent,0);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(mContext,0,mIntent,0);

        objAlarmManager.set(AlarmManager.RTC_WAKEUP,objCalendar.getTimeInMillis(), alarmPendingIntent);
    }

    public static void cancelAlarma(Context mContext, int YEAR, int MONTH, int DAY, int HOUR, int MINUTE, int idEvent){
        AlarmManager objAlarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Calendar objCalendar = Calendar.getInstance();

        objCalendar.set(Calendar.YEAR, YEAR);
        objCalendar.set(Calendar.MONTH, MONTH - 1);
        objCalendar.set(Calendar.DAY_OF_MONTH, DAY);
        objCalendar.set(Calendar.HOUR_OF_DAY, HOUR);
        objCalendar.set(Calendar.MINUTE, MINUTE);
        objCalendar.set(Calendar.SECOND, 0);
        objCalendar.set(Calendar.MILLISECOND, 0);
        objCalendar.set(Calendar.AM_PM, Calendar.AM);

        Intent alamShowIntent = new Intent(mContext,BradcastAlarmReceiver.class);
        alamShowIntent.putExtra(Constants.EXTRA_ID_EVENT,idEvent);

        PendingIntent alarmPendingIntent = PendingIntent.getActivity(mContext,0,alamShowIntent,0);

        objAlarmManager.cancel(alarmPendingIntent);

    }




    public static void makeNotification(long mId,
                                        int mIconId,
                                        int mIconIdSmall,
                                        String mTitle,
                                        String mContent,
                                        Context mContext,
                                        Intent mIntent,
                                        Class mClass) {
        final NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);

        // Añadir actividad padre
        stackBuilder.addParentStack(mClass);

        // Referenciar Intent para la notificación
        stackBuilder.addNextIntent(mIntent);

        // Obtener PendingIntent resultante de la pila
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT);


        // Asignación del pending intent
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
                        .setColor(mContext.getResources().getColor(R.color.colorPrimaryDark))
                        .setContentIntent(resultPendingIntent)
                        .setAutoCancel(true)
                ;

        mNotificationManager.notify((int)mId, mBuilder.build());
    }





    public static boolean validateDates(String date1, String date2) {
        String d1 = date1.split("/")[2];
        String d2 = date2.split("/")[2];
        String m1 = date1.split("/")[1];
        String m2 = date2.split("/")[1];
        String y1 = date1.split("/")[0];
        String y2 = date2.split("/")[0];

        if (Integer.parseInt(y1) > Integer.parseInt(y2))
            return false;
        if (Integer.parseInt(m1) > Integer.parseInt(m2))
            return false;

        return Integer.parseInt(d1) <= Integer.parseInt(d2);

    }


    public static String convertDate(String date) {
        String d1 = date.split("/")[2];
        String m1 = date.split("/")[1];
        String y1 = date.split("/")[0];

        if (Integer.parseInt(d1) < 10)
            d1 = "0" + d1;

        if (Integer.parseInt(m1) < 10)
            m1 = "0" + m1;

        return y1 + "/" + m1 + "/" + d1;
    }
    private static DatePicker datePicker;
    private static TimePicker timePicker;
    private static int hour;
    private static int minute;

    public static void selectTime(final TextView mAdd, final Activity mActivity,View clickView) {
        //showDate(year, month+1, day);
        final int mMinute;
        final TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int min) {
                hour = hourOfDay;
                minute = min;

                mAdd.setText(hour + ":" + minute);

            }
        };

        clickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new TimePickerDialog(mActivity, timeSetListener, hour, minute, false)).show();
            }
        });
    }

    private static DatePickerDialog formDatePickerDialog;
    private static Calendar calendar;
    private static TextView dateView;
    private static int year, month, day;

    public static String todayDay() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "/" + month + "/" + day;
    }

    public static int randomNumber() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR);
        int min = calendar.get(Calendar.MINUTE);
        int mili = calendar.get(Calendar.MILLISECOND);

        return (year + month + day + hour + min + mili);
    }

    public static String todayDayNatural() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "/" + month + "/" + day;
    }

    public static String tomorrowDay() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH) + 1;
        return year + "/" + month + "/" + day;
    }

    public static void selectDate(final TextView mAdd, Activity mActivity,final View mAction) {
        //showDate(year, month+1, day);
        //final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        formDatePickerDialog = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int m = monthOfYear + 1;
                mAdd.setText(dayOfMonth + "/" + m + "/" + year);
            }

        }, year, month, day);

        //formDatePickerDialog.getDatePicker().updateDate(year,month,day);


        mAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formDatePickerDialog.show();
            }
        });


    }

}
