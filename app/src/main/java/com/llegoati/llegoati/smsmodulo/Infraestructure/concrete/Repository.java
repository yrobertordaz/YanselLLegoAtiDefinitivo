package com.llegoati.llegoati.smsmodulo.Infraestructure.concrete;

import android.content.Context;
import android.database.Cursor;


import com.llegoati.llegoati.R;
import com.llegoati.llegoati.smsmodulo.Infraestructure.abstracts.IRepositorySms;
import com.llegoati.llegoati.smsmodulo.Infraestructure.concrete.helpers.DocumentDataSource;
import com.llegoati.llegoati.smsmodulo.Models.SmsMessage;

import com.llegoati.llegoati.smsmodulo.Utils.UtilsFunctions;

import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepositorySms {

    Context mContext;
    private DocumentDataSource dataSource;


    public Repository(Context mCotext) {
        this.mContext = mCotext;
        this.dataSource = new DocumentDataSource(mCotext);
    }

    public static IRepositorySms getInstace(Context mContext) {
        return new Repository(mContext);
    }

    @Override
    public long insertNewSms(String mIdMessage,String mDescription, String mIdClient, String mIdVendedor) {

        String date = UtilsFunctions.todayDay();
        date = UtilsFunctions.convertDate(date);
        date = date.replace("/", "-");

        try {
            return dataSource.insertNewSms(date, mIdMessage, mDescription, mIdClient, mIdVendedor);
        } catch (Exception e) {
            new Exception("Error insertando");
        }

        return 0;
    }

    @Override
    public long insertResponceSms(String mDescription, String mIdClient, String mIdVendedor) {


        String date = UtilsFunctions.todayDay();
        date = UtilsFunctions.convertDate(date);
        date = date.replace("/", "-");
        try {
            return dataSource.insertResponce(date, mDescription, mIdClient, mIdVendedor);
        } catch (Exception e) {
            new Exception(mContext.getString(R.string.err));
        }


        return 0;
    }

    @Override
    public void deleteSms(int mId) {
        dataSource.deleteSms(mId);
    }

    @Override
    public void setNext(String mId, String nextId) {
        dataSource.setNext(mId, nextId);
    }

    @Override
    public void changeStatus(int mId) {
        dataSource.updateSms(mId);
    }

    @Override
    public void totalDelete(int mId) {
        dataSource.deleteSmsFinaly(mId);
    }

    @Override
    public List<SmsMessage> getInbox() {

        List<SmsMessage> mNews = new ArrayList<>();
        List<SmsMessage> mInbox = new ArrayList<>();

        Cursor mNewsSms = dataSource.getNews();
        Cursor mInboxSms = dataSource.getInbox();

        int posDescription = mNewsSms.getColumnIndex(DocumentDataSource.ColumnSms.DESCRIPTION);
        int posDate = mNewsSms.getColumnIndex(DocumentDataSource.ColumnSms.DATE);
        int posIdSms = mNewsSms.getColumnIndex(DocumentDataSource.ColumnSms.ID_SMS);
        int posId = mNewsSms.getColumnIndex(DocumentDataSource.ColumnSms.ID);
        int posIdClient = mNewsSms.getColumnIndex(DocumentDataSource.ColumnSms.ID_CLIENT);
        int posIdVendedor = mNewsSms.getColumnIndex(DocumentDataSource.ColumnSms.ID_VENDEDOR);

        if (mNewsSms.moveToLast()) {



            do {
                SmsMessage mMessage = new SmsMessage(
                        mNewsSms.getString(posIdSms),
                        mNewsSms.getString(posIdClient),
                        mNewsSms.getString(posIdVendedor),
                        mNewsSms.getString(posDescription)
                );

                mMessage.setDate(mNewsSms.getString(posDate));
                mMessage.setmId(mNewsSms.getInt(posId));
                mMessage.setNew(true);
                mMessage.setResponce(false);
                mMessage.setDelete(false);


                mNews.add(mMessage);

            } while (mNewsSms.moveToPrevious());
        }



        if (mInboxSms.moveToLast()) {



            do {
                SmsMessage mMessage = new SmsMessage(
                        mInboxSms.getString(posIdSms),
                        mInboxSms.getString(posIdClient),
                        mInboxSms.getString(posIdVendedor),
                        mInboxSms.getString(posDescription)
                );

                mMessage.setDate(mInboxSms.getString(posDate));
                mMessage.setmId(mInboxSms.getInt(posId));
                mMessage.setNew(false);
                mMessage.setResponce(false);
                mMessage.setDelete(false);
                mInbox.add(mMessage);

            } while (mInboxSms.moveToPrevious());
        }

        if (mNews.size() > 0 && mInbox.size() > 0) {
            mNews.addAll(mInbox);
            return mNews;
        } else if (mNews.size() == 0 && mInbox.size() > 0) {
            return mInbox;
        } else
            return mNews;

    }

    @Override
    public List<SmsMessage> getResponces() {

        List<SmsMessage> mNews = new ArrayList<>();
        Cursor mNewsSms = dataSource.getResponce();

        int posDescription = mNewsSms.getColumnIndex(DocumentDataSource.ColumnSms.DESCRIPTION);
        int posDate = mNewsSms.getColumnIndex(DocumentDataSource.ColumnSms.DATE);
        int posIdSms = mNewsSms.getColumnIndex(DocumentDataSource.ColumnSms.ID_SMS);
        int posId = mNewsSms.getColumnIndex(DocumentDataSource.ColumnSms.ID);
        int posIdClient = mNewsSms.getColumnIndex(DocumentDataSource.ColumnSms.ID_CLIENT);
        int posIdVendedor = mNewsSms.getColumnIndex(DocumentDataSource.ColumnSms.ID_VENDEDOR);

        if (mNewsSms.moveToLast()) {


            do {
                SmsMessage mMessage = new SmsMessage(
                        mNewsSms.getString(posIdSms),
                        mNewsSms.getString(posIdClient),
                        mNewsSms.getString(posIdVendedor),
                        mNewsSms.getString(posDescription)
                );

                mMessage.setDate(mNewsSms.getString(posDate));
                mMessage.setmId(mNewsSms.getInt(posId));
                mMessage.setNew(false);
                mMessage.setResponce(true);
                mMessage.setDelete(false);
                mNews.add(mMessage);

            } while (mNewsSms.moveToPrevious());
        }


        if (mNews.size()>0)
            return mNews;

        return mNews;
    }

    @Override
    public List<SmsMessage> getTrash() {

        List<SmsMessage> mNews = new ArrayList<>();
        Cursor mNewsSms = dataSource.getDelete();

        int posDescription = mNewsSms.getColumnIndex(DocumentDataSource.ColumnSms.DESCRIPTION);
        int posDate = mNewsSms.getColumnIndex(DocumentDataSource.ColumnSms.DATE);
        int posIdSms = mNewsSms.getColumnIndex(DocumentDataSource.ColumnSms.ID_SMS);
        int posId = mNewsSms.getColumnIndex(DocumentDataSource.ColumnSms.ID);
        int posIdClient = mNewsSms.getColumnIndex(DocumentDataSource.ColumnSms.ID_CLIENT);
        int posIdVendedor = mNewsSms.getColumnIndex(DocumentDataSource.ColumnSms.ID_VENDEDOR);

        if (mNewsSms.moveToLast()) {



            do {
                SmsMessage mMessage = new SmsMessage(
                        mNewsSms.getString(posIdSms),
                        mNewsSms.getString(posIdClient),
                        mNewsSms.getString(posIdVendedor),
                        mNewsSms.getString(posDescription)
                );

                mMessage.setDate(mNewsSms.getString(posDate));
                mMessage.setmId(mNewsSms.getInt(posId));
                mMessage.setNew(false);
                mMessage.setResponce(false);
                mMessage.setDelete(true);
                mNews.add(mMessage);

            } while (mNewsSms.moveToPrevious());
        }

        return mNews;
    }

    @Override
    public SmsMessage getMessageByIdSms(int mId) {
        SmsMessage mMessage = null;
        Cursor mM = dataSource.getSmsByIdSms(mId);
        int posDescription = mM.getColumnIndex(DocumentDataSource.ColumnSms.DESCRIPTION);
        int posDate = mM.getColumnIndex(DocumentDataSource.ColumnSms.DATE);
        int posIdSms = mM.getColumnIndex(DocumentDataSource.ColumnSms.ID_SMS);
        int posId = mM.getColumnIndex(DocumentDataSource.ColumnSms.ID);
        int posIdClient = mM.getColumnIndex(DocumentDataSource.ColumnSms.ID_CLIENT);
        int posIdVendedor = mM.getColumnIndex(DocumentDataSource.ColumnSms.ID_VENDEDOR);
        int posNew = mM.getColumnIndex(DocumentDataSource.ColumnSms.IS_NEW);
        int posResponce = mM.getColumnIndex(DocumentDataSource.ColumnSms.IS_RESPONCE);
        int posDelete = mM.getColumnIndex(DocumentDataSource.ColumnSms.IS_DELETE);
        if (mM.moveToFirst()) {

            mMessage = new SmsMessage(
                    mM.getString(posIdSms),
                    mM.getString(posIdClient),
                    mM.getString(posIdVendedor),
                    mM.getString(posDescription)
            );
            mMessage.setDate(mM.getString(posDate));
            mMessage.setmId(mM.getInt(posId));

            mMessage.setNew(mM.getInt(posNew) == 1);
            mMessage.setResponce(mM.getInt(posResponce) == 1);
            mMessage.setDelete(mM.getInt(posDelete) == 1);
        }
        return mMessage;

    }


    public SmsMessage getMessageById(int mId) {

        SmsMessage mMessage = null;
        Cursor mM = dataSource.getSmsById(mId);
        int posDescription = mM.getColumnIndex(DocumentDataSource.ColumnSms.DESCRIPTION);
        int posDate = mM.getColumnIndex(DocumentDataSource.ColumnSms.DATE);
        int posIdSms = mM.getColumnIndex(DocumentDataSource.ColumnSms.ID_SMS);
        int posId = mM.getColumnIndex(DocumentDataSource.ColumnSms.ID);
        int posIdClient = mM.getColumnIndex(DocumentDataSource.ColumnSms.ID_CLIENT);
        int posIdVendedor = mM.getColumnIndex(DocumentDataSource.ColumnSms.ID_VENDEDOR);
        int posNew = mM.getColumnIndex(DocumentDataSource.ColumnSms.IS_NEW);
        int posResponce = mM.getColumnIndex(DocumentDataSource.ColumnSms.IS_RESPONCE);
        int posDelete = mM.getColumnIndex(DocumentDataSource.ColumnSms.IS_DELETE);
        if (mM.moveToFirst()) {

            mMessage = new SmsMessage(
                    mM.getString(posIdSms),
                    mM.getString(posIdClient),
                    mM.getString(posIdVendedor),
                    mM.getString(posDescription)
            );
            mMessage.setDate(mM.getString(posDate));
            mMessage.setmId(mM.getInt(posId));

            mMessage.setNew(mM.getInt(posNew) == 1);
            mMessage.setResponce(mM.getInt(posResponce) == 1);
            mMessage.setDelete(mM.getInt(posDelete) == 1);
        }
        return mMessage;
    }
}


