package com.llegoati.llegoati.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.llegoati.llegoati.App;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.adapter.ShoppingCartRecyclerViewAdapter;
import com.llegoati.llegoati.dialogs.CheckoutContactDialog;
import com.llegoati.llegoati.exceptions.CuponDontExistException;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.infrastructure.abstracts.IUserManager;
import com.llegoati.llegoati.infrastructure.concrete.utils.DateUtils;
import com.llegoati.llegoati.infrastructure.events.LoginEvent;
import com.llegoati.llegoati.infrastructure.events.ShoppingCartEvent;
import com.llegoati.llegoati.infrastructure.models.CheckoutLoteryCode;
import com.llegoati.llegoati.infrastructure.models.MessengerPrice;
import com.llegoati.llegoati.infrastructure.models.Seller;
import com.llegoati.llegoati.infrastructure.models.ShoppingCartItem;
import com.llegoati.llegoati.infrastructure.models.ShoppingCartOrder;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;


public class ShoppingCartFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public static String TAG = ShoppingCartFragment.class.getSimpleName();

    private double priceSent = 0;
    ArrayAdapter<String> spinnerAdapter;

    private View rootView;
    private double partialCost = 0f;
    private double priceTotal;

    public static ShoppingCartRecyclerViewAdapter shoppingItemAdapter;
    private List<MessengerPrice> messengerPrices;

    @Bind(R.id.rl_discount_unavailable)
    RelativeLayout rlDiscountUnavailable;

    @Bind(R.id.rl_shopping_cart_empty)
    RelativeLayout rlShoppingCartEmpty;

    @Bind(R.id.ll_loading_sent_price)
    LinearLayout llLoadingSentPrice;

    @Bind(R.id.tv_gained_points_value)
    TextView tvGainedPointsValue;

    @Bind(R.id.sent_price)
    TextView priceSentTextView;

    @Bind(R.id.sent_spinner)
    Spinner sentSpinner;

    @Bind(R.id.total_value)
    TextView totalTextView;

    @Bind(R.id.tv_discount_points)
    TextView tvDiscountPoints;

    @Bind(R.id.tv_discount_points_date)
    TextView tvDiscountPointsDate;

    @Bind(R.id.tv_discount_vip_value)
    TextView tvDiscountVipValue;

    @Bind(R.id.tv_discount_count)
    TextView tvDiscountCount;


    @Bind(R.id.tv_consume_points_value)
    TextView tvConsumePointsValue;

    @Bind(R.id.tv_discount_lotery_value)
    TextView tvDiscountLoteryValue;

    @Bind(R.id.et_discount_lotery)
    EditText etDiscountLotery;

    @Bind(R.id.tv_discount_partial_value_strike)
    TextView tvDiscountPartialValueStrike;

    @Bind(R.id.tv_discount_partial_value)
    TextView tvDiscountPartialValue;
    private String loteryId;

    @Inject
    IUserManager userManager;

    @Inject
    IRepository repository;

    @OnClick(R.id.fab_send)
    public void fabSend() {

        if (userManager.isUserPermanentAuthenticated() && checkRequestReady()) {
            if (userManager.user().getContacts().length > 1) {

                ShoppingCartOrder shoppingCartOrder = new ShoppingCartOrder(
                        userManager.user().getId(),
                        Double.parseDouble(tvConsumePointsValue.getText().toString().split(" ")[0]),
                        loteryId,
                        (Double.parseDouble(tvConsumePointsValue.getText().toString().split(" ")[0]) != 0) ? userManager.accumulatedPoints().getUserPoints() : 0,
                        shoppingItemAdapter.getShoppingCartItems(),
                        Double.parseDouble(tvDiscountVipValue.getText().toString().split(" ")[0]),
                        Double.parseDouble(priceSentTextView.getText().toString().split(" ")[0].replace(',', '.')));

                CheckoutShoppingCartAsync checkoutShoppingCartAsync = new CheckoutShoppingCartAsync(shoppingCartOrder);
                checkoutShoppingCartAsync.execute();
            }
        } else {

            CheckoutContactDialog checkoutContactDialog = new CheckoutContactDialog();
            checkoutContactDialog.setShoppingItemAdapter(shoppingItemAdapter);
            checkoutContactDialog.show(this.getChildFragmentManager(), TAG);

//            ShoppingCartOrder shoppingCartOrder = new ShoppingCartOrder(
//                    new Double(0),
//                    null,
//                    new Double(0),
//                    shoppingItemAdapter.getShoppingCartItems(),
//                    new Double(0),
//                    new Double(0),
//                    "000000",
//                    "Edif 5");
//            CheckoutShoppingCartAsync checkoutShoppingCartAsync = new CheckoutShoppingCartAsync(shoppingCartOrder);
//            checkoutShoppingCartAsync.execute();
        }
    }

    @OnClick(R.id.btn_apply_discount_lotery)
    public void btnApplyDiscountLotery() {
        if (TextUtils.isEmpty(etDiscountLotery.getText().toString())) {
            Snackbar.make(etDiscountLotery, R.string.et_discount_lotery_empty, Snackbar.LENGTH_LONG).show();
        } else {
            ApplyDiscountLoteryAsync applyDiscountLoteryAsync = new ApplyDiscountLoteryAsync();
            applyDiscountLoteryAsync.execute(etDiscountLotery.getText().toString());
            updatePartialCost();

        }
    }

    @OnClick(R.id.btn_undo_apply_discount_lotery)
    public void btnUndoApplyDiscountLotery() {
        tvDiscountLoteryValue.setText(String.format(Locale.US, "%.2f cuc", 0f));
        updatePartialCost();
        updateValues();

    }

    @OnClick(R.id.btn_consume_points)
    public void btnConsumePointsOnClick() {
        if (userManager.accumulatedPoints().getCucAcumulatedByPoints() != 0)
            tvConsumePointsValue.setText(String.format(Locale.US, "-%.2f cuc", userManager.accumulatedPoints().getCucAcumulatedByPoints()));
        else
            tvConsumePointsValue.setText(String.format(Locale.US, "%.2f cuc", userManager.accumulatedPoints().getCucAcumulatedByPoints()));
        tvDiscountPoints.setText(String.format(Locale.US, "%s %.2f = %.2f cuc", getString(R.string.tv_discount_points_), 0f, 0f));
        tvDiscountPointsDate.setVisibility(View.INVISIBLE);
        updatePartialCost();
    }

    @OnClick(R.id.btn_undo_consume_points)
    public void btnUndoConsumePoints() {
        tvConsumePointsValue.setText(String.format(Locale.US, "%.2f cuc", 0f));
        tvDiscountPoints.setText(String.format(Locale.US, "%s %.2f = %.2f cuc", getString(R.string.tv_discount_points_), userManager.accumulatedPoints().getUserPoints().floatValue(), userManager.accumulatedPoints().getCucAcumulatedByPoints().floatValue()));
        tvDiscountPointsDate.setVisibility(View.VISIBLE);
        updatePartialCost();
    }

    public static class CheckoutShoppingCartAsync extends AsyncTask<Void, Void, Void> {
        ShoppingCartOrder shoppingCartOrder;
        @Inject
        IRepository repository;

        public CheckoutShoppingCartAsync(ShoppingCartOrder shoppingCartOrder) {
            this.shoppingCartOrder = shoppingCartOrder;
            App.getInstance().getAppComponent().inject(this);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                repository.checkoutShoppingCart(this.shoppingCartOrder);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            shoppingItemAdapter.clear();
            EventBus.getDefault().post(new ShoppingCartEvent(true, true));
            super.onPostExecute(aVoid);
        }
    }

    private boolean checkRequestReady() {
        boolean hasPhone = false;
        boolean hasAddress = false;
        for (int i = 0; i < userManager.user().getContacts().length; i++) {
            if (userManager.user().getContacts()[i].getActive() && userManager.user().getContacts()[i].getType() == 0) {
                hasAddress = true;
            } else if (userManager.user().getContacts()[i].getActive()) {
                hasPhone = true;
            }
            if (hasAddress && hasPhone)
                return true;
        }
        return false;
    }


    public double calculatePartialCost() {
        partialCost = ShoppingCartFragment.shoppingItemAdapter.getTotal();
        return partialCost + calculateDiscountCost();
    }

    public double calculateDiscountCost() {
        double discountPoints = Double.parseDouble(tvConsumePointsValue.getText().toString().split(" ")[0]);
        double discountLotery = Double.parseDouble(tvDiscountLoteryValue.getText().toString().split(" ")[0]);
        double discountCount = Double.parseDouble(tvDiscountCount.getText().toString().split(" ")[0]);
        double discountVip = Double.parseDouble(tvDiscountVipValue.getText().toString().split(" ")[0]);
        return discountCount + discountLotery + discountPoints + discountVip;
    }

    public void updatePartialCost() {
        tvDiscountPartialValueStrike.setText(String.format(Locale.US, "%.2f cuc", ShoppingCartFragment.shoppingItemAdapter.getTotal()));
        tvDiscountPartialValue.setText(String.format(Locale.US, "%.2f cuc", calculatePartialCost()));
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ShoppingCartFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ShoppingCartFragment newInstance(int columnCount) {
        ShoppingCartFragment fragment = new ShoppingCartFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_productitem_list, container, false);
        ButterKnife.bind(this, rootView);
        App.getInstance().getAppComponent().inject(this);

        // Set the adapter
        if (rootView.findViewById(R.id.list) instanceof RecyclerView) {
            Context context = rootView.getContext();
            RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.list);

            recyclerView.setLayoutManager(new LinearLayoutManager(context));


            ShoppingCartFragment.shoppingItemAdapter = new ShoppingCartRecyclerViewAdapter(/*totalTextView,*/ getActivity().getSupportFragmentManager());
            recyclerView.setAdapter(ShoppingCartFragment.shoppingItemAdapter);

        }
        if (userManager.isUserPermanentAuthenticated())
            initialization();
        else {
            rlDiscountUnavailable.setVisibility(View.VISIBLE);
        }

        setupSentSpinner(sentSpinner);

        return rootView;
    }

    private void initialization() {

        tvDiscountPoints.setText(String.format(Locale.US, "%s %.2f = %.2f cuc", getString(R.string.tv_discount_points_), userManager.accumulatedPoints().getUserPoints().floatValue(), userManager.accumulatedPoints().getCucAcumulatedByPoints().floatValue()));

        if (userManager.accumulatedPoints().isVip()) {
            tvDiscountVipValue.setText(String.format(Locale.US, (userManager.accumulatedPoints().getCucAcumulatedByPoints() == 0) ? "%.2f cuc" : "-%.2f cuc", Double.valueOf(userManager.accumulatedPoints().getLowerVip().toString())));
        }
        try {
            tvDiscountPointsDate.setText(String.format(Locale.US, "%s %s", getString(R.string.tv_discount_points_date), DateUtils.formatDate(userManager.accumulatedPoints().getDatePointCaduce())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        fillDiscountCount();
    }

    private void fillDiscountCount() {
        float discount = 0f;
        float pointsGained = 0f;
        HashMap<Seller, Integer> prodCount = new HashMap<Seller, Integer>();

        Iterator<ShoppingCartItem> items = shoppingItemAdapter.getShoppingCartItems();
        while (items.hasNext()) {
            ShoppingCartItem itemTemp = items.next();
            pointsGained += itemTemp.getQuantity() * itemTemp.getPointByUnit();
            if (prodCount.containsKey(itemTemp.getSeller())) {
                Integer value = prodCount.get(itemTemp.getSeller());
                value += itemTemp.getQuantity();
                prodCount.put(itemTemp.getSeller(), value);
            } else {
                prodCount.put(itemTemp.getSeller(), itemTemp.getQuantity());
            }
        }
        Iterator<Seller> sellerIterator = prodCount.keySet().iterator();
        while (sellerIterator.hasNext()) {
            Seller sellerTmp = sellerIterator.next();
            Integer cant = prodCount.get(sellerTmp);
            if (sellerTmp.getLowerParameter() <= cant) {
                discount += sellerTmp.getLowerPrice();
            }
        }

        tvDiscountCount.setText(String.format(Locale.US, (discount != 0) ? "-%.2f cuc" : "%.2f cuc", discount));
        tvGainedPointsValue.setText(String.format(Locale.US, "%.0f", pointsGained));
        updatePartialCost();

    }


    private class FillSentSpinner extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            spinnerAdapter.clear();
            llLoadingSentPrice.animate().alpha(1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    llLoadingSentPrice.setVisibility(View.VISIBLE);
                    super.onAnimationEnd(animation);
                }
            });
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (messengerPrices != null) {

                for (MessengerPrice item : messengerPrices) {
                    spinnerAdapter.add(item.getDestine().getMunicipeName());
                }
                spinnerAdapter.notifyDataSetChanged();
                sentSpinner.setSelection(0);
                updateValues();
            }
            llLoadingSentPrice.animate().alpha(0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    llLoadingSentPrice.setVisibility(View.INVISIBLE);
                    super.onAnimationEnd(animation);
                }
            });

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                messengerPrices = repository.messengerPrices();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void updateValues() {
        if (spinnerAdapter != null && !spinnerAdapter.isEmpty()) {
            if (sentSpinner.getSelectedItemPosition() < 0)
                sentSpinner.setSelection(0);
            priceSent = messengerPrices.get(sentSpinner.getSelectedItemPosition()).getValue();
            priceTotal = calculatePartialCost() + priceSent;
            priceSentTextView.setText(String.format("%.2f cuc", priceSent));
            totalTextView.setText(String.format("%.2f cuc", priceTotal));
        }
    }

    private void setupSentSpinner(final Spinner sentSpinner) {
        spinnerAdapter = new ArrayAdapter<>(sentSpinner.getContext(),
                android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sentSpinner.setAdapter(spinnerAdapter);
        sentSpinner.setOnItemSelectedListener(ShoppingCartFragment.this);
        FillSentSpinner fillSentSpinner = new FillSentSpinner();
        fillSentSpinner.execute();
    }

    public void onEvent(LoginEvent event) {
        rlDiscountUnavailable.setVisibility(View.GONE);
        initialization();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void onEvent(ShoppingCartEvent event) {
        if (event.isEmpty()) {
            rlShoppingCartEmpty.animate()
                    .alpha(1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            rlShoppingCartEmpty.setVisibility(View.VISIBLE);
                            super.onAnimationEnd(animation);
                        }
                    });
            if (event.isSuccess()) {
                Snackbar.make(rlShoppingCartEmpty, R.string.success_text, Snackbar.LENGTH_LONG).show();
            }
        } else {
            rlShoppingCartEmpty.animate().alpha(0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            rlShoppingCartEmpty.setVisibility(View.GONE);
                            super.onAnimationEnd(animation);
                        }
                    });
        }
        if (shoppingItemAdapter != null) {
            shoppingItemAdapter.notifyDataSetChanged();
            updateValues();
            fillDiscountCount();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        updateValues();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class ApplyDiscountLoteryAsync extends AsyncTask<String, Void, Void> {
        CheckoutLoteryCode checkoutLoteryCode;

        @Override
        protected void onPostExecute(Void aVoid) {
            if (checkoutLoteryCode.getRebaja() != 0)
                tvDiscountLoteryValue.setText(String.format(Locale.US, "-%.2f cuc", checkoutLoteryCode.getRebaja()));
            else
                tvDiscountLoteryValue.setText(String.format(Locale.US, "%.2f cuc", checkoutLoteryCode.getRebaja()));
            updatePartialCost();
            updateValues();
            // TODO: 7/29/2017 Duda con respecto a que si el loteryId es el Id del cupon asociado a la rebaja
            loteryId = checkoutLoteryCode.getCupon().getIdCoupon();
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                checkoutLoteryCode = repository.checkOutLoteryCode(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CuponDontExistException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
