package com.llegoati.llegoati.infrastructure.models;

/**
 * Created by Yansel on 5/10/2017.
 */

public class Contact {
    String Id;
    int Type;
    Boolean Active;
    String ContactValue;

    public Contact(String id, int type, Boolean active, String contactValue) {
        Id = id;
        Type = type;
        Active = active;
        ContactValue = contactValue;
    }

    public Contact(int type, Boolean active, String contactValue) {
        Type = type;
        Active = active;
        ContactValue = contactValue;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public Boolean getActive() {
        return Active;
    }

    public void setActive(Boolean active) {
        Active = active;
    }

    public String getContactValue() {
        return ContactValue;
    }

    public void setContactValue(String contactValue) {
        ContactValue = contactValue;
    }
}
