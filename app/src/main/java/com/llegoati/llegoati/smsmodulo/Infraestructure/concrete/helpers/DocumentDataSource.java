package com.llegoati.llegoati.smsmodulo.Infraestructure.concrete.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;


public class DocumentDataSource {


    private DbReaderDbHelper openHelper;
    private SQLiteDatabase database;

    public DocumentDataSource(Context context) {
        openHelper = new DbReaderDbHelper(context);
        database = openHelper.getWritableDatabase();
    }

    public DocumentDataSource getInstance(Context mContext){
        return new DocumentDataSource(mContext);
    }



    // Types
    public static final String STRING_TYPE = "TEXT";
    public static final String BOOLEAN_TYPE = "BOOLEAN";
    public static final String INTEGER_TYPE = "INTEGER";
    public static final String FLOAT_TYPE = "FLOAT";
    public static final String DATE_TYPE = "DATE";
    public static final String BOLD_TYPE = "BOLB";


    public static final String TABLE_SMS ="SMS_LLEGO_A_TI";

    public Cursor getSmsByIdSms(int mId) {
        Cursor mC =   database.query(
                TABLE_SMS,
                null,
                String.format("%s LIKE \"%s\" ", ColumnSms.ID_SMS,mId),
                null,
                null,
                null,
                ColumnSms.DATE
        );


        if (mC.moveToFirst()){
            return mC;
        }else {
        return    database.query(
                TABLE_SMS,
                    null,
                    String.format("%s = %s ", ColumnSms.ID,mId),
                    null,
                    null,
                    null,
                    ColumnSms.DATE
            );
        }






    }

    public Cursor getSmsById(int mId) {
        return database.query(
                TABLE_SMS,
                null,
                String.format("%s = %s ", ColumnSms.ID,mId),
                null,
                null,
                null,
                ColumnSms.DATE
        );
    }


    public static class ColumnSms {
        public static final String ID = BaseColumns._ID;
        public static final String DESCRIPTION = "DESCRIPTION";
        public static final String ID_CLIENT = "ID_CLIENT";
        public static final String ID_SMS = "ID_SMS";
        public static final String ID_VENDEDOR = "ID_VENDEDOR";
        public static final String DATE = "SMS_DATE";
        public static final String IS_NEW = "EXTRA_IS_NEW";
        public static final String IS_DELETE = "IS_DELETE";
        public static final String IS_RESPONCE = "IS_RESPONCE";
        public static final String NEXT_SMS = "NEXT_SMS";
    }


    public static final String CREATE_TABLE_SMS =
            "CREATE TABLE " + TABLE_SMS + " (" +
                    ColumnSms.ID + " " + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT, " +
                    ColumnSms.DATE + " " + DATE_TYPE + " NOT NULL, " +
                    ColumnSms.NEXT_SMS + " " + STRING_TYPE + " , " +
                    ColumnSms.ID_SMS + " " + STRING_TYPE + " , " +
                    ColumnSms.ID_CLIENT + " " + STRING_TYPE + " , " +
                    ColumnSms.ID_VENDEDOR + " " + STRING_TYPE + " , " +
                    ColumnSms.IS_NEW + " " + BOOLEAN_TYPE + " NOT NULL, " +
                    ColumnSms.IS_DELETE + " " + BOOLEAN_TYPE + " NOT NULL, " +
                    ColumnSms.IS_RESPONCE + " " + BOOLEAN_TYPE + " NOT NULL, " +
                    ColumnSms.DESCRIPTION + " " + STRING_TYPE + " NOT NULL )";


    public long insertNewSms(String mDate,String mIdMessage, String mDescription, String mIdClient, String mIdVendedor) throws Exception {

        ContentValues contentValues = new ContentValues();

        contentValues.put(ColumnSms.DATE, mDate);
        contentValues.put(ColumnSms.IS_DELETE, false);
        contentValues.put(ColumnSms.ID_CLIENT, mIdClient);
        contentValues.put(ColumnSms.ID_VENDEDOR, mIdVendedor);
        contentValues.put(ColumnSms.ID_SMS, mIdMessage);
        contentValues.put(ColumnSms.IS_NEW, true);
        contentValues.put(ColumnSms.IS_RESPONCE, false);
        contentValues.put(ColumnSms.DESCRIPTION, mDescription);

        return database.insert(TABLE_SMS, null, contentValues);
    }

