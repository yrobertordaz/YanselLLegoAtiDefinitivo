package com.llegoati.llegoati.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.llegoati.llegoati.App;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.exceptions.IncorrectPasswordException;
import com.llegoati.llegoati.exceptions.InvalidPasswordFormatException;
import com.llegoati.llegoati.exceptions.UserExistException;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.infrastructure.abstracts.IUserManager;
import com.llegoati.llegoati.infrastructure.concrete.utils.Validators;
import com.llegoati.llegoati.infrastructure.models.UserChangePassword;

import java.io.IOException;

import javax.inject.Inject;

public class ChangePasswordActivity extends BaseActivity {

    private EditText etOldPassword;
    private EditText etPassword;
    private EditText etPasswordConfirmation;

    private Button btnEnter;
    private ProgressDialog load;
    UserChangePassword userChangePassword;

    private View relativeView;

    @Inject
    IRepository repository;

    @Inject
    IUserManager userManager;

    public ChangePasswordActivity() {
        super();
        App.getInstance().getAppComponent().inject(this);
    }

    public static void start() {
        App.getInstance().startActivity(new Intent(App.getInstance(), ChangePasswordActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
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
                    String password = etPassword.getText().toString();
                    String oldPassword = etOldPassword.getText().toString();

                    userChangePassword = new UserChangePassword(
                            userManager.user().getEmail(),
                            oldPassword,
                            password);
                    ChangePasswordTask changePasswordTask = new ChangePasswordTask();
                    changePasswordTask.execute(userChangePassword);

                }
            }
        });
    }

    private void initialization() {

        etPassword = (EditText) findViewById(R.id.etPassword);
        etOldPassword = (EditText) findViewById(R.id.etOldPassword);
        etPasswordConfirmation = (EditText) findViewById(R.id.etPasswordConfirmation);
        btnEnter = (Button) findViewById(R.id.btnEnter);
        relativeView = findViewById(R.id.main_view);
    }

    private boolean isValidData() {
        boolean valid = false;
        // Contraseña
        if (!Validators.passwordValid(etPassword.getText().toString())) {
            etPassword.setError("Debe introducir una contraseña");
        } else if (!Validators.passwordConfirmationValid(etPassword.getText().toString(), etPasswordConfirmation.getText().toString())) {
            etPasswordConfirmation.setError("La contraseña y la confirmación deben coincidir.");
        } else {
            etPasswordConfirmation.setError(null);
            etPassword.setError(null);
            valid = true;
        }

        return valid;
    }


    public class ChangePasswordTask extends AsyncTask<UserChangePassword, Void, Void> {
        private boolean passwordChanged;


        @Override
        protected Void doInBackground(UserChangePassword... params) {
            try {

                repository.changePassword(params[0], ChangePasswordActivity.this);
                passwordChanged = true;
            } catch (UserExistException e) {
                Snackbar.make(relativeView, Html.fromHtml("<span style='color:red;'>" + "El usuario no existe" + "</span>"), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } catch (IOException e) {
                Snackbar.make(relativeView, Html.fromHtml("<span style='color:red;'>Se perdió la conexión.</span>"), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } catch (IncorrectPasswordException e) {
                Snackbar.make(relativeView, Html.fromHtml("<span style='color:red;'>Contraseña incorrecta.</span>"), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } catch (InvalidPasswordFormatException e) {
                Snackbar.make(relativeView, Html.fromHtml("<span style='color:red;'>La contraseña debe tener como mínimo 6 caracteres.</span>"), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            passwordChanged = false;
            load = ProgressDialog.show(ChangePasswordActivity.this, "Cambiando contraseña", "Espere por favor...");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            load.cancel();
            load.dismiss();
            if (passwordChanged) {
                finish();
            }
            super.onPostExecute(aVoid);

        }
    }
}
