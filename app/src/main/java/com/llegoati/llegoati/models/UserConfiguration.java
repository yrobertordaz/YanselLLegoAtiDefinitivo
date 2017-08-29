package com.llegoati.llegoati.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Richard on 8/11/2017.
 */

public class UserConfiguration {
    @SerializedName("Seller")
    public String Seller;
    @SerializedName("UserPoints")
    public int UserPoints;
    @SerializedName("SendedMessajeToday")
    public int SendedMessajeToday;
    @SerializedName("MaxMessajesAllowed")
    public int MaxMessajesAllowed;
    @SerializedName("ValueToSendSms")
    public int ValueToSendSms;
    @SerializedName("CanSendFree")
    public boolean CanSendFree;
}
