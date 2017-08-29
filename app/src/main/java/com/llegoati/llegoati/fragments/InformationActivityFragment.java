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
import com.llegoati.llegoati.adapter.InformationAdapter;
import com.llegoati.llegoati.adapter.viewholders.IndexAdapter;
import com.llegoati.llegoati.dialogs.IndexDialog;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.models.Index;
import com.llegoati.llegoati.models.Information;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class InformationActivityFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final java.lang.String TAG = InformationActivityFragment.class.getSimpleName();
    View rootView;
    private InformationAdapter informationAdapter;

    @Inject
    IRepository repository;

    @Bind(R.id.srl_information)
    SwipeRefreshLayout srlInformation;

    @Bind(R.id.rv_information)
    RecyclerView rvInformation;
    private IndexAdapter indexAdapter;

    @OnClick(R.id.iv_index_action)
    public void ivIndexActionOnClick() {
        IndexDialog indexDialog = new IndexDialog();
        indexDialog.setIndexAdapter(indexAdapter);
        indexDialog.show(this.getChildFragmentManager(), TAG);
    }

    public InformationActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_information, container, false);
        App.getInstance().getAppComponent().inject(this);
        ButterKnife.bind(this, rootView);
        setupInformationRecyclerView();
        srlInformation.setOnRefreshListener(this);
        FillInformationAsync fillInformationAsync = new FillInformationAsync();
        fillInformationAsync.execute();
        return rootView;
    }

    private void setupInformationRecyclerView() {
        informationAdapter = new InformationAdapter();
        indexAdapter = new IndexAdapter(rvInformation);
        rvInformation.setAdapter(informationAdapter);

    }

    @Override
    public void onRefresh() {
        FillInformationAsync fillInformationAsync = new FillInformationAsync();
        fillInformationAsync.execute();
    }

    private class FillInformationAsync extends AsyncTask<Void, Void, Void> {
        List<Information> infoTmp;

        @Override
        protected void onPreExecute() {
            srlInformation.setRefreshing(true);
            informationAdapter.clear();
            indexAdapter.clear();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            for (Information item : infoTmp) {
                informationAdapter.add(item);
                indexAdapter.add(new Index(item.getQuestion()));
            }
            informationAdapter.notifyDataSetChanged();
            indexAdapter.notifyDataSetChanged();
            srlInformation.setRefreshing(false);
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                infoTmp = repository.informations();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
