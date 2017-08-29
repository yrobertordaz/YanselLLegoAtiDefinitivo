package com.llegoati.llegoati.dialogs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.llegoati.llegoati.App;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.models.Province;
import com.llegoati.llegoati.models.SelectionSeller;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yansel on 8/2/2017.
 */

public class FilterDialog extends DialogFragment implements MaterialDialog.SingleButtonCallback {

    public static final String TAG = FilterDialog.class.getSimpleName();

    @Inject
    IRepository repository;

    @Bind(R.id.sw_province)
    Switch swProvince;

    @Bind(R.id.sw_artisan_name)
    Switch swArtisanName;

    @Bind(R.id.sw_active_filter_messenger)
    Switch swActiveFilterMessenger;

    @Bind(R.id.sw_with_messenger)
    Switch swWithMessenger;


    @Bind(R.id.sp_province)
    Spinner spProvince;

    @Bind(R.id.sp_artisan_name)
    Spinner spArtisanName;

    @Bind(R.id.rl_with_messenger)
    RelativeLayout rlWithMessenger;


    //Adapters
    ArrayAdapter<String> spinArtisanAdapter;
    ArrayAdapter<String> spinProvinceAdapter;

    String[] sellerNames;
    String[] provinceNames;

    private IOnChangeFiltersListener onChangeFiltersListener;
    private String selectedArtisanName = null;
    private String selectedProvinceName = null;
    private Boolean withMessenger = null;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View form = getActivity().getLayoutInflater().inflate(R.layout.filter_dialog, null);

        ButterKnife.bind(this, form);
        App.getInstance().getAppComponent().inject(this);
        swProvince.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    spProvince.animate().alpha(1).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            spProvince.setVisibility(View.VISIBLE);
                        }
                    });
                else
                    spProvince.animate().alpha(0).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            spProvince.setVisibility(View.GONE);
                        }
                    });
            }
        });
        swArtisanName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    spArtisanName.animate().alpha(1).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            spArtisanName.setVisibility(View.VISIBLE);
                        }
                    });
                else
                    spArtisanName.animate().alpha(0).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            spArtisanName.setVisibility(View.GONE);
                        }
                    });
            }
        });

        swActiveFilterMessenger.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rlWithMessenger.animate().alpha(1).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            rlWithMessenger.setVisibility(View.VISIBLE);
                        }
                    });
                } else {
                    rlWithMessenger.animate().alpha(0).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            rlWithMessenger.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });

        // TODO: 8/23/2017 cargar los nombres de los artesanos
        // TODO: 8/23/2017 cargar los nombres de las provincias
        spinArtisanAdapter = new ArrayAdapter<String>(FilterDialog.this.getContext(), android.R.layout.simple_spinner_dropdown_item, new String[]{});
        spinProvinceAdapter = new ArrayAdapter<String>(FilterDialog.this.getContext(), android.R.layout.simple_spinner_dropdown_item, new String[]{});

        spArtisanName.setAdapter(spinArtisanAdapter);
        spProvince.setAdapter(spinProvinceAdapter);


        FillAutoCompleteTextViews fillAutoCompleteTextViews = new FillAutoCompleteTextViews();
        fillAutoCompleteTextViews.execute();


        MaterialStyledDialog.Builder builder1 = new MaterialStyledDialog.Builder(getActivity())
                .setTitle(R.string.filter_dialog_title)
                .setStyle(Style.HEADER_WITH_TITLE)
                .setCustomView(form)
                .autoDismiss(true)
                .setNegativeText(R.string.Cancel)
                .setPositiveText(R.string.Ok)
                .onPositive(this);
        return builder1.show();
    }


    @Override
    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
        if (which == DialogAction.POSITIVE) {
            onChangeFiltersListener.onSetFiltersValues(
                    swProvince.isChecked() ? spProvince.getSelectedItem().toString() : null,
                    swArtisanName.isChecked() ? spArtisanName.getSelectedItem().toString() : null,
                    swActiveFilterMessenger.isChecked() ? swWithMessenger.isChecked() : null);
        }
    }

    public void setOnChangeFiltersListener(IOnChangeFiltersListener onChangeFiltersListener) {
        this.onChangeFiltersListener = onChangeFiltersListener;
    }

    public IOnChangeFiltersListener getOnChangeFiltersListener() {
        return onChangeFiltersListener;
    }

    public void setArtisanName(String artisanId) {
        this.selectedArtisanName = artisanId;
    }

    public void setProvinceName(String provinceId) {
        this.selectedProvinceName = provinceId;
    }

    public void setWithMessenger(Boolean withMessenger) {
        this.withMessenger = withMessenger;
    }


    public class FillAutoCompleteTextViews extends AsyncTask<Void, Void, Void> {

        private List<SelectionSeller> sellerList;
        private List<Province> provinceList;


        @Override
        protected void onPreExecute() {
            spinArtisanAdapter.clear();
            spinProvinceAdapter.clear();
            spinProvinceAdapter.notifyDataSetChanged();
            spinArtisanAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            sellerNames = new String[sellerList.size()];
            provinceNames = new String[provinceList.size()];

            int i;

            Integer found = null;
            Integer foundProvince = null;
            for (i = 0; i < sellerList.size(); i++) {
                sellerNames[i] = sellerList.get(i).getName();
                if (selectedArtisanName != null && sellerNames[i].equals(selectedArtisanName)) {
                    found = i;
                    swArtisanName.setChecked(true);
                }
            }

            for (i = 0; i < provinceList.size(); i++) {
                provinceNames[i] = provinceList.get(i).getName();

                if (selectedProvinceName != null && provinceList.get(i).getName().equals(selectedProvinceName)) {
                    foundProvince = i;
                    swProvince.setChecked(true);
                }
            }

            spinArtisanAdapter = new ArrayAdapter<String>(FilterDialog.this.getContext(), android.R.layout.simple_dropdown_item_1line, sellerNames);
            spinProvinceAdapter = new ArrayAdapter<String>(FilterDialog.this.getContext(), android.R.layout.simple_dropdown_item_1line, provinceNames);

            spArtisanName.setAdapter(spinArtisanAdapter);
            spProvince.setAdapter(spinProvinceAdapter);

            if (withMessenger != null) {
                swActiveFilterMessenger.setChecked(true);
                swWithMessenger.setChecked(withMessenger);
            } else
                swActiveFilterMessenger.setChecked(false);

            if (found != null) {
                spArtisanName.setSelection(found, true);
            }
            if (foundProvince != null) {
                spProvince.setSelection(foundProvince, true);
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                sellerList = repository.sellers();
                provinceList = repository.provinces();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public interface IOnChangeFiltersListener {
        public void onSetFiltersValues(String provinceId, String artisanId, Boolean withMessenger);
    }

}
