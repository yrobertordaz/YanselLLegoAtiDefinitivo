package com.llegoati.llegoati.infrastructure.modules;

import android.content.Context;
import android.net.ConnectivityManager;

import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.infrastructure.concrete.RemoteRepository;
import com.llegoati.llegoati.infrastructure.concrete.SqliteRepository;
import com.llegoati.llegoati.infrastructure.concrete.utils.UtilsFunction;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by Yansel on 3/19/2017.
 */
@Module
public class ApiModule {


    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }

    @Provides
    @Singleton
    ConnectivityManager provideConectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Provides
    @Singleton
    IRepository provideRemoteRepository(OkHttpClient okHttpClient, ConnectivityManager connectivityManager, Context context) {

        if (UtilsFunction.isOnline(context))
            return new RemoteRepository(okHttpClient, connectivityManager, context);
        else {
            return new SqliteRepository();
        }
        //return new RemoteRepository(okHttpClient, connectivityManager, context);
    }
}
