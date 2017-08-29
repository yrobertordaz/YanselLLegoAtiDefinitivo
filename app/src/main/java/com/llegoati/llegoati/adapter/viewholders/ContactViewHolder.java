package com.llegoati.llegoati.adapter.viewholders;

import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.llegoati.llegoati.App;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.infrastructure.abstracts.IUserManager;
import com.llegoati.llegoati.infrastructure.events.ContactEvents;
import com.llegoati.llegoati.infrastructure.models.Contact;
import com.llegoati.llegoati.infrastructure.models.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

/**
 * Created by Yansel on 5/12/2017.
 */

public class ContactViewHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener {
    @Inject
    IUserManager userManager;

    @Inject
    IRepository repository;

    public final TextView tvType;
    public final TextView tvValue;
    public final TextView tvActive;
    public final View contact_popup;


    public ContactViewHolder(View itemView) {
        super(itemView);
        tvType = (TextView) itemView.findViewById(R.id.tv_type);
        tvValue = (TextView) itemView.findViewById(R.id.tv_value);
        tvActive = (TextView) itemView.findViewById(R.id.tv_active);
        contact_popup = itemView.findViewById(R.id.btn_contact_popup);

        final PopupMenu popupMenu = new PopupMenu(itemView.getContext(), contact_popup);
        popupMenu.inflate(R.menu.menu_contact_item);

        popupMenu.setOnMenuItemClickListener(this);
        contact_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });
        App.getInstance().getAppComponent().inject(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_remove_contact) {
            RemoveContactTask removeContactTask = new RemoveContactTask();
            removeContactTask.execute(getAdapterPosition());
        } else if (item.getItemId() == R.id.action_activate_contact) {
            ActivateContactTask activateContactTask = new ActivateContactTask();
            activateContactTask.execute(getAdapterPosition());
        }
        return false;
    }


    List<Contact> contacts;
    User user;

    public class ActivateContactTask extends AsyncTask<Integer, Void, Void> {
        boolean contactActivated;

        @Override
        protected void onPreExecute() {
            contactActivated = false;
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            EventBus.getDefault().post(new ContactEvents(0));
            super.onPostExecute(aVoid);
        }

        public ActivateContactTask() {
            user = userManager.user();
            contacts = Arrays.asList(user.getContacts());
        }

        @Override
        protected Void doInBackground(Integer... params) {
            try {
                List<Contact> tmpContacts = new ArrayList<>();
                for (int i = 0; i < contacts.size(); i++) {

                    tmpContacts.add(contacts.get(i));
                    if (i != params[0] && tmpContacts.get(i).getActive() && tmpContacts.get(i).getType() == contacts.get(params[0]).getType()) {
                        tmpContacts.get(i).setActive(false);
                    } else if (i == params[0] && !tmpContacts.get(i).getActive()) {
                        tmpContacts.get(i).setActive(true);
                    }
                }
                repository.updateContactUser(userManager.user().getId(), tmpContacts.toArray(new Contact[tmpContacts.size()]));
                user.setContacts(tmpContacts.toArray(new Contact[tmpContacts.size()]));
                userManager.saveUser(user);
                contactActivated = true;


            } catch (IOException e) {
                Snackbar.make(tvActive, Html.fromHtml("<span style='color:red;'>Se perdió la conexión.</span>"), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            return null;
        }
    }

    public class RemoveContactTask extends AsyncTask<Integer, Void, Void> {
        private boolean contactRemoved;

        public RemoveContactTask() {
            user = userManager.user();
            contacts = Arrays.asList(user.getContacts());
        }

        @Override
        protected Void doInBackground(Integer... params) {
            try {
                List<Contact> tmpContacts = new ArrayList<>();
                for (int i = 0; i < contacts.size(); i++) {
                    if (i != params[0])
                        tmpContacts.add(contacts.get(i));
                }
                repository.updateContactUser(userManager.user().getId(), tmpContacts.toArray(new Contact[tmpContacts.size()]));
                user.setContacts(tmpContacts.toArray(new Contact[tmpContacts.size()]));
                userManager.saveUser(user);
                contactRemoved = true;

            } catch (IOException e) {
                Snackbar.make(tvActive, Html.fromHtml("<span style='color:red;'>Se perdió la conexión.</span>"), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            contactRemoved = false;
//            load = ProgressDialog.show(AddContactActivity.this, "Adicionando contacto", "Espere por favor...");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
//            load.cancel();
//            load.dismiss();
//            if (contactRemoved) {
//                finish();
//            }
            EventBus.getDefault().post(new ContactEvents(1));
            super.onPostExecute(aVoid);
        }
    }
}
