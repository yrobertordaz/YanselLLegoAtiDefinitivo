package com.llegoati.llegoati.infrastructure.events;

/**
 * Created by Yansel on 6/9/2017.
 */

public class ContactEvents {
    private final int type;

    public ContactEvents(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
