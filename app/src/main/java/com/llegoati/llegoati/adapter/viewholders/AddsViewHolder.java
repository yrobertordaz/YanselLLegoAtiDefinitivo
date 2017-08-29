package com.llegoati.llegoati.adapter.viewholders;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import com.llegoati.llegoati.R;

/**
 * Created by Yansel on 7/13/2017.
 */

public class AddsViewHolder extends ViewHolder {

    public final TextView tvTitle;

    public AddsViewHolder(View itemView) {
        super(itemView);
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
    }
}
