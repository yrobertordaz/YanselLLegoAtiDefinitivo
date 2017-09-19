package com.llegoati.llegoati.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.llegoati.llegoati.App;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.adapter.EventsAdapter;
import com.llegoati.llegoati.adapter.NewsAdapter;
import com.llegoati.llegoati.adapter.ProductHomeRecyclerViewAdapter;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.infrastructure.abstracts.IUserManager;
import com.llegoati.llegoati.infrastructure.concrete.RemoteRepository;
import com.llegoati.llegoati.infrastructure.concrete.utils.BitmapUtils;
import com.llegoati.llegoati.infrastructure.models.HomeItem;
import com.llegoati.llegoati.infrastructure.models.ProductDetail;
import com.llegoati.llegoati.infrastructure.models.ProductHomeItem;
import com.llegoati.llegoati.infrastructure.models.ProductItem;
import com.llegoati.llegoati.models.Event;
import com.llegoati.llegoati.models.News;
import com.llegoati.llegoati.recycleranimator.animators.animators.FadeInAnimator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class HomeFragment extends Fragment {

    @Inject
    IUserManager userManager;

    @Inject
    IRepository repository;

    @Bind(R.id.swipe_home_products)
    SwipeRefreshLayout sRLHomeProducts;

    @Bind(R.id.rv_news)
    RecyclerView rvNews;
    //
    @Bind(R.id.rv_events)
    RecyclerView rvEvents;

    NewsAdapter newsAdapter;


    EventsAdapter eventsAdapter;
    View recyclerView;

    public static ProductHomeRecyclerViewAdapter homeProductAdapter;
    List<ProductItem> homeItems;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_index, container, false);

        App.getInstance().getAppComponent().inject(this);
        ButterKnife.bind(this, v);


        recyclerView = v.findViewById(R.id.list_home_product);
        assert recyclerView != null;

        setupRecyclerView((RecyclerView) recyclerView);
        sRLHomeProducts.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FillHomeAsyncTask fillHomeAsyncTask = new FillHomeAsyncTask();
                fillHomeAsyncTask.execute();
            }
        });
        return v;

    }

    private void addElementsToHome() {
        FillHomeAsyncTask fillHomeAsyncTask = new FillHomeAsyncTask();
        fillHomeAsyncTask.execute();
    }


    private void setupRecyclerView(@android.support.annotation.NonNull RecyclerView recyclerView) {

        HomeFragment.homeProductAdapter = new ProductHomeRecyclerViewAdapter(new ArrayList<HomeItem>());
        recyclerView.setItemAnimator(new FadeInAnimator(new OvershootInterpolator(1.0f)));
        recyclerView.getItemAnimator().setAddDuration(7000);
//        recyclerView.getItemAnimator().setRemoveDuration(500);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(HomeFragment.homeProductAdapter);


        newsAdapter = new NewsAdapter();
        eventsAdapter = new EventsAdapter(2);

        rvEvents.setAdapter(eventsAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(App.getInstance().getApplicationContext()) {
            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {

                LinearSmoothScroller smoothScroller = new LinearSmoothScroller(App.getInstance().getApplicationContext()) {
                    private static final float SPEED = 200f;// Change this value (default=25f)

                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        return SPEED / displayMetrics.densityDpi;
                    }
                };
                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);
            }

        };

        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvNews.setLayoutManager(linearLayoutManager);
        rvNews.setAdapter(newsAdapter);
        autoScroll();
        addElementsToHome();


    }

    private void autoScroll() {
        final int speedScroll = 4050;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;

            @Override
            public void run() {
                if (count == newsAdapter.getItemCount())
                    count = 0;
                if (count < newsAdapter.getItemCount()) {

                }

                rvNews.smoothScrollToPosition(++count);
                handler.postDelayed(this, speedScroll);
            }
        };
        handler.postDelayed(runnable, speedScroll);
    }


    private class FillHomeAsyncTask extends AsyncTask<Void, List<ProductItem>, Void> {

        boolean error;
        private List<ProductHomeItem> homePromotions;
        List<News> news;
        List<Event> events;

        @Override
        protected void onPreExecute() {
            error = false;
            homePromotions = new ArrayList<>();
            sRLHomeProducts.setRefreshing(true);
            homeProductAdapter.clearElements();

            newsAdapter.clear();
            eventsAdapter.clear();
            news = new ArrayList<>();
            events = new ArrayList<>();
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (error)
                Toast.makeText(getContext(), "Ocurrió un error!", Toast.LENGTH_LONG).show();
            else {
                for (ProductHomeItem item : homePromotions) {
                    homeProductAdapter.addItem(item);
                    homeProductAdapter.notifyItemInserted(homeProductAdapter.getHomeItemList().indexOf(item));
                }
            }
            for (News item : news) {
                newsAdapter.add(item);
            }
            newsAdapter.notifyDataSetChanged();

            for (Event item : events) {
                eventsAdapter.add(item);
            }

            sRLHomeProducts.setRefreshing(false);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                if (repository instanceof RemoteRepository && !userManager.isUserPermanentAuthenticated() && ((RemoteRepository) repository).isConnectedToService(getResources().getString(R.string.llegoati_url))) {
                    homeProductAdapter.addItem(new HomeItem(BitmapUtils.resourceDrawableToBase64(getContext(), R.drawable.register), ProductHomeRecyclerViewAdapter.ViewType.REGISTER_OR_LOGIN));
                }
                homeItems = repository.promotions();
                for (final ProductItem item : homeItems) {
                    ProductDetail itemDetail = repository.productDetail(item.getProductId());

                    if (itemDetail.getCalifier().getCalifierName().toLowerCase().equals("nuevo")) {
                        // Nuevo
                        String image = repository.images(itemDetail.getId()).get(0);
                        ProductHomeItem newProduct = new ProductHomeItem(image, item.getNameSubcategory(), ProductHomeRecyclerViewAdapter.ViewType.PRODUCT_NEW, item.getProductId(), item.getSeller().getName(), item.getPrice());
                        homePromotions.add(newProduct);
//                        homeProductAdapter.addItem(newProduct);

                    } else if (itemDetail.getCalifier().getCalifierName().toLowerCase().equals("promoción")) {
                        // Promoción
                        String image = repository.images(itemDetail.getId()).get(0);
                        ProductHomeItem promotionProduct = new ProductHomeItem(image, item.getNameSubcategory(), ProductHomeRecyclerViewAdapter.ViewType.PRODUCT_IN_OFFER, item.getProductId(), item.getSeller().getName(), item.getPrice());
                        homePromotions.add(promotionProduct);
//                        homeProductAdapter.addItem(promotionProduct);

                    } else if (itemDetail.getCalifier().getCalifierName().toLowerCase().equals("oferta")) {
                        // Oferta
                        String image = repository.images(itemDetail.getId()).get(0);
                        ProductHomeItem offertProduct = new ProductHomeItem(image, item.getNameSubcategory(), ProductHomeRecyclerViewAdapter.ViewType.PRODUCT_PROMOTION, item.getProductId(), item.getSeller().getName(), item.getPrice());
                        homePromotions.add(offertProduct);
//                        homeProductAdapter.addItem(offertProduct);
                    }
                }
                news = repository.news();
                events = repository.events();

            } catch (Exception e) {
                error = true;
            }
            return null;
        }
    }

}