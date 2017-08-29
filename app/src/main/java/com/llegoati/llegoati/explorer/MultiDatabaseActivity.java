package com.llegoati.llegoati.explorer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.llegoati.llegoati.App;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.activities.BaseActivity;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.smsmodulo.Utils.Constants;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MultiDatabaseActivity extends BaseActivity {


    @Bind(R.id.add_database)
    View mBtnAddDatabase;

    @Bind(R.id.list_database)
    RecyclerView mDataBses;

    @Inject
    IRepository mRepository;

    PreferencesFactory mPreferencesFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_database);
        ButterKnife.bind(this);

        App.getInstance().getAppComponent().inject(this);
        mPreferencesFactory = new PreferencesFactory(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LLegoDataBaseFactory[] mDb = mPreferencesFactory.getDataBaseFactories();
        mDataBses.setLayoutManager(new LinearLayoutManager(this));
        mDataBses.setHasFixedSize(true);
        mDataBses.setAdapter(new AdapterDatabaseList(mDb));
    }


    @OnClick(R.id.add_database)
    void clickBtnAdd(){
        Constants.ADD_DATABASE = true;
        startActivity(new Intent(MultiDatabaseActivity.this,ExplorerActivity.class));

    }

    @Override
    protected void onResume() {
        super.onResume();
        LLegoDataBaseFactory[] mDb = mPreferencesFactory.getDataBaseFactories();
        mDataBses.setAdapter(new AdapterDatabaseList(mDb));
    }


}
