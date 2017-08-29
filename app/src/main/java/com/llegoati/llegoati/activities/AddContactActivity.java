package com.llegoati.llegoati.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;

import com.llegoati.llegoati.App;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.infrastructure.abstracts.IUserManager;
import com.llegoati.llegoati.infrastructure.models.Contact;
import com.llegoati.llegoati.infrastructure.models.User;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddContactActivity extends BaseActivity {

    User user;
    ArrayList<Contact> contacts;
    @Inject
    IUserManager userManager;

    @Inject
    IRepository repository;


    @Bind(R.id.rbAddress)
    AppCompatRadioButton rbAddress;

    @Bind(R.id.rbMovil)
    AppCompatRadioButton rbMovil;

    @Bind(R.id.rbPhone)
    AppCompatRadioButton rbPhone;

    @Bind(R.id.etValue)
    AppCompatEditText etValue;
    private ProgressDialog load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setTheme(R.style.AppTheme_Dialog);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        App.getInstance().getAppComponent().inject(this);
        ButterKnife.bind(this);
        contacts = new ArrayList<>();
        user = userManager.user();
        Contact[] contacts1 = user.getContacts();

        for (int i = 0; i < contacts1.length; i++) {
            contacts.add(contacts1[i]);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.btnAddContact)
    public void btnAddContactOnClick() {
        if (isValid()) {
            if (rbAddress.isChecked()) {
                contacts.add(new Contact(0, false, etValue.getText().toString()));
            } else if (rbMovil.isChecked()) {
                contacts.add(new Contact(2, false, etValue.getText().toString()));
            } else {
                contacts.add(new Contact(1, false, etValue.getText().toString()));
            }
            AddContactTask addContactTask = new AddContactTask();
            addContactTask.execute();
        }


    }

    private boolean isValid() {
        if (TextUtils.isEmpty(etValue.getText().toString())) {
            etValue.setError("Campo requerido.");
            return false;
        }
        etValue.setError(null);
        return true;
    }

    public class AddContactTask extends AsyncTask<Void, Void, Void> {
        private boolean contactAdded;


        @Override
        protected Void doInBackground(Void... params) {
            try {
                repository.updateContactUser(user.getId(), contacts.toArray(new Contact[contacts.size()]));
                user.setContacts(contacts.toArray(new Contact[contacts.size()]));
                userManager.saveUser(user);
                contactAdded = true;

            } catch (IOException e) {
                Snackbar.make(etValue, Html.fromHtml("<span style='color:red;'>Se perdió la conexión.</span>"), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            contactAdded = false;
            load = ProgressDialog.show(AddContactActivity.this, "Adicionando contacto", "Espere por favor...");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            load.cancel();
            load.dismiss();
            if (contactAdded) {
                finish();
            }
            super.onPostExecute(aVoid);
        }
    }
}
