package com.llegoati.llegoati.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.llegoati.llegoati.R;
import com.llegoati.llegoati.infrastructure.events.ContactEvents;

import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class ContactsActivity extends BaseActivity {

    private static final int ACTION_ADD_CONTACT = 0;

    @OnClick(R.id.addContact)
    public void addContactOnClick() {
        Intent addContactIntent = new Intent(ContactsActivity.this, AddContactActivity.class);
        startActivityForResult(addContactIntent, ACTION_ADD_CONTACT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void finish() {
        EventBus.getDefault().unregister(this);
        super.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        EventBus.getDefault().post(new ContactEvents(0));
        super.onActivityResult(requestCode, resultCode, data);
    }


}
