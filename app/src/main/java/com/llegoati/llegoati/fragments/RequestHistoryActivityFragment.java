package com.llegoati.llegoati.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.llegoati.llegoati.App;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.adapter.RequestAdapter;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.infrastructure.abstracts.IUserManager;
import com.llegoati.llegoati.models.Request;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class RequestHistoryActivityFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @Inject
    IRepository repository;

    @Inject
    IUserManager userManager;

    @Bind(R.id.swipe_request_list)
    SwipeRefreshLayout swRequestList;

    @Bind(R.id.rv_contacts)
    RecyclerView recyclerViewRequest;

    RequestAdapter requestAdapter;

    public RequestHistoryActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_request_history, container, false);
        App.getInstance().getAppComponent().inject(this);
        ButterKnife.bind(this, rootView);
        setupRecyclerView();
        FillRequestHistoryListAsync fillRequestHistoryListAsync = new FillRequestHistoryListAsync();
        fillRequestHistoryListAsync.execute();
        swRequestList.setOnRefreshListener(this);
        return rootView;

    }

    private void setupRecyclerView() {
        requestAdapter = new RequestAdapter();
        recyclerViewRequest.setAdapter(requestAdapter);
    }

    @Override
    public void onRefresh() {
        FillRequestHistoryListAsync fillRequestHistoryListAsync = new FillRequestHistoryListAsync();
        fillRequestHistoryListAsync.execute();
    }

    private class FillRequestHistoryListAsync extends AsyncTask<Void, Void, Void> {
        List<Request> requests;

        @Override
        protected void onPreExecute() {
            swRequestList.setRefreshing(true);
            requestAdapter.clear();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            for (int i = 0; i < requests.size(); i++) {
                requestAdapter.add(requests.get(i));
            }
            requestAdapter.notifyDataSetChanged();
            swRequestList.setRefreshing(false);
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                requests = repository.requestHistory(userManager.user().getId(), false);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
