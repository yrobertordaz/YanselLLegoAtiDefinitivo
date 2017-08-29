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
import com.llegoati.llegoati.exceptions.NotConfirmedException;
import com.llegoati.llegoati.exceptions.UserException;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.infrastructure.abstracts.IUserManager;
import com.llegoati.llegoati.infrastructure.concrete.utils.Jsonable;
import com.llegoati.llegoati.infrastructure.concrete.utils.Validators;
import com.llegoati.llegoati.infrastructure.models.User;
import com.llegoati.llegoati.infrastructure.models.UserConfirm;
import com.llegoati.llegoati.infrastructure.models.UserLogin;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    private ProgressDialog load;

    @Inject
    IRepository repository;

    @Inject
    IUserManager userManager;

    @Bind(R.id.etEmail)
    EditText etEmail;

    @Bind(R.id.etPassword)
    EditText etPassword;

    @OnClick(R.id.btnEnter)
    public void enter(View view) {
        if (isValidData()) {
            UserLogin userLogin = new UserLogin(etEmail.getText().toString(), etPassword.getText().toString());
            LoginTask loginTask = new LoginTask();
            loginTask.execute(userLogin);
        }
    }

    private boolean isValidData() {
        boolean valid = false;
        if (!Validators.emailValid(etEmail.getText().toString())) {
            etEmail.setError("Introduzca un correo válido.");
        } else {
            etEmail.setError(null);
            valid = true;
        }

        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError("La contraseña no puede estar vacía.");
        } else {
            etPassword.setError(null);
            valid = true;
        }
        return valid;
    }

    @OnClick(R.id.register_text)
    public void registerButton(View view) {
        RegisterActivity.start();
    }

    public LoginActivity() {
        super();
        App.getInstance().getAppComponent().inject(this);
    }

    public static void start() {
        App.getInstance().startActivity(new Intent(App.getInstance(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
    }

    public class LoginTask extends AsyncTask<UserLogin, Void, Void> {

        private boolean loginDone;

        @Override
        protected Void doInBackground(UserLogin... userLogins) {
            try {

                User user = repository.loginUser(userLogins[0], LoginActivity.this);
                loginDone = true;
                userManager.saveUser(user);
            } catch (UserException userException) {
                Snackbar.make(etEmail, Html.fromHtml("<span style='color:red;'>El usuario o la contraseña son incorrectos.</span>"), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } catch (NotConfirmedException e) {
                UserConfirm userConfirm = new UserConfirm(userLogins[0].getEmail(), "");
                Bundle b = new Bundle();
                b.putString("userConfirm", Jsonable.toJson(userConfirm));
                ConfirmActivity.start(b);
            } catch (IOException e) {
                Snackbar.make(etEmail, Html.fromHtml("<span style='color:red;'>Se perdió la conexión.</span>"), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            loginDone = false;
            load = ProgressDialog.show(LoginActivity.this, "Autenticando usuario", "Espere por favor...");
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            load.cancel();
            load.dismiss();
            if (loginDone) {
                LoginActivity.this.setResult(MainActivity.LOGIN_CODE);
                LoginActivity.this.onBackPressed();
            }
            super.onPostExecute(aVoid);
        }
    }


}
