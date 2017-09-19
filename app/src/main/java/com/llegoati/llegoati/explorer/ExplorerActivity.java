package com.llegoati.llegoati.explorer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.llegoati.llegoati.App;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.activities.BaseActivity;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class ExplorerActivity extends BaseActivity {

    @Inject
    IRepository mRepository;

    public IRepository getmRepository() {
        return mRepository;
    }

    public void setmRepository(IRepository mRepository) {
        this.mRepository = mRepository;
    }

    String intentPatch;String patch;
    private ArrayList<FileInformation> mFiles;
    private int position = 20;
    private SwipeRefreshLayout refreshLayout;
    private AdapterFile adapterFile;
    private boolean isAll;
    private RecyclerView rvS;
    public ArrayList<String> mHistoryPrevius;
    public String mHistoryNow;
    public ArrayList<String> globalHistoryPosittion;
    PreferencesFactory mPreferencesFactory;
    String parentPatch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorer);

        App.getInstance().getAppComponent().inject(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mPreferencesFactory = PreferencesFactory.getInstance(this);



        ActionBar mActionBar = getSupportActionBar();
        assert mActionBar != null;
        mActionBar.setTitle("Seleccione base de datos");
        mActionBar.setBackgroundDrawable(getResources().getDrawable(R.color.colorPrimary));

        isAll = false;

        patch = Environment.getExternalStorageDirectory().getPath();

        /*File mExternal = new File(Environment.getExternalStorageDirectory().getPath());

        if (Environment.isExternalStorageEmulated()){
            String traingPath = "/storage/emulated/0";

            File mN = new File(traingPath);

            if (mN.exists()){
                patch = traingPath;
            }
        }
*/
        parentPatch = patch;
        intentPatch = getIntent().getStringExtra("patch");

        if(intentPatch!=null){
            patch = intentPatch;
        }

        Log.e("Patch: ",patch);

        adapterFile = new AdapterFile(createList(patch),ExplorerActivity.this);

        rvS = (RecyclerView) findViewById(R.id.rv_ls_files);
        final LinearLayoutManager llm = new LinearLayoutManager(ExplorerActivity.this);
        rvS.setLayoutManager(llm);
        rvS.setItemAnimator(new DefaultItemAnimator());
        rvS.setAdapter(adapterFile);


// Obtener el refreshLayout
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
            }
        });
// Iniciar la tarea asncrona al revelar el indicador
        refreshLayout.setColorSchemeResources(
                R.color.redType,
                R.color.blueType,
                R.color.greenType,
                R.color.blueType
        );

        rvS.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //cuando el scrool del reciclerview este en lo ultimo cargo la siguiente lista de elementos
                //asi acelero la carga, solo muestro la cantidad que quiero y lo otro lo dejo pendiente
                if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1  && !isAll) {
                    new HackingBackgroundTask().execute();
                }
            }
        });
    }

    private boolean close = false;
    private void tapTwooToOut(){

        if (close) {
            int p = android.os.Process.myPid();
            android.os.Process.killProcess(p);
        } else {
            Toast to = Toast.makeText(ExplorerActivity.this,
                    "Presione de nuevo para salir", Toast.LENGTH_SHORT);
            close = true;
            to.show();
            final Thread t = new Thread() {
                public void run() {

                    try {
                        int time = 0;
                        while (time <= 2000) {
                            sleep(100);
                            time += 100;
                        }

                        close = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {

                    }

                }
            };
            t.start();
        }
    }





    public void  update(String mPatch){

        patch = mPatch;
        adapterFile.clear();
        adapterFile.addAll(createList(patch));


    }

    private  ArrayList<FileInformation> createList(String mPatch){

        position = 20;// desto seria la cantidad de elementos que voy a mostrar para cargar mas rapiddo, los cargo de 20 en 20
        // despues puedo cambiar este valor por el que yo quiera
        mFiles = GlobalMethods.getFileList(mPatch);
        if (mFiles.size() > position) {
            isAll = false;
            List<FileInformation> lsitAdapter = mFiles.subList(0, position);
            ArrayList<FileInformation> este = new ArrayList<FileInformation>();

            for (FileInformation fl : lsitAdapter) {
                este.add(fl);
            }
            Log.e("Values: ","position: "+position + " size: " +mFiles.size());
            return  este;
        }else{
            isAll = true;
            return mFiles;
        }



    }



    private class HackingBackgroundTask extends AsyncTask<Void, Void, ArrayList<FileInformation>> {

        static final int DURACION = 2 * 1000; // 2 segundos de carga
        Activity activity;
        ProgressDialog dialog;
        public HackingBackgroundTask() {

            this.activity = ExplorerActivity.this;
            this.dialog = new ProgressDialog(this.activity);
        }



        @Override
        protected ArrayList<FileInformation> doInBackground(Void... params) {
            // Simulacin de la carga de items
            try {
                Thread.sleep(DURACION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (mFiles.size() > position + 20) {
                List<FileInformation> lsitAdapter1 = mFiles.subList(position, position + 20);
                position = position + 20;

                ArrayList<FileInformation> este = new ArrayList<>();

                for (FileInformation fl : lsitAdapter1) {
                    este.add(fl);
                }
                Log.e("Values: ","position: "+position + " size: " +mFiles.size());
                return este;

            }else if (mFiles.size() > position){

                List<FileInformation> lsitAdapter2 = mFiles.subList(position,mFiles.size());
                position = mFiles.size();

                ArrayList<FileInformation> este = new ArrayList<>();

                for (FileInformation fl : lsitAdapter2) {
                    este.add(fl);
                }
                isAll = true;
                Log.e("Values: ","position: "+position + " size: " +mFiles.size());
                return este;
            }else {
                Log.e("Values: ","position: "+position + " size: " +mFiles.size());
                return null;
            }




            // Retornar en nuevos elementos para el adaptador

        }

        @Override
        protected void onPreExecute() {
            refreshLayout.setRefreshing(true);
            //dialog.show();
        }

        @Override
        protected void onPostExecute( ArrayList<FileInformation> result) {
            super.onPostExecute(result);

            // Limpiar elementos antiguos
            // adapterFile.clear();
            // Aadir elementos nuevos

            if (result!=null)
                adapterFile.addAll(result);

            // Parar la animacin del indicador
            refreshLayout.setRefreshing(false);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (patch.equals(parentPatch)) {
            //super.onBackPressed();
            //if(mPreferencesFactory.existDb())
                super.onBackPressed();

        } else {
            final File temp = new File(patch);
            update(temp.getParent());
        }


    }

}
