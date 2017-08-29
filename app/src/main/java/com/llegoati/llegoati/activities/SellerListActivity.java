package com.llegoati.llegoati.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.view.MenuItem;
import android.widget.EditText;

import com.llegoati.llegoati.App;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.adapter.SellersAdapter;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.models.PartialSeller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class SellerListActivity extends BaseActivity {


    @Bind(R.id.seller_listing)
    RecyclerView mSellerListingView;

    public static SellersAdapter mSellersAdapter;

    @Inject
    IRepository mRepository;


    @Bind(R.id.swipe_sellers)
    SwipeRefreshLayout mSqSwipeSeller;

    @Bind(R.id.searh_astesano)
    EditText mSearchField;

    int pageIndex = 0;
    List<PartialSeller> mSellers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        App.getInstance().getAppComponent().inject(this);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Listado de vendedores");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSellerListingView.setLayoutManager(new GridLayoutManager(this,1));
        mSellerListingView.setHasFixedSize(true);

        mChargeData = new LoadSellersInformationAsync(this);
        mChargeData.execute();




    }

    @OnTextChanged(R.id.searh_astesano)
    public void texChange(SpannableStringBuilder mText){
        mSellersAdapter.search(mText.toString());
    }

    private void sellerSearch(){

    }

    LoadSellersInformationAsync mChargeData;

    @Override
    protected void onStop() {
        mChargeData.cancel(true);

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if(!mChargeData.isCancelled()){
            mChargeData.cancel(true);

        }
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            onBackPressed();
        }

        return true;
    }

    private class LoadSellersInformationAsync extends AsyncTask<Void, Void, Void> {


        private ProgressDialog mDialog;

        public LoadSellersInformationAsync(Activity mActivity) {
            this.mDialog = new ProgressDialog(mActivity);
        }

        @Override
        protected void onPreExecute() {


        }


        @Override
        protected void onPostExecute(Void aVoid) {

            if (mSellers == null)
                mSellers = new ArrayList<>();

            mSellersAdapter = new SellersAdapter(mSellers);
            mSellerListingView.setAdapter(mSellersAdapter);



        }

        @Override
        protected Void doInBackground(Void... params) {

            mSellers = mRepository.getPartialSellers(pageIndex);

            return null;
        }
    }




}
