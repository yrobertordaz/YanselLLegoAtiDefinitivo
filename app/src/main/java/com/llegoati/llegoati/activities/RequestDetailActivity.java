package com.llegoati.llegoati.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.llegoati.llegoati.App;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.adapter.RequestProductListAdapter;
import com.llegoati.llegoati.adapter.viewholders.RequestHistoryViewHolder;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.infrastructure.concrete.utils.DateUtils;
import com.llegoati.llegoati.infrastructure.models.RequestProductItem;
import com.llegoati.llegoati.models.Request;

import java.io.IOException;
import java.text.ParseException;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RequestDetailActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Inject
    IRepository repository;

    @Bind(R.id.swipe_request_detail)
    SwipeRefreshLayout swipeRequestDetail;

    @Bind(R.id.tv_id_value)
    TextView tvIdValue;

    @Bind(R.id.tv_total_cost)
    TextView tvCostValue;

    @Bind(R.id.tv_msg_cost)
    TextView tvMsgCost;

    @Bind(R.id.tv_discount_per_vip)
    TextView tvDiscountPerVip;

    @Bind(R.id.rv_request_products_list)
    RecyclerView rvRequestProductsList;

    @Bind(R.id.tv_discount_per_qty)
    TextView tvDiscountPerQty;
    @Bind(R.id.tv_discount_point)
    TextView tvDiscountPoint;
    @Bind(R.id.tv_discount_lotery)
    TextView tvDiscountLotery;
    @Bind(R.id.tv_points)
    TextView tvPoints;
    @Bind(R.id.tv_date_value)
    TextView tvDateValue;
    @Bind(R.id.tv_partial_cost)
    TextView tvPartialCost;
    @Bind(R.id.tv_status_value)
    TextView tvStatusValue;
    @Bind(R.id.tv_client_name_value)
    TextView tvClientNameValue;
    @Bind(R.id.tv_address_value)
    TextView tvAddressValue;
    @Bind(R.id.tv_phone_value)
    TextView tvPhoneValue;
    @Bind(R.id.tv_phone_movil_value)
    TextView tvPhoneMovilValue;

    String requestId;
    RequestProductListAdapter requestProductListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        App.getInstance().getAppComponent().inject(this);

        ButterKnife.bind(this);
        requestId = this.getIntent().getStringExtra(RequestHistoryViewHolder.REQUEST_ID);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupRecyclerView();
        FillRequestDataAsync fillRequestDataAsync = new FillRequestDataAsync();
        fillRequestDataAsync.execute();
        swipeRequestDetail.setOnRefreshListener(this);
    }

    private void setupRecyclerView() {
        requestProductListAdapter = new RequestProductListAdapter(getFragmentManager());
        requestProductListAdapter.setRequestId(requestId);
        rvRequestProductsList.setAdapter(requestProductListAdapter);
    }

    @Override
    public void onRefresh() {
        FillRequestDataAsync fillRequestDataAsync = new FillRequestDataAsync();
        fillRequestDataAsync.execute();
    }

    private class FillRequestDataAsync extends AsyncTask<Void, Void, Void> {
        Request request;

        @Override
        protected void onPreExecute() {
            swipeRequestDetail.setRefreshing(true);
            requestProductListAdapter.clear();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            requestProductListAdapter.setRequest(request);
            for (int i = 0; i < request.getProducts().length; i++) {
                requestProductListAdapter.addRequestProduct(request.getProducts()[i]);
            }

            tvIdValue.setText(request.getGeneratedOrderIdentifier());
            tvCostValue.setText(String.format("%.2f cuc", request.getTotalPrice()));
            tvMsgCost.setText(String.format("%.2f cuc", request.getMessengerPrice()));

            tvStatusValue.setText(tvStatusValue.getContext().getResources().getStringArray(R.array.request_status)[request.getState()]);

            tvDiscountPerVip.setText(String.format((request.getLowerVip() == 0) ? "%.2f cuc" : "-%.2f cuc", request.getLowerVip()));
            tvDiscountPerQty.setText(String.format((request.getLowerSeller() == 0) ? "%.2f cuc" : "-%.2f cuc", request.getLowerSeller()));
            tvDiscountPoint.setText(String.format((request.getLowerPoints() == 0) ? "%.2f cuc" : "-%.2f cuc", request.getLowerPoints()));
            tvDiscountLotery.setText(String.format((request.getLowerLotery() == 0) ? "%.2f cuc" : "-%.2f cuc", request.getLowerLotery()));
            tvPoints.setText(String.format("%.2f", request.getAcumulatedPoints()));
            try {
                tvDateValue.setText(DateUtils.formatDate(request.getOrderDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Double _partialCost = partialCost();
            tvPartialCost.setText(String.format("%.2f cuc", _partialCost));

            tvClientNameValue.setText(request.getClient().getName());
            tvAddressValue.setText(request.getAddresContact());
            tvPhoneMovilValue.setText(request.getMovilContact());
            tvPhoneValue.setText(request.getPhoneContact());
            swipeRequestDetail.setRefreshing(false);
            super.onPostExecute(aVoid);
        }

        private Double partialCost() {
            RequestProductItem[] productItems = request.getProducts();
            Double total = 0.0;
            for (int i = 0; i < productItems.length; i++) {
                total += productItems[i].getProductPrice() * productItems[i].getProductCant();
            }
            return total;
        }


        @Override
        protected Void doInBackground(Void... params) {
            try {
                request = repository.request(requestId);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
