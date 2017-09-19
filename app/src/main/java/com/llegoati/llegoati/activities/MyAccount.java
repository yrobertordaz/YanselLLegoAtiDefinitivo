package com.llegoati.llegoati.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.llegoati.llegoati.App;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.adapter.CuponAdapter;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.infrastructure.abstracts.IUserManager;
import com.llegoati.llegoati.infrastructure.concrete.utils.DateUtils;
import com.llegoati.llegoati.infrastructure.models.AcumulatedPoint;
import com.llegoati.llegoati.infrastructure.models.Cupon;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyAccount extends BaseActivity {
    @Inject
    IUserManager userManager;
    @Inject
    IRepository repository;

    @Bind(R.id.tv_vip_notification)
    TextView tvVipNotification;

    @Bind(R.id.user_profile_name)
    TextView name;

    @Bind(R.id.user_profile_email)
    TextView email;

    @Bind(R.id.user_profile_phone)
    TextView tvPhone;

    @Bind(R.id.tv_accumulated_end_date)
    TextView tvAccumulatedEndDate;

    @Bind(R.id.user_profile_specification)
    TextView tvSpecification;

    @Bind(R.id.tv_accumulated_cuc)
    TextView tvCuc;

    @Bind(R.id.user_profile_points)
    TextView etPoint;

    @Bind(R.id.rl_point_box)
    RelativeLayout rlPointBox;

    @Bind(R.id.header_cover_image)
    ImageView ivCover;

    @Bind(R.id.user_profile_vip)
    TextView userProfileVip;

    @Bind(R.id.rv_lotery_code)
    RecyclerView rvLoteryCode;

    CuponAdapter cuponAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        App.getInstance().getAppComponent().inject(this);
        ButterKnife.bind(this);
        initialization();
        setupLoteryCodeRecyclerView();
        //userManager.updateLoginInformation();
        LoadLoteryCodesAsync loadLoteryCodesAsync = new LoadLoteryCodesAsync();
        loadLoteryCodesAsync.execute();
    }

    private void initialization() {
        name.setText(userManager.user().getName());
        email.setText(userManager.user().getEmail());
        if (userManager.user().getSpecification() != null)
            tvSpecification.setText(userManager.user().getSpecification());
        else
            tvSpecification.setVisibility(View.GONE);
        ivCover.setVisibility(View.VISIBLE);
        Glide.with(ivCover.getContext())
                .load((userManager.user().getImage() != null) ? Base64.decode(userManager.user().getImage(), Base64.DEFAULT) : null)
                .asBitmap().animate(android.R.anim.fade_in)
                .error((userManager.user().getSex() != null && userManager.user().getSex().toLowerCase().trim().startsWith("f")) ? R.mipmap.ic_female : R.mipmap.ic_male)
                .into(ivCover);


        LoadInformationAccumulatedPointsAsync loadInformationAccumulatedPointsAsync = new LoadInformationAccumulatedPointsAsync(this);
        loadInformationAccumulatedPointsAsync.execute();

        if (userManager.accumulatedPoints() != null && userManager.accumulatedPoints().isVip()) {
            userProfileVip.setVisibility(View.VISIBLE);
            try {
                tvVipNotification.setText(String.format(Locale.US, "usted tiene hasta el %s para disfrutar de ser VIP. Haga una compra antes de esta fecha," +
                        "con rebaja de %.2f cuc por cada compra y así no perderá su estatus!", DateUtils.formatDate(userManager.accumulatedPoints().getDateToVip()), 1f));
            } catch (ParseException e) {
            }
        } else {
            userProfileVip.setVisibility(View.INVISIBLE);
        }
        tvPhone.setText(Html.fromHtml(String.format("<strong>Teléfono: </strong>%s", userManager.user().getPhone())));


    }

    private void setupLoteryCodeRecyclerView() {
        cuponAdapter = new CuponAdapter();
        rvLoteryCode.setAdapter(cuponAdapter);
    }

    private class LoadLoteryCodesAsync extends AsyncTask<Void, Void, Void> {
        List<Cupon> tmp;

        @Override
        protected void onPreExecute() {
            cuponAdapter.clear();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            for (int i = 0; i < tmp.size(); i++) {
                cuponAdapter.addCupon(tmp.get(i));
            }
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                //userManager.updateLoginInformation();
                if (userManager.user().getId()!=null)
                    tmp = repository.loteryCodeHistory(userManager.user().getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    private class LoadInformationAccumulatedPointsAsync extends AsyncTask<Void, Void, Void> {
        Context context;
        private boolean informationLoaded;

        public LoadInformationAccumulatedPointsAsync(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            informationLoaded = false;
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (userManager.accumulatedPoints() != null) {

                etPoint.setText(userManager.accumulatedPoints().getUserPoints().toString());

//                tvCuc.setText(String.format("%.2f cuc",userManager.accumulatedPoints().getCucAcumulatedByPoints()));
                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                tvCuc.setText(String.format("%s cuc", decimalFormat.format(userManager.accumulatedPoints().getCucAcumulatedByPoints()).toString()));

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
//                    Date date = simpleDateFormat.parse(userManager.accumulatedPoints().getDatePointCaduce().split("T")[0]);
                    tvAccumulatedEndDate.setText(formatDate(userManager.accumulatedPoints().getDatePointCaduce()));
                } catch (ParseException e) {
                    tvAccumulatedEndDate.setVisibility(View.GONE);
                }


                rlPointBox.setVisibility(View.VISIBLE);
            }
            super.onPostExecute(aVoid);
        }

        private String formatDate(String date) throws ParseException {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateConverted = simpleDateFormat.parse(date.split("T")[0]);
            SimpleDateFormat shortformat = new SimpleDateFormat("dd/MM/yyyy");
            return shortformat.format(dateConverted);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                AcumulatedPoint acumulatedPoint = repository.acumulatedPoints(userManager.user().getId());
                userManager.saveAccumulatePoints(acumulatedPoint);
                informationLoaded = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
