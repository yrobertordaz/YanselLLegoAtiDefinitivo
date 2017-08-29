package com.llegoati.llegoati.adapter.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.llegoati.llegoati.R;

/**
 * Created by Richard on 5/14/2017.
 */

public class SellerViewHolder extends RecyclerView.ViewHolder {


    public final TextView mNameTextView;
    public final TextView mIconTextView;
    public final ImageView mIconImage;
    public final View mView;


    public SellerViewHolder(View itemView) {
        super(itemView);

        mNameTextView = (TextView) itemView.findViewById(R.id.seller_name);
        mIconTextView = (TextView) itemView.findViewById(R.id.seller_icon);
        mIconImage = (ImageView) itemView.findViewById(R.id.seller_icon_image);
        this.mView = itemView.findViewById(R.id.clickView);

    }

}
