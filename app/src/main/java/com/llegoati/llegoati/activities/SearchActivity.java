package com.llegoati.llegoati.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.llegoati.llegoati.App;
import com.llegoati.llegoati.R;

import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        App.getInstance().getAppComponent().inject(this);
        ButterKnife.bind(this);
    }
}
