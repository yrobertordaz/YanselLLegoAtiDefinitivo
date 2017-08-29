package com.llegoati.llegoati.infrastructure.models;

/**
 * Created by Yansel on 1/19/2017.
 */

public class DestineInformation {
    String Id;
    String MunicipeName;
    String ProvinceName;

    public DestineInformation(String id, String municipeName, String provinceName) {
        Id = id;
        MunicipeName = municipeName;
        ProvinceName = provinceName;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getMunicipeName() {
        return MunicipeName;
    }

    public void setMunicipeName(String municipeName) {
        MunicipeName = municipeName;
    }

    public String getProvinceName() {
        return ProvinceName;
    }

    public void setProvinceName(String provinceName) {
        ProvinceName = provinceName;
    }
}
