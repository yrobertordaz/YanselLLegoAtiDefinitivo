package com.llegoati.llegoati.adapter.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.llegoati.llegoati.R;

/**
 * Created by Yansel on 7/17/2017.
 */

public class InformationViewHolder extends RecyclerView.ViewHolder {

    public final TextView tvTitle;
    public final TextView tvContent;

    public InformationViewHolder(View itemView) {
        super(itemView);
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        tvContent = (TextView) itemView.findViewById(R.id.tv_content);
    }
}
