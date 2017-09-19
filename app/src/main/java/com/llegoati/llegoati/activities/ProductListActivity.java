package com.llegoati.llegoati.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.llegoati.llegoati.App;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.adapter.ProductRecyclerViewAdapter;
import com.llegoati.llegoati.controls.EndlessRecyclerViewScrollListener;
import com.llegoati.llegoati.dialogs.FilterDialog;
import com.llegoati.llegoati.fragments.ProductListActivityFragment;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.infrastructure.models.ProductItem;

import java.io.IOException;
import java.util.Iterator;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductListActivity extends BaseActivity implements FilterDialog.IOnChangeFiltersListener {
    public static final Integer PAGE_SIZE = 10;

    @Inject
    IRepository repository;

    @Bind(R.id.swipe_products)
    SwipeRefreshLayout swpProducts;

    private String subcategory;
    private String subcategoryId;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private GridLayoutManager gridLayoutManager;
    View recyclerView;
    private EndlessRecyclerViewScrollListener scrollListener;
    private FillProductAsyncTask fillProductAsyncTask;
    private SearchView searchView;
    private String artisanName;
    private String provinceName;
    private Boolean withoutMessenger;
    private FilterDialog filterDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        App.getInstance().getAppComponent().inject(this);

        subcategory = getIntent().getStringExtra(ProductListActivityFragment.ARG_SUBCATEGORY_NAME);
        subcategoryId = getIntent().getStringExtra(ProductListActivityFragment.ARG_SUBCATEGORY_ID);
        pref = getSharedPreferences(MainActivity.PREFERENCES, Context.MODE_PRIVATE);
        if (subcategoryId != null) {
            editor = pref.edit();
            editor.putString(ProductListActivityFragment.ARG_SUBCATEGORY_NAME, subcategory);
            editor.putString(ProductListActivityFragment.ARG_SUBCATEGORY_ID, subcategoryId);
            editor.apply();
            editor.commit();
        } else {
            subcategory = pref.getString(ProductListActivityFragment.ARG_SUBCATEGORY_NAME, null);
            subcategoryId = pref.getString(ProductListActivityFragment.ARG_SUBCATEGORY_ID, null);
        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        gridLayoutManager = new GridLayoutManager(ProductListActivity.this, 2);

        recyclerView = findViewById(R.id.product_list);
        assert recyclerView != null;


        setupRecyclerView((RecyclerView) recyclerView);
        this.setTitle(subcategory);
        ButterKnife.bind(this);

        swpProducts.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FillProductAsyncTask fillProductAsyncTask = new FillProductAsyncTask();
                fillProductAsyncTask.execute(
                        subcategoryId,
                        String.valueOf(0),
                        PAGE_SIZE.toString(),
                        String.valueOf(true),
                        null,
                        null,
                        null);
            }
        });

        fillProductAsyncTask = new FillProductAsyncTask();
        fillProductAsyncTask.execute(
                subcategoryId,
                String.valueOf(0),
                PAGE_SIZE.toString(),
                String.valueOf(true),
                artisanName,
                provinceName,
                (withoutMessenger != null) ? withoutMessenger.toString() : null
        );

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.product_list_menu, menu);


        //  searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.filter_action) {

            filterDialog = new FilterDialog();
            filterDialog.setOnChangeFiltersListener(this);
            filterDialog.setArtisanName(artisanName);
            filterDialog.setProvinceName(provinceName);
            filterDialog.setWithMessenger(withoutMessenger);
            filterDialog.show(this.getSupportFragmentManager(), MainActivity.class.getSimpleName());
        }
        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(@android.support.annotation.NonNull RecyclerView recyclerView) {


        MainActivity.productAdapter = new ProductRecyclerViewAdapter(subcategory);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(MainActivity.productAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);

        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                fillProductAsyncTask = new FillProductAsyncTask();
                fillProductAsyncTask.execute(
                        subcategoryId,
                        String.valueOf(page),
                        PAGE_SIZE.toString(),
                        String.valueOf(false).toString(),
                        artisanName,
                        provinceName,
                        (withoutMessenger != null) ? withoutMessenger.toString() : null
                );
            }
        };
        recyclerView.addOnScrollListener(scrollListener);

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putString(ProductListActivityFragment.ARG_SUBCATEGORY_NAME, subcategory);
        outState.putString(ProductListActivityFragment.ARG_SUBCATEGORY_ID, subcategoryId);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onResume() {
        if (MainActivity.goShoppingCart) {
            this.finish();
        }
        super.onResume();
    }

    /**
     * This method is called after {@link #onStart} when the activity is
     * being re-initialized from a previously saved state, given here in
     * <var>savedInstanceState</var>.  Most implementations will simply use {@link #onCreate}
     * to restore their state, but it is sometimes convenient to do it here
     * after all of the initialization has been done or to allow subclasses to
     * decide whether to use your default implementation.  The default
     * implementation of this method performs a restore of any view state that
     * had previously been frozen by {@link #onSaveInstanceState}.
     * <p>
     * <p>This method is called between {@link #onStart} and
     * {@link #onPostCreate}.
     *
     * @param savedInstanceState the data most recently supplied in {@link #onSaveInstanceState}.
     * @see #onCreate
     * @see #onPostCreate
     * @see #onResume
     * @see #onSaveInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        this.subcategory = savedInstanceState.getString(ProductListActivityFragment.ARG_SUBCATEGORY_NAME);
        subcategoryId = savedInstanceState.getString(ProductListActivityFragment.ARG_SUBCATEGORY_ID);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSetFiltersValues(String provinceId, String artisanId, Boolean withMessenger) {
        this.provinceName = provinceId;
        this.artisanName = artisanId;
        this.withoutMessenger = withMessenger;

        fillProductAsyncTask = new FillProductAsyncTask();
        fillProductAsyncTask.execute(
                subcategoryId,
                String.valueOf(0),
                PAGE_SIZE.toString(),
                String.valueOf(true),
                artisanName,
                provinceName,
                (withoutMessenger != null) ? withoutMessenger.toString() : null
        );
    }

    private class FillProductAsyncTask extends AsyncTask<String, Void, Void> {
        boolean refreshing = false;
        Iterator<ProductItem> productItemIterator;

        @Override
        protected void onPreExecute() {
            swpProducts.setRefreshing(true);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (refreshing) {
                MainActivity.productAdapter.clear();
                refreshing = false;
            }

            MainActivity.productAdapter.notifyDataSetChanged();
            if (productItemIterator != null)
                while (productItemIterator.hasNext()) {

                    ProductItem item = productItemIterator.next();
                    MainActivity.productAdapter.addProduct(item);
                    MainActivity.productAdapter.notifyItemInserted(MainActivity.productAdapter.getItems().indexOf(item));

                }

            swpProducts.setRefreshing(false);
            refreshing = false;
        }

        @Override
        protected Void doInBackground(String... strings) {
            refreshing = Boolean.parseBoolean(strings[3]);
            try {
                productItemIterator = repository.products(
                        strings[0],
                        Integer.parseInt(strings[1]),
                        Integer.parseInt(strings[2]),
                        strings[4],
                        strings[5],
                        strings[6] != null ? Boolean.parseBoolean(strings[6]) : null)
                        .iterator();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
