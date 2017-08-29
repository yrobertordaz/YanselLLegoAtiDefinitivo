package com.llegoati.llegoati.smsmodulo.Infraestructure.abstracts;


import com.llegoati.llegoati.smsmodulo.Models.SmsMessage;

import java.util.List;

public interface IRepositorySms {


    long insertNewSms(String mIdMEssage, String mDescription, String mIdClient, String mIdVendedor);
    long insertResponceSms(String mDescription, String mIdClient, String mIdVendedor);


    void deleteSms(int mId);
    void setNext(String mId, String nextId);
    void changeStatus(int mId);
    void totalDelete(int mId);

    List<SmsMessage> getInbox();
    List<SmsMessage> getResponces();
    List<SmsMessage> getTrash();

    SmsMessage getMessageByIdSms(int mId);
    SmsMessage getMessageById(int mId);






}