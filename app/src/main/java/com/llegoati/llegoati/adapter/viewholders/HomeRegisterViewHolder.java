package com.llegoati.llegoati.adapter.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.llegoati.llegoati.R;

/**
 * Created by Yansel on 1/6/2017.
 */

public class HomeRegisterViewHolder extends RecyclerView.ViewHolder {
    public final ImageView imageView;
    public final View mView;

    public HomeRegisterViewHolder(View itemView) {
        super(itemView);
        this.mView = itemView.findViewById(R.id.content_item);
        imageView = (ImageView) itemView.findViewById(R.id.register);
    }
}
