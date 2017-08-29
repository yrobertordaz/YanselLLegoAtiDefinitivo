package com.llegoati.llegoati.smsmodulo.Infraestructure.concrete.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbReaderDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Calendar";
    private static final int DATABASE_VERSION = 1;

    public DbReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DocumentDataSource.CREATE_TABLE_SMS);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
