package com.llegoati.llegoati.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.llegoati.llegoati.R;
import com.llegoati.llegoati.adapter.viewholders.ContactViewHolder;
import com.llegoati.llegoati.infrastructure.models.Contact;

import java.util.ArrayList;

/**
 * Created by Yansel on 5/12/2017.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> {

    ArrayList<Contact> contacts;

    public ContactAdapter() {

        this.contacts = new ArrayList<>();
    }


    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_content, parent, false);

        return new ContactViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        Contact item = contacts.get(position);

        holder.tvActive.setVisibility(item.getActive() ? View.VISIBLE : View.INVISIBLE);
        switch (item.getType()) {
            case 0:
                holder.tvType.setText("Dirección");
                break;
            case 1:
                holder.tvType.setText("Teléfono movil");
                break;
            case 2:
                holder.tvType.setText("Teléfono fijo");
                break;
        }
        holder.tvValue.setText(item.getContactValue());

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void clear() {
        contacts.clear();
        notifyDataSetChanged();
    }

    public void add(Contact contact) {
        contacts.add(contact);
        notifyItemInserted(contacts.size() - 1);
    }
}
