package com.llegoati.llegoati.adapter.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.llegoati.llegoati.R;

/**
 * Created by Yansel on 7/16/2017.
 */

public class NewsViewHolder extends RecyclerView.ViewHolder {

    public final TextView tvAbstract;
    public final TextView tvTitle;
    public final ImageView ivLogo;

    public NewsViewHolder(View itemView) {
        super(itemView);
        this.tvAbstract = (TextView) itemView.findViewById(R.id.tv_abstract);
        this.tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        ivLogo = (ImageView) itemView.findViewById(R.id.iv_logo);
    }
}
