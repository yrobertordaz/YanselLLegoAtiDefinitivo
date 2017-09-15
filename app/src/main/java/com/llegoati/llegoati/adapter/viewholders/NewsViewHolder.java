package com.llegoati.llegoati.adapter.viewholders;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.llegoati.llegoati.R;
import com.llegoati.llegoati.models.News;

/**
 * Created by Yansel on 7/16/2017.
 */

public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public final TextView tvAbstract;
    public final TextView tvTitle;
    public final ImageView ivLogo;
    public News item;

    public NewsViewHolder(View itemView) {
        super(itemView);
        this.tvAbstract = (TextView) itemView.findViewById(R.id.tv_abstract);
        this.tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        ivLogo = (ImageView) itemView.findViewById(R.id.iv_logo);
        itemView.setOnClickListener(this);
    }

    public News getItem() {
        return item;
    }

    public void setItem(News item) {
        this.item = item;
    }

    @Override
    public void onClick(View v) {
        String url = item.getUrl();
        Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        v.getContext().startActivity(urlIntent);
    }
}
