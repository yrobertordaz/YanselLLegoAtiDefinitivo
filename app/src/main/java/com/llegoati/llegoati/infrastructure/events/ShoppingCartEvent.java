package com.llegoati.llegoati.infrastructure.events;

/**
 * Created by Yansel on 6/23/2017.
 */

public class ShoppingCartEvent {
    boolean success = false;
    boolean empty = false;

    public ShoppingCartEvent() {
    }

    public ShoppingCartEvent(boolean empty) {
        this.empty = empty;
    }

    public ShoppingCartEvent(boolean empty, boolean success) {
        this.empty = empty;
        this.success = success;
    }


    public boolean isSuccess() {
        return success;
    }

    public boolean isEmpty() {
        return empty;
    }
}
