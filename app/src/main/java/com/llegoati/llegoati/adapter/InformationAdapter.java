package com.llegoati.llegoati.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.llegoati.llegoati.R;
import com.llegoati.llegoati.adapter.viewholders.InformationViewHolder;
import com.llegoati.llegoati.models.Information;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yansel on 7/17/2017.
 */

public class InformationAdapter extends RecyclerView.Adapter<InformationViewHolder> {
    List<Information> informations;

    public void add(Information info) {
        informations.add(info);
    }

    public InformationAdapter() {
        informations = new ArrayList<>();
    }

    @Override
    public InformationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.information_list_item, parent, false);

        return new InformationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InformationViewHolder holder, int position) {
        Information item = informations.get(position);
        holder.tvTitle.setText(item.getQuestion());
        holder.tvContent.setText(item.getAnswer());
    }

    @Override
    public int getItemCount() {
        return informations.size();
    }

    public void clear() {
        informations.clear();
    }
}
