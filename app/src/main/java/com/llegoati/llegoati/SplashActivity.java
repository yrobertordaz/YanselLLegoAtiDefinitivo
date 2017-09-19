package com.llegoati.llegoati;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.llegoati.llegoati.activities.BaseActivity;
import com.llegoati.llegoati.activities.MainActivity;
import com.llegoati.llegoati.explorer.ExplorerActivity;
import com.llegoati.llegoati.explorer.PreferencesFactory;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.infrastructure.abstracts.IUserManager;
import com.llegoati.llegoati.infrastructure.concrete.SqliteRepository;
import com.llegoati.llegoati.infrastructure.concrete.dbconection;
import com.llegoati.llegoati.infrastructure.concrete.utils.UtilsFunction;
import com.llegoati.llegoati.smsmodulo.Utils.Constants;

import java.io.File;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity {

    public static boolean permissionsGranted = false;
    @Inject
    IUserManager userManager;

    @Inject
    IRepository repository;

    private static final int SELECTED_DB = 8695;

    PreferencesFactory mPreferencesFactory;

    AsyncTask mSplash = new AsyncTask() {
        @Override
        protected Object doInBackground(Object[] params) {
            return null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        App.getInstance().getAppComponent().inject(this);

        if (!UtilsFunction.chekPermission(this)){
            UtilsFunction.chekPermissionsToUser(this);
        }else {
            init();
        }
    }


    private void  init(){
        if (!UtilsFunction.isOnline(this)) {
            mPreferencesFactory = new PreferencesFactory(this);
            if (!mPreferencesFactory.existDb())
                startActivityForResult(new Intent(this, ExplorerActivity.class), SELECTED_DB);
            else {
                File mF = new File(mPreferencesFactory.getDbPatch());
                String[] mPatch = mF.getAbsolutePath().split(File.separator);
                String mFinalPath = "";
                for (int i=0;i<mPatch.length-1;i++){
                    mFinalPath += mPatch[i] + File.separator;
                }
                if (repository instanceof SqliteRepository)
                    ((SqliteRepository) repository).setmDbconection(
                            new dbconection(this, mFinalPath,mF.getName())
                    );

                new MyAsyncTask(this).execute();
            }
        }else {
            new MyAsyncTask(this).execute();
            String a = "";
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECTED_DB){
            init();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.CONCEDER_PERMISOS: {
                int count = 0 ;
                if(grantResults.length>0) {
                    if (!UtilsFunction.chekPermission(this)){
                        UtilsFunction.chekPermissionsToUser(this);
                    }else {
                        init();
                    }

                }



            }
        }
    }

    public class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        private ProgressDialog dialog;
        private SplashActivity activity;


        public MyAsyncTask(SplashActivity activity) {

            this.activity = activity;
            this.dialog = new ProgressDialog(this.activity);

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(Void result) {




                    final Thread t = new Thread() {
                        public void run() {

                            try {
                                /*MediaPlayer mp = MediaPlayer.create(activity,
                                        R.raw.sad);
                                mp.start();*/

                                int time = 0;
                                while (time <= 4000) {
                                    sleep(100);
                                    time += 100;
                                }

                               // mp.stop();

                                Intent intent = new Intent(
                                        getApplicationContext(),
                                        MainActivity.class);
                                startActivity(intent);


                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } finally {
                                finish();
                            }

                        }

                    };

                    t.start();



        }

        @Override
        protected Void doInBackground(Void... params) {


            return null;
        }

    }

}
