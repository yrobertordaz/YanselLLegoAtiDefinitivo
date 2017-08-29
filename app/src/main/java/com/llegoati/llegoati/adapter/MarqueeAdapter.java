package com.llegoati.llegoati.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.llegoati.llegoati.R;
import com.llegoati.llegoati.adapter.viewholders.AddsViewHolder;
import com.llegoati.llegoati.models.Adds;

import java.util.ArrayList;

/**
 * Created by Yansel on 7/13/2017.
 */

public class MarqueeAdapter extends RecyclerView.Adapter<AddsViewHolder> {
    ArrayList<Adds> addses;

    public MarqueeAdapter() {
        super();
        addses = new ArrayList<>();
    }

    @Override
    public AddsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adds_item, parent, false);
        return new AddsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddsViewHolder holder, int position) {
        Adds adds = addses.get(position);
        holder.tvTitle.setText(adds.getTitle());
    }

    public ArrayList<Adds> getAddses() {
        return addses;
    }

    @Override
    public int getItemCount() {
        return addses.size();
    }
}