    public long insertResponce(String mDate,String mDescription,String mIdClient,String mIdVendedor) throws Exception {

        ContentValues contentValues = new ContentValues();

        contentValues.put(ColumnSms.DATE, mDate);
        contentValues.put(ColumnSms.IS_DELETE, false);
        contentValues.put(ColumnSms.ID_CLIENT, mIdClient);
        contentValues.put(ColumnSms.ID_VENDEDOR, mIdVendedor);
        contentValues.put(ColumnSms.IS_NEW, false);
        contentValues.put(ColumnSms.IS_RESPONCE, true);
        contentValues.put(ColumnSms.DESCRIPTION, mDescription);

        return database.insert(TABLE_SMS, null, contentValues);
    }

    public void setNext(String mId,String nextId){
        ContentValues values = new ContentValues();
        values.put(ColumnSms.NEXT_SMS, nextId);

        database.update(
                TABLE_SMS,
                values,
                String.format("%s=?", ColumnSms.ID_SMS),
                new String[]{String.valueOf(mId)}
        );
    }

    public void deleteSms(int mId){

        ContentValues values = new ContentValues();
        values.put(ColumnSms.IS_DELETE, true);

        database.update(
                TABLE_SMS,
                values,
                String.format("%s=?", ColumnSms.ID),
                new String[]{String.valueOf(mId)}
        );
    }


    public void updateSms(int mId){

        ContentValues values = new ContentValues();
        values.put(ColumnSms.IS_NEW, false);

        database.update(
                TABLE_SMS,
                values,
                String.format("%s=?", ColumnSms.ID),
                new String[]{String.valueOf(mId)}
        );

    }


    public void deleteSmsFinaly(int mId) {
        database.delete(
                TABLE_SMS,
                String.format("%s=?", ColumnSms.ID),
                new String[]{String.valueOf(mId)}
        );
    }


    public Cursor getNews(){
        return database.query(
                TABLE_SMS,
                null,
                String.format("%s = \"%s\"", ColumnSms.IS_NEW,1),
                null,
                null,
                null,
                ColumnSms.DATE
        );
    }


    public Cursor getInbox(){
        return database.query(
                TABLE_SMS,
                null,
                String.format("%s = \"%s\" AND %s = \"%s\" AND %s = \"%s\" ", ColumnSms.IS_NEW,0,ColumnSms.IS_RESPONCE,0,ColumnSms.IS_DELETE,0),
                null,
                null,
                null,
                ColumnSms.DATE
        );
    }





    public Cursor getDelete(){
        return database.query(
                TABLE_SMS,
                null,
                String.format("%s = \"%s\"", ColumnSms.IS_DELETE,1),
                null,
                null,
                null,
                ColumnSms.DATE
        );
    }

    public Cursor getResponce(){
        return database.query(
                TABLE_SMS,
                null,
                String.format("%s = \"%s\"", ColumnSms.IS_RESPONCE,1),
                null,
                null,
                null,
                ColumnSms.DATE
        );
    }

