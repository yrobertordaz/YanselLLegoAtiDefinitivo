package com.llegoati.llegoati.smsmodulo;

import android.content.Context;

import com.llegoati.llegoati.smsmodulo.Infraestructure.concrete.Repository;
import com.llegoati.llegoati.smsmodulo.Utils.Constants;
import com.llegoati.llegoati.smsmodulo.Utils.Utils;

/**
 * Created by Richard on 4/1/2017.
 */

public class SendMessage {


    public static void sendMessageToSeller(Context mContext,String idClient, String idVendedor, String mBody){
        String mMessage = Constants.ID_APP + String.valueOf(Constants.FLUJO_CLIENTE_VENDEDOR) +  idClient + idVendedor +  "M" +  mBody;
        //// TODO: 4/1/2017 el caracter de mas despues ver para que sirve
        Utils.directSms(Constants.SERVER_PHONE_SERVICE,mMessage,mContext);

    }

    public static void sendMessageToClient(Context mContext,String idSms, String mBody){
        String mMessage = Constants.ID_APP + String.valueOf(Constants.FLUJO_VENDEDOR_CLIENTE) + idSms + "MMM" +  mBody;
        //// TODO: 4/1/2017 el caracter de mas despues ver para que sirve
        Utils.directSms(Constants.SERVER_PHONE_SERVICE,mMessage,mContext);
    }

    public static void sendMessage(Context mContext,String idClient, String idVendedor, String mBody){
        String mMessage = Constants.ID_APP + String.valueOf(Constants.FLUJO_CLIENTE_VENDEDOR) +  idClient + idVendedor +  mBody;
        //// TODO: 4/1/2017 el caracter de mas despues ver para que sirve
        final Repository mRepository = new Repository(mContext);
        mRepository.insertResponceSms(mBody,idClient,idVendedor);
        Utils.directSms(Constants.SERVER_PHONE_SERVICE,mMessage,mContext);
    }


}
