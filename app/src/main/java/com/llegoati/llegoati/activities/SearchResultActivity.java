package com.llegoati.llegoati.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.llegoati.llegoati.App;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.adapter.SearchResultAdapter;
import com.llegoati.llegoati.controls.EndlessRecyclerViewScrollListener;
import com.llegoati.llegoati.dialogs.FilterDialog;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.infrastructure.models.ProductItem;
import com.llegoati.llegoati.smsmodulo.Utils.Constants;

import java.io.IOException;
import java.util.Iterator;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchResultActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, FilterDialog.IOnChangeFiltersListener {

    private static final Integer PAGE_SIZE = 10;
    @Inject
    IRepository repository;

    @Bind(R.id.sw_search_result)
    SwipeRefreshLayout swSearchResult;

    @Bind(R.id.rv_product_results)
    RecyclerView rvProductResults;
    private EndlessRecyclerViewScrollListener scrollListener;

    SearchResultAdapter searchResultAdapter;
    SearchAsyncTask searchAsyncTask;
    //    private Integer count = 0;
    private String query;
    private SearchView searchView;
    private String artisanId;
    private String provinceId;
    private Boolean withoutMessenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        App.getInstance().getAppComponent().inject(this);
        ButterKnife.bind(this);
        searchResultAdapter = new SearchResultAdapter();
        handleIntent(getIntent());
        configureRecyclerView();
        swSearchResult.setOnRefreshListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void configureRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvProductResults.setLayoutManager(gridLayoutManager);
        rvProductResults.setAdapter(searchResultAdapter);
        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                searchAsyncTask = new SearchAsyncTask();
                searchAsyncTask.execute(
                        query,
                        String.valueOf(page),
                        String.valueOf(PAGE_SIZE),
                        Boolean.valueOf(false).toString(),
                        artisanId,
                        provinceId,
                        (withoutMessenger != null) ? withoutMessenger.toString() : null);
            }
        };
        rvProductResults.addOnScrollListener(scrollListener);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
            //this.setTitle(getResources().getString(R.string.search_result_title, query));
            searchAsyncTask = new SearchAsyncTask();
            searchAsyncTask.execute(
                    query,
                    "0",
                    PAGE_SIZE.toString(),
                    Boolean.valueOf(false).toString(),
                    null,
                    null,
                    null
            );
        }else
            if (intent.getStringExtra(Constants.SELLER_ID)!= null){

                this.artisanId = intent.getStringExtra(Constants.SELLER_ID);

                searchAsyncTask = new SearchAsyncTask();
                searchAsyncTask.execute(
                        query,
                        "0",
                        PAGE_SIZE.toString(),
                        Boolean.valueOf(false).toString(),
                        intent.getStringExtra(Constants.SELLER_ID),
                        null,
                        null
                );
            }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    @Override
    protected void onResume() {
        if (MainActivity.goShoppingCart) {
            finish();
        }
        super.onResume();
    }

    @Override
    public void onRefresh() {
        swSearchResult.setRefreshing(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_result_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search_action).getActionView();
        searchView.setQuery(query, false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchResultActivity.this.query = query;
                searchAsyncTask = new SearchAsyncTask();
                searchAsyncTask.execute(
                        query,
                        "0",
                        PAGE_SIZE.toString(),
                        Boolean.valueOf(true).toString(),
                        null,
                        null,
                        null
                );
                searchView.onActionViewCollapsed();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

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
            FilterDialog filterDialog = new FilterDialog();
            filterDialog.setOnChangeFiltersListener(this);
            filterDialog.setArtisanName(artisanId);
            filterDialog.setProvinceName(provinceId);
            filterDialog.setWithMessenger(withoutMessenger);
            filterDialog.show(this.getSupportFragmentManager(), MainActivity.class.getSimpleName());
        }
        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSetFiltersValues(String provinceId, String artisanId, Boolean withoutMessenger) {

        this.provinceId = provinceId;
        this.artisanId = artisanId;
        this.withoutMessenger = withoutMessenger;
        searchAsyncTask = new SearchAsyncTask();
        searchAsyncTask.execute(
                query,
                "0",
                PAGE_SIZE.toString(),
                Boolean.valueOf(true).toString(),
                artisanId,
                provinceId,
                (withoutMessenger != null) ? withoutMessenger.toString() : null);

    }

    private class SearchAsyncTask extends AsyncTask<String, Void, Void> {
        boolean refreshing = false;
        Iterator<ProductItem> iterator;

        @Override
        protected void onPreExecute() {
            swSearchResult.setRefreshing(true);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (refreshing) {
                searchResultAdapter.clear();
                refreshing = false;
            }
            searchResultAdapter.notifyDataSetChanged();
            if (iterator != null)
                while (iterator.hasNext()) {
                    ProductItem item = iterator.next();
                    searchResultAdapter.add(item);
                    searchResultAdapter.notifyItemInserted(searchResultAdapter.getItems().indexOf(item));
                }
            swSearchResult.setRefreshing(false);
            refreshing = false;
        }

        @Override
        protected Void doInBackground(String... params) {
            refreshing = Boolean.parseBoolean(params[3]);

            try {

                //search(String query, int pageIndex, int pageSize, String artisanId, String provinceId, Boolean withoutMessenger)
                iterator = repository.search(
                        params[0], // Query
                        Integer.parseInt(params[1]), // PageIndex
                        Integer.parseInt(params[2]), // PageSize
                        (params[4] != null) ? params[4] : null, // ArtisanId
                        (params[5] != null) ? params[5] : null, // ProvinceId
                        (params[6] != null) ? Boolean.parseBoolean(params[6]) : null // WithMessenger
                ).iterator();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
