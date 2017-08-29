package com.llegoati.llegoati.infrastructure.modules;

import android.content.SharedPreferences;

import com.llegoati.llegoati.infrastructure.concrete.ShoppingCartSharedPreference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Yansel on 4/2/2017.
 */
@Module
public class ShoppingCartModule {

    @Provides
    @Singleton
    public ShoppingCartSharedPreference provideShoppingCartSharedPreference(SharedPreferences sharedPreferences) {
        return new ShoppingCartSharedPreference(sharedPreferences);
    }
}
