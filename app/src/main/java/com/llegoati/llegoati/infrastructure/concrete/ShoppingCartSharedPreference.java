package com.llegoati.llegoati.infrastructure.concrete;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.llegoati.llegoati.infrastructure.abstracts.IShoppingCart;
import com.llegoati.llegoati.infrastructure.concrete.utils.Jsonable;
import com.llegoati.llegoati.infrastructure.models.ShoppingCartItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by Yansel on 4/2/2017.
 */

public class ShoppingCartSharedPreference extends ArrayList<ShoppingCartItem> implements IShoppingCart {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String SERIALIZED_SHOPPING_CART = "serialized_shopping_cart";

    public ShoppingCartSharedPreference(int initialCapacity, SharedPreferences sharedPreferences) {
        super(initialCapacity);
        this.sharedPreferences = sharedPreferences;
        load();
    }

    public ShoppingCartSharedPreference(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        load();
    }

    public ShoppingCartSharedPreference(@NonNull Collection<? extends ShoppingCartItem> c, SharedPreferences sharedPreferences) {
        super(c);
        this.sharedPreferences = sharedPreferences;
        load();
    }

//    @Override
//    public int size() {
//        int size = super.size();
//        if(size == 0)
//            EventBus.getDefault().post(new ShoppingCartEvent(true));
//        else
//            EventBus.getDefault().post(new ShoppingCartEvent(false));
//        return size;
//    }

    @Override
    public void save() {
        List<ShoppingCartItem> items = new ArrayList<>();
        for (ShoppingCartItem item :
                this) {
            items.add(item);
        }
        editor = this.sharedPreferences.edit();
        editor.putString(SERIALIZED_SHOPPING_CART, Jsonable.toJson(items));
        editor.commit();
    }

    @Override
    public void load() {
        this.clear();
        if (sharedPreferences.contains(SERIALIZED_SHOPPING_CART)) {
            List<ShoppingCartItem> items = new ArrayList<>();
            items = Arrays.asList((ShoppingCartItem[]) Jsonable.fromJson(this.sharedPreferences.getString(SERIALIZED_SHOPPING_CART, null), ShoppingCartItem[].class));
            for (ShoppingCartItem item :
                    items) {
                this.add(item);
            }
        }

    }
}
