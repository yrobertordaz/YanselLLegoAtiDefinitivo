package com.llegoati.llegoati.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import com.llegoati.llegoati.App;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.exceptions.CuponDontExistException;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.infrastructure.models.CheckoutLoteryCode;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class CheckoutLoteryCodeFragment extends Fragment {

    @Inject
    IRepository repository;

    @Bind(R.id.etCode)
    EditText etCode;

    @Bind(R.id.tl_coupon_information_box)
    TableLayout tlCoupunInformationBox;

    @Bind(R.id.tv_category)
    TextView tvCategory;

    @Bind(R.id.tv_discount)
    TextView tvDiscount;

    @Bind(R.id.tv_start_date)
    TextView tvStartDate;

    @Bind(R.id.tv_end_date)
    TextView tvEndDate;

    private ProgressDialog load;


    @OnClick(R.id.btnEnter)
    public void OnClick() {
        if (isValidData()) {
            CheckLoteryCodeTask checkLoteryCodeTask = new CheckLoteryCodeTask(this.getContext());
            checkLoteryCodeTask.execute(etCode.getText().toString());
        }
    }

    public CheckoutLoteryCodeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        App.getInstance().getAppComponent().inject(this);
        View rootView = inflater.inflate(R.layout.fragment_checkout_lotery_code, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    private boolean isValidData() {
        boolean valid = false;
        // Contraseña
        if (TextUtils.isEmpty(etCode.getText().toString())) {
            etCode.setError("Debe introducir un código");
        } else {
            etCode.setError(null);
            valid = true;
        }

        return valid;
    }


    public class CheckLoteryCodeTask extends AsyncTask<String, Void, Void> {
        private boolean requestCorrect;
        private Context context;

        CheckoutLoteryCode checkoutLoteryCode;

        public CheckLoteryCodeTask(Context context) {
            this.context = context;
        }


        @Override
        protected Void doInBackground(String... params) {
            try {

                checkoutLoteryCode = repository.checkOutLoteryCode(params[0]);
                requestCorrect = true;
            } catch (CuponDontExistException e) {
                Snackbar.make(etCode, Html.fromHtml("<span style='color:red;'>Código de lotería inválido.</span>"), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } catch (IOException e) {
                Snackbar.make(etCode, Html.fromHtml("<span style='color:red;'>Se perdió la conexión.</span>"), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            requestCorrect = false;
            tlCoupunInformationBox.setVisibility(View.GONE);
            load = ProgressDialog.show(context, getString(R.string.loading_check_lotery_code), getString(R.string.text_wait_please));
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            load.cancel();
            load.dismiss();
            if (requestCorrect) {
                updateLoteryCodeInformation();
            }
            super.onPostExecute(aVoid);

        }

        private void updateLoteryCodeInformation() {
            tvCategory.setText(checkoutLoteryCode.getCupon().getCategory());
            tvDiscount.setText(String.format("%s cuc", checkoutLoteryCode.getRebaja().toString()));
            tvStartDate.setText(checkoutLoteryCode.getActiveDateFrom());
            tvEndDate.setText(checkoutLoteryCode.getExpireDate());
            tlCoupunInformationBox.setVisibility(View.VISIBLE);
        }
    }

}
