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
import com.llegoati.llegoati.adapter.EventsAdapter;
import com.llegoati.llegoati.adapter.viewholders.IndexAdapter;
import com.llegoati.llegoati.dialogs.IndexDialog;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.models.Event;
import com.llegoati.llegoati.models.Index;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class EventsActivityFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = EventsActivityFragment.class.getSimpleName();
    public static final String SMOOTH_SCROLL_TO_POSITION = "smooth_scroll_to_position";

    @Inject
    IRepository repository;

    @Bind(R.id.rv_events)
    RecyclerView rvEvents;
    @Bind(R.id.srl_event)
    SwipeRefreshLayout srlEvent;
    private int smoothToPosition;

    @OnClick(R.id.iv_index_action)
    public void ivIndexAction() {
        IndexDialog indexDialog = new IndexDialog();
        indexDialog.setIndexAdapter(indexEventAdapter);
        indexDialog.show(this.getChildFragmentManager(), TAG);
    }

    EventsAdapter eventsAdapter;
    private IndexAdapter indexEventAdapter;

    public EventsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        App.getInstance().getAppComponent().inject(this);
        ButterKnife.bind(this, view);
        setupRvEvents();
        srlEvent.setOnRefreshListener(this);
        FillEventsAsync fillEventsAsync = new FillEventsAsync();
        fillEventsAsync.execute();
        smoothToPosition = (int) getActivity().getIntent().getLongExtra(SMOOTH_SCROLL_TO_POSITION, -1);
        return view;
    }

    private void setupRvEvents() {
        eventsAdapter = new EventsAdapter(0);
        rvEvents.setAdapter(eventsAdapter);
        rvEvents.setHasFixedSize(false);
        indexEventAdapter = new IndexAdapter(rvEvents);
    }

    @Override
    public void onRefresh() {
        FillEventsAsync fillEventsAsync = new FillEventsAsync();
        fillEventsAsync.execute();
    }

    private class FillEventsAsync extends AsyncTask<Void, Void, Void> {
        List<Event> infoTmp;

        @Override
        protected void onPreExecute() {
            srlEvent.setRefreshing(true);
            eventsAdapter.clear();
            indexEventAdapter.clear();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            for (Event item : infoTmp) {
                eventsAdapter.add(item);
                indexEventAdapter.add(new Index(item.getTitle()));
            }
            eventsAdapter.notifyDataSetChanged();
            indexEventAdapter.notifyDataSetChanged();
            srlEvent.setRefreshing(false);
            if (smoothToPosition != -1) {
                rvEvents.smoothScrollToPosition(smoothToPosition);
                smoothToPosition = -1;
            }
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                infoTmp = repository.events();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
