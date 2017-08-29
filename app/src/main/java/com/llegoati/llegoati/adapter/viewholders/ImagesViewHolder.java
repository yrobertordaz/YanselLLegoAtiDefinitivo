package com.llegoati.llegoati.adapter.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.llegoati.llegoati.R;

/**
 * Created by Yansel on 7/22/2017.
 */

public class ImagesViewHolder extends RecyclerView.ViewHolder {
    public final ImageView ivImage;

    public ImagesViewHolder(View itemView) {
        super(itemView);
        ivImage = (ImageView) itemView.findViewById(R.id.iv_image);
    }
}
