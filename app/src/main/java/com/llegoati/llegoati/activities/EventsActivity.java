package com.llegoati.llegoati.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.llegoati.llegoati.App;
import com.llegoati.llegoati.R;

public class EventsActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public static void start(Bundle bundle) {
        App.getInstance().startActivity(new Intent(App.getInstance(), EventsActivity.class).putExtras(bundle).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
