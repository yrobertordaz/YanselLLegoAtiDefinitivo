package com.llegoati.llegoati.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.adapter.viewholders.NewsViewHolder;
import com.llegoati.llegoati.models.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yansel on 7/16/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {
    List<News> items;

    public NewsAdapter() {
        items = new ArrayList<>();
    }

    public void add(News item) {
        items.add(item);
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_item, parent, false);

        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        News item = items.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvAbstract.setText(item.getAbstrac());
        Glide.with(holder.itemView.getContext().getApplicationContext()).load((item.getLogo() != null) ? Base64.decode(item.getLogo(), Base64.DEFAULT) : null)
                .asBitmap().animate(android.R.anim.fade_in)
                .placeholder(R.drawable.ic_subcategory_empty)
                .error(R.drawable.ic_subcategory_empty)
                .into(holder.ivLogo);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }
}
