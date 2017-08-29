package com.llegoati.llegoati.dialogs;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.llegoati.llegoati.App;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.adapter.ShoppingCartRecyclerViewAdapter;
import com.llegoati.llegoati.fragments.ShoppingCartFragment;
import com.llegoati.llegoati.infrastructure.models.ShoppingCartOrder;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yansel on 7/20/2017.
 */

public class CheckoutContactDialog extends DialogFragment implements MaterialDialog.SingleButtonCallback {

    ShoppingCartRecyclerViewAdapter shoppingItemAdapter;

    public ShoppingCartRecyclerViewAdapter getShoppingItemAdapter() {
        return shoppingItemAdapter;
    }

    public void setShoppingItemAdapter(ShoppingCartRecyclerViewAdapter shoppingItemAdapter) {
        this.shoppingItemAdapter = shoppingItemAdapter;
    }

    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_name)
    EditText etName;

    @Bind(R.id.et_address)
    EditText etAddress;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View form = getActivity().getLayoutInflater().inflate(R.layout.checkout_contact_dialog, null);
        ButterKnife.bind(this, form);
        App.getInstance().getAppComponent().inject(this);
        MaterialStyledDialog.Builder dialog = new MaterialStyledDialog.Builder(getActivity())
                .setTitle(R.string.checkout_contact_dialog_title)
                .setStyle(Style.HEADER_WITH_TITLE)
                .setCustomView(form)
                .setNegativeText(R.string.Cancel)
                .setPositiveText(R.string.Ok)
                .onPositive(this);
        return dialog.show();
    }

    @Override
    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

        if (which == DialogAction.POSITIVE) {
            boolean valid = true;
            if (TextUtils.isEmpty(etPhone.getText().toString())) {
                valid = false;
                etPhone.setError(getString(R.string.error_field_required));
            } else {
                etPhone.setError(null);
            }
            if (TextUtils.isEmpty(etAddress.getText().toString())) {
                valid = false;
                etAddress.setError(getString(R.string.error_field_required));
            } else {
                etAddress.setError(null);
            }
            if (TextUtils.isEmpty(etName.getText().toString())) {
                valid = false;
                etName.setError(getString(R.string.error_field_required));
            } else {
                etName.setError(null);
            }


            if (valid) {
                ShoppingCartOrder shoppingCartOrder = new ShoppingCartOrder(
                        new Double(0),
                        null,
                        new Double(0),
                        shoppingItemAdapter.getShoppingCartItems(),
                        new Double(0),
                        new Double(0),
                        etPhone.getText().toString(),
                        etAddress.getText().toString(),
                        etName.getText().toString());

                ShoppingCartFragment.CheckoutShoppingCartAsync checkoutShoppingCartAsync = new ShoppingCartFragment.CheckoutShoppingCartAsync(shoppingCartOrder);
                checkoutShoppingCartAsync.execute();
            }
        }
    }


}
