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
import com.llegoati.llegoati.adapter.ContactAdapter;
import com.llegoati.llegoati.infrastructure.abstracts.IUserManager;
import com.llegoati.llegoati.infrastructure.events.ContactEvents;
import com.llegoati.llegoati.infrastructure.models.Contact;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * A placeholder fragment containing a simple view.
 */
public class ContactsActivityFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @Inject
    IUserManager userManager;

    @Bind(R.id.rv_contacts)
    RecyclerView recyclerViewContacts;

    @Bind(R.id.swipe_contact_list)
    SwipeRefreshLayout swContactList;
    ContactAdapter contactAdapter;
    FillContactListAsync fillContactListAsync;

    public ContactsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        App.getInstance().getAppComponent().inject(this);
        View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);
        ButterKnife.bind(this, rootView);
        setupRecyclerView();
        EventBus.getDefault().register(this);
        fillContactListAsync = new FillContactListAsync();
        fillContactListAsync.execute();
        swContactList.setOnRefreshListener(this);
        return rootView;
    }

    private void setupRecyclerView() {
        contactAdapter = new ContactAdapter();
        recyclerViewContacts.setAdapter(contactAdapter);
    }

    @Override
    public void onRefresh() {
        fillContactListAsync = new FillContactListAsync();
        fillContactListAsync.execute();
    }

    private class FillContactListAsync extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            swContactList.setRefreshing(true);
            contactAdapter.clear();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            swContactList.setRefreshing(false);
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Contact[] contacts = userManager.user().getContacts();
            try {
                for (int i = 0; i < contacts.length; i++) {
                    contactAdapter.add(contacts[i]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void onEvent(ContactEvents event) {
        fillContactListAsync = new FillContactListAsync();
        fillContactListAsync.execute();
    }
}
