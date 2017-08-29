package com.llegoati.llegoati.infrastructure.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Yansel on 2/4/2017.
 */

public class SharedPreferencesManager {
    private static SharedPreferences shared_preferences;

    public static SharedPreferences newInstance(Context context) {
        if (shared_preferences == null)
            shared_preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return shared_preferences;
    }
}
