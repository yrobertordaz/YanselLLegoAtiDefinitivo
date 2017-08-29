package com.llegoati.llegoati;

import android.app.Activity;
import android.app.Application;

import com.llegoati.llegoati.infrastructure.components.AppComponent;
import com.llegoati.llegoati.infrastructure.components.DaggerAppComponent;
import com.llegoati.llegoati.infrastructure.modules.AppModule;

/**
 * Created by Yansel on 2/4/2017.
 */

public class App extends Application {

    private static App instance = null;
    private static AppComponent appComponent;

    public static App getInstance() {
        return instance;
    }

    public AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
        }
        return appComponent;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static void forceClose(Activity activity) {

        if (activity != null) activity.finish();
        System.exit(0);
    }

    public static String getVersionName() {
        try {
            return App.getInstance().getPackageManager().getPackageInfo(getPkgName(), 0).versionName;
        } catch (Exception e) {
            return "0";
        }
    }

    public static String getPkgName() {
        try {
            return App.getInstance().getPackageName();
        } catch (Exception e) {
            return "0";
        }
    }

    public static int getVersionCode() {
        try {
            return App.getInstance().getPackageManager().getPackageInfo(getPkgName(), 0).versionCode;
        } catch (Exception e) {
            return 0;
        }
    }

}
