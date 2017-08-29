package com.llegoati.llegoati.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.llegoati.llegoati.App;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.exceptions.UserExistException;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.infrastructure.concrete.utils.Jsonable;
import com.llegoati.llegoati.infrastructure.concrete.utils.Validators;
import com.llegoati.llegoati.infrastructure.models.UserConfirm;
import com.llegoati.llegoati.infrastructure.models.UserRegister;

import java.io.IOException;

import javax.inject.Inject;


public class RegisterActivity extends BaseActivity {

    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etPasswordConfirmation;
    private EditText etPhone;
    private Button btnEnter;
    private RadioButton rbSex;
    private ProgressDialog load;
    UserRegister userRegister;
    private View relativeView;

    @Inject
    IRepository repository;

    public RegisterActivity() {
        super();
        App.getInstance().getAppComponent().inject(this);
    }

    public static void start() {
        App.getInstance().startActivity(new Intent(App.getInstance(), RegisterActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initialization();
        events();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void events() {
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidData()) {

                    String sex = rbSex.isChecked() ? "Femenino" : "Masculino";
                    String name = etName.getText().toString();
                    String email = etEmail.getText().toString();
                    String password = etPassword.getText().toString();
                    String passwordConfirmation = etPasswordConfirmation.getText().toString();
                    String phone = etPhone.getText().toString();
                    userRegister = new UserRegister(name, email, phone, sex, password);
                    RegisterTask registerTask = new RegisterTask();
                    registerTask.execute(userRegister);

                }
            }
        });
    }

    private void initialization() {
        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPasswordConfirmation = (EditText) findViewById(R.id.etPasswordConfirmation);
        etPhone = (EditText) findViewById(R.id.etPhone);
        btnEnter = (Button) findViewById(R.id.btnEnter);
        rbSex = (RadioButton) findViewById(R.id.rbSex);
        relativeView = findViewById(R.id.main_view);
    }

    private boolean isValidData() {

        // Contra vacío
        if (TextUtils.isEmpty(etName.getText().toString())) {
            etName.setError("Introduzca el nombre.");
            return false;
        } else
            etName.setError(null);
        // Email
        if (!Validators.emailValid(etEmail.getText().toString())) {
            etEmail.setError("Introduzca un email válido.");
            return false;
        } else
            etEmail.setError(null);

        // Teléfono
        if (!Validators.phoneValid(etPhone.getText().toString())) {
            etPhone.setError("Introduzca un teléfono válido.");
            return false;
        } else
            etPhone.setError(null);

        // Contraseña
        if (!Validators.passwordValid(etPassword.getText().toString())) {
            etPassword.setError("Debe introducir una contraseña");
        } else if (!Validators.passwordConfirmationValid(etPassword.getText().toString(), etPasswordConfirmation.getText().toString())) {
            etPasswordConfirmation.setError("La contraseña y la confirmación deben coincidir.");
        } else {
            etPasswordConfirmation.setError(null);
            etPassword.setError(null);
        }

        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public class RegisterTask extends AsyncTask<UserRegister, Void, Void> {
        private boolean registerDone;


        @Override
        protected Void doInBackground(UserRegister... params) {
            try {
                repository.registerUser(params[0], RegisterActivity.this);
                registerDone = true;
            } catch (UserExistException e) {
                Snackbar.make(relativeView, Html.fromHtml("<span style='color:red;'>" + e.getMessage() + "</span>"), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } catch (IOException e) {
                Snackbar.make(relativeView, Html.fromHtml("<span style='color:red;'>Se perdió la conexión.</span>"), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            registerDone = false;
            load = ProgressDialog.show(RegisterActivity.this, "Registrando cuenta", "Espere por favor...");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            load.cancel();
            load.dismiss();
            if (registerDone) {
                Bundle newBundle = new Bundle();
                UserConfirm userConfirm = new UserConfirm(etEmail.getText().toString(), "");
                newBundle.putString("userConfirm", Jsonable.toJson(userConfirm));
                ConfirmActivity.start(newBundle);
            }
            super.onPostExecute(aVoid);

        }
    }

}
