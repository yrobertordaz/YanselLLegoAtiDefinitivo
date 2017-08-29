package com.llegoati.llegoati.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.llegoati.llegoati.App;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.adapter.viewholders.IndexAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yansel on 7/21/2017.
 */

public class IndexDialog extends DialogFragment implements MaterialDialog.SingleButtonCallback {
    @Bind(R.id.rv_index)
    RecyclerView rvIndex;

    IndexAdapter indexAdapter;

    public void setIndexAdapter(IndexAdapter indexAdapter) {
        this.indexAdapter = indexAdapter;
        this.indexAdapter.setDialog(this);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View form = getActivity().getLayoutInflater().inflate(R.layout.index_dialog, null);

        ButterKnife.bind(this, form);
        App.getInstance().getAppComponent().inject(this);
        MaterialStyledDialog.Builder builder1 = new MaterialStyledDialog.Builder(getActivity())
                .setTitle(R.string.index_dialog_title)
                .setStyle(Style.HEADER_WITH_TITLE)
                .setCustomView(form)
                .autoDismiss(false)
                .setNegativeText(getString(R.string.Cancel))
                .onNegative(this);
        setupRecyclerView();
        return builder1.show();
    }

    private void setupRecyclerView() {
        rvIndex.setAdapter(indexAdapter);
    }

    @Override
    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
        this.dismiss();
    }
}