    /*
    private static final String CALENDAR_EVENTS_NAMES = "CALENDAR_EVENT" ;
    public static final String CREATE_EVENT_NAME_SCRIPT =
            "CREATE TABLE " + CALENDAR_EVENTS_NAMES + " (" +
                    ColumnEvent.ID + " " + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT, " +
                    ColumnEvent.TITLE + " " + STRING_TYPE + " NOT NULL, " +
                    ColumnEvent.DATE + " " + DATE_TYPE + " NOT NULL, " +
                    ColumnEvent.TIME + " " + STRING_TYPE + " NOT NULL, " +
                    ColumnEvent.HOUR + " " + INTEGER_TYPE + " NOT NULL, " +
                    ColumnEvent.MINUTE + " " + INTEGER_TYPE + " NOT NULL, " +
                    ColumnEvent.ACTIVE_ALARM + " " + INTEGER_TYPE + " NOT NULL, " +
                    ColumnEvent.TYPE + " " + INTEGER_TYPE + " NOT NULL, " +
                    ColumnEvent.CONTACT + " " + STRING_TYPE + " NOT NULL, " +
                    ColumnEvent.LOCATION + " " + STRING_TYPE + " NOT NULL, " +
                    ColumnEvent.DESCRIPTION + " " + STRING_TYPE + " NOT NULL )";

    public Cursor getAllEventsByDate(String mDate) {

        mDate = UtilsFunctions.convertDate(mDate);
        mDate =mDate.replace("/","-");

        Cursor mR =database.query(CALENDAR_EVENTS_NAMES,
                null,
                String.format(" %s = date('%s')", ColumnEvent.DATE,mDate),
                null,
                null,
                null,
                ColumnEvent.HOUR);

        return mR;
    }

    public Cursor searchByDate(String mDate) {



        return null;
    }

    public Cursor searchByBody(String query) {

        String mQuery = "SELECT * FROM "+ CALENDAR_EVENTS_NAMES + " WHERE "+ ColumnEvent.TITLE + " LIKE '%" + query +"%' OR " +
                ColumnEvent.DESCRIPTION + " LIKE '%" + query +"%' OR " +
                ColumnEvent.CONTACT + " LIKE '%" + query +"%' OR " +
                ColumnEvent.LOCATION + " LIKE '%" + query +"%'";

        Cursor mR = database.rawQuery(mQuery,null,null);

        return mR;
    }

    public static class ColumnEvent {
        public static final String ID = BaseColumns._ID;
        public static final String TITLE = "TITLE";
        public static final String DESCRIPTION = "DESCRIPTION";
        public static final String DATE = "DATE";
        public static final String TIME = "ALARM";
        public static final String HOUR = "HOURS";
        public static final String MINUTE = "MINUTES";
        public static final String ACTIVE_ALARM = "ACTIVE_ALARM";
        public static final String TYPE = "TYPE";
        public static final String CONTACT = "CONTACT";
        public static final String LOCATION = "LOCATION";
    }



    public Cursor countByType(int type,String date){
        Cursor mR =

                database.query(CALENDAR_EVENTS_NAMES,
                        null,
                        String.format(" %s = date('%s') AND %s = %s ", ColumnEvent.DATE,date,date,ColumnEvent.TYPE,type),
                        null,
                        null,
                        null,
                        ColumnEvent.DATE);
        return mR;


    }

    public Cursor countByType(int type,String date1,String date2){
        Cursor mR =

                database.query(CALENDAR_EVENTS_NAMES,
                        null,
                        String.format(" %s BETWEEN date('%s') AND date('%s') AND %s = %s ", ColumnEvent.DATE,date1,date2,ColumnEvent.TYPE,type),
                        null,
                        null,
                        null,
                        ColumnEvent.DATE);
        return mR;


    }


    public Cursor getAllEvents(){

        Cursor mR =

        database.query(CALENDAR_EVENTS_NAMES,
                null,
                null,
                null,
                null,
                null,
                ColumnEvent.DATE);

        return mR;
    }

    public Cursor getBetweenDates(String date1,String date2){


        if(!UtilsFunctions.validateDates(date1,date2)){
            String temp = date1;
            date1 = date2;
            date2 = temp;
        }

        //date1 = UtilsFunctions.convertDate(date1);
        //date2 = UtilsFunctions.convertDate(date2);

        date1 = date1.replace("/","-");
        date2 = date2.replace("/","-");

        Cursor mR = database.query(CALENDAR_EVENTS_NAMES,
                null,
                String.format(" %s BETWEEN date('%s') AND date('%s') ", ColumnEvent.DATE,date1,date2),
                null,
                null,
                null,
                ColumnEvent.DATE);

        return mR;
    }

    public Cursor getCountEventsDates(String date1,String date2){


        if(!UtilsFunctions.validateDates(date1,date2)){
            String temp = date1;
            date1 = date2;
            date2 = temp;
        }

        //date1 = UtilsFunctions.convertDate(date1);
        //date2 = UtilsFunctions.convertDate(date2);

        date1 = date1.replace("/","-");
        date2 = date2.replace("/","-");

        Cursor mR = database.query(CALENDAR_EVENTS_NAMES,
                null,
                String.format(" %s BETWEEN date('%s') AND date('%s') ", ColumnEvent.DATE,date1,date2),
                null,
                null,
                null,
                ColumnEvent.DATE);

        return mR;
    }

    public Cursor getByType(int mType){
        return database.query(CALENDAR_EVENTS_NAMES,
                null,
                String.format(" %s = ? ", ColumnEvent.TYPE),
                new String[] {String.valueOf(mType)},
                null,
                null,
                ColumnEvent.DATE);

    }

    public Cursor getById(int mId){
            return database.query(CALENDAR_EVENTS_NAMES,
                    null,
                    String.format(" %s = ? ", ColumnEvent.ID),
                    new String[] {String.valueOf(mId)},
                    null,
                    null,
                    null);

        }

    public void delete(int id){
        database.delete(CALENDAR_EVENTS_NAMES,
                String.format("%s = ? ", ColumnEvent.ID),
                new String[]{String.valueOf(id)});
    }


*/
}
