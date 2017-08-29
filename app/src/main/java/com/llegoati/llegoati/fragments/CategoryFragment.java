package com.llegoati.llegoati.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.llegoati.llegoati.App;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.adapter.CategoryAdapter;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.infrastructure.models.Category;

import java.util.Iterator;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.llegoati.llegoati.activities.MainActivity.categoryAdapter;

/**
 * Created by Richard on 15/10/2016.
 */
public class CategoryFragment extends Fragment {

    @Inject
    IRepository repository;

    @Bind(R.id.swipe_category_products)
    SwipeRefreshLayout swpCategoryProducts;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_catalogo, container, false);

        RecyclerView listCategories = (RecyclerView) v.findViewById(R.id.content_catalogo);
        App.getInstance().getAppComponent().inject(this);
        ButterKnife.bind(this, v);

        categoryAdapter = new CategoryAdapter();
        listCategories.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(container.getContext());


        listCategories.setLayoutManager(llm);
        listCategories.setAdapter(categoryAdapter);
        FillCategoriesAsyncTask fillCategoriesAsyncTask = new FillCategoriesAsyncTask();
        fillCategoriesAsyncTask.execute();
        swpCategoryProducts.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FillCategoriesAsyncTask fillCategoriesAsyncTask = new FillCategoriesAsyncTask();
                fillCategoriesAsyncTask.execute();
            }
        });
        return v;
    }

    private class FillCategoriesAsyncTask extends AsyncTask<Void, Void, Void> {
        Iterator<Category> catIterator;

        @Override
        protected void onPreExecute() {
            swpCategoryProducts.setRefreshing(true);
            categoryAdapter.clear();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {

                catIterator = repository.categories().iterator();


            } catch (Exception e) {

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (catIterator != null) {
                while (catIterator.hasNext()) {
                    categoryAdapter.addCategory(catIterator.next());
                }
            } else {
                Toast.makeText(getActivity(), R.string.error_requesting_categories, Toast.LENGTH_SHORT)
                        .show();
            }
            swpCategoryProducts.setRefreshing(false);
            super.onPostExecute(aVoid);
        }
    }
}
