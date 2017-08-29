package com.llegoati.llegoati.infrastructure.modules;

import android.content.Context;

import com.llegoati.llegoati.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Yansel on 3/19/2017.
 */

@Module
public class AppModule {
    private final App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return this.app;
    }


}
