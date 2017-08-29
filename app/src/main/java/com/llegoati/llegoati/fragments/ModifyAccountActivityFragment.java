package com.llegoati.llegoati.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import com.llegoati.llegoati.App;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.activities.MainActivity;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.infrastructure.abstracts.IUserManager;
import com.llegoati.llegoati.infrastructure.concrete.utils.Validators;
import com.llegoati.llegoati.infrastructure.models.User;
import com.llegoati.llegoati.models.UserModify;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class ModifyAccountActivityFragment extends Fragment {

    @Inject
    IRepository repository;

    @Inject
    IUserManager userManager;

    @Bind(R.id.etName)
    EditText etName;

    @Bind(R.id.etEmail)
    EditText etEmail;

    @Bind(R.id.etPhone)
    EditText etPhone;

    @Bind(R.id.rbSex)
    RadioButton rbSex;

    @Bind(R.id.rbSexMale)
    RadioButton rbSexMale;

    private ProgressDialog load;

    @OnClick(R.id.btnEnter)
    public void btnEnterOnClick() {
        if (isValidData()) {
            UserModify userModify = new UserModify(userManager.user().getId(), etName.getText().toString(),
                    etEmail.getText().toString(),
                    etPhone.getText().toString(),
                    rbSex.isChecked() ? "Femenino" : "Masculino");
            ModifyAccountTask modifyAccountTask = new ModifyAccountTask(this.getContext());
            modifyAccountTask.execute(userModify);
        }
    }


    public ModifyAccountActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_modify_account, container, false);
        App.getInstance().getAppComponent().inject(this);
        ButterKnife.bind(this, rootView);
        fillDataForm();

        return rootView;
    }

    private void fillDataForm() {

        etName.setText(userManager.user().getName() != null ? userManager.user().getName() : "");
        etEmail.setText(userManager.user().getEmail() != null ? userManager.user().getEmail() : "");
        etPhone.setText(userManager.user().getPhone() != null ? userManager.user().getPhone() : "");
        if (userManager.user().getSex() != null && userManager.user().getSex().toLowerCase().equals("femenino")) {

            rbSex.setChecked(true);
        } else {
            rbSexMale.setChecked(true);
        }
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

        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public class ModifyAccountTask extends AsyncTask<UserModify, Void, Void> {
        Context context;
        private boolean modifyDone;

        public ModifyAccountTask(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(UserModify... params) {
            try {
                repository.updateUser(params[0], context);
                modifyDone = true;
            } catch (IOException e) {
                Snackbar.make(etName, Html.fromHtml("<span style='color:red;'>Se perdió la conexión.</span>"), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            modifyDone = false;
            load = ProgressDialog.show(context, "Modificando cuenta", getString(R.string.text_wait_please));
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            load.cancel();
            load.dismiss();
            if (modifyDone) {
                User userToSave = userManager.user();
                userToSave.setEmail(etEmail.getText().toString());
                userToSave.setName(etName.getText().toString());
                userToSave.setPhone(etPhone.getText().toString());

                userToSave.setSex(rbSex.isChecked() ? getString(R.string.text_female) : getString(R.string.text_male));

                userManager.saveUser(userToSave);
                ModifyAccountActivityFragment.this.getActivity().setResult(MainActivity.MODIFY_CODE);
                ModifyAccountActivityFragment.this.getActivity().finish();
            }
            super.onPostExecute(aVoid);

        }
    }
}
