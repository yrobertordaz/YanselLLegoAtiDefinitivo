package com.llegoati.llegoati.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.llegoati.llegoati.App;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.exceptions.IncorrectConfirmException;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.infrastructure.concrete.utils.Jsonable;
import com.llegoati.llegoati.infrastructure.models.UserConfirm;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmActivity extends BaseActivity {


    private ProgressDialog load;
    private UserConfirm userConfirm;

    @Inject
    IRepository repository;

    @Bind(R.id.etConfirmationCode)
    EditText etConfirmationCode;


    @OnClick(R.id.btnConfirm)
    public void confirmUser(View view) {
        if (isValidData()) {
            ConfirmTask confirmTask = new ConfirmTask();
            userConfirm.setConfirmationCode(etConfirmationCode.getText().toString());
            confirmTask.execute(userConfirm);
        }
    }

    private boolean isValidData() {
        boolean valid = false;
        if (TextUtils.isEmpty(etConfirmationCode.getText().toString())) {
            valid = false;
            etConfirmationCode.setError("Introduzca el un código válido.");
        } else {
            etConfirmationCode.setError(null);
            valid = true;
        }
        return valid;
    }


    public static void start(Bundle bundle) {
        App.getInstance().startActivity(new Intent(App.getInstance(), ConfirmActivity.class).putExtras(bundle).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    public ConfirmActivity() {
        super();
        App.getInstance().getAppComponent().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getExtras();
        ButterKnife.bind(this);

    }

    private void getExtras() {
        try {
            userConfirm = (UserConfirm) Jsonable.fromJson(getIntent().getStringExtra("userConfirm"), UserConfirm.class);

        } catch (Exception e) {

        }
    }


    public class ConfirmTask extends AsyncTask<UserConfirm, Void, Void> {
        private boolean confirmDone;


        @Override
        protected Void doInBackground(UserConfirm... params) {
            try {
                repository.confirmUser(params[0], ConfirmActivity.this);
                confirmDone = true;
            } catch (IOException e) {

                Snackbar.make(etConfirmationCode, Html.fromHtml("<span style='color:red;'>Se perdió la conexión. " + e.getMessage() + "</span>"), Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            } catch (IncorrectConfirmException e) {
                Snackbar.make(etConfirmationCode, Html.fromHtml("<span style='color:red;'>Error. " + e.getMessage() + "</span>"), Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            confirmDone = false;
            load = ProgressDialog.show(ConfirmActivity.this, "Confirmando usuario", "Espere por favor...");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            load.cancel();
            load.dismiss();
            if (confirmDone) {
                ConfirmActivity.this.onBackPressed();
            }
            super.onPostExecute(aVoid);

        }
    }
}
