package com.llegoati.llegoati.infrastructure.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.infrastructure.abstracts.IUserManager;
import com.llegoati.llegoati.infrastructure.concrete.UserManagerSharedPreference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Yansel on 3/27/2017.
 */

@Module
public class UserModule {

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }


    @Provides
    @Singleton
    public IUserManager provideUserManagerSharedPreference(SharedPreferences sharedPreferences, IRepository repository, Context context) {
        return new UserManagerSharedPreference(sharedPreferences, repository, context);
    }
}
