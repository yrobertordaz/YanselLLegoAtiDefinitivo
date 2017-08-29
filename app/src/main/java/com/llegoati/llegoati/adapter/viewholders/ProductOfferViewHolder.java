package com.llegoati.llegoati.adapter.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.llegoati.llegoati.R;

/**
 * Created by Yansel on 1/6/2017.
 */

public class ProductOfferViewHolder extends RecyclerView.ViewHolder {

    public final ImageView imageView;
    public final View mView;
    public final View productInformation;
    public final TextView nameSubcategory;
    public final TextView productSeller;
    public final TextView productPrice;
    public final ImageView productImage;

    public ProductOfferViewHolder(View itemView) {
        super(itemView);
        this.mView = itemView.findViewById(R.id.content_item);
        imageView = (ImageView) itemView.findViewById(R.id.iv_product_image);
        nameSubcategory = (TextView) itemView.findViewById(R.id.tv_subcategory_name);
        productSeller = (TextView) itemView.findViewById(R.id.product_seller);
        productPrice = (TextView) itemView.findViewById(R.id.tv_product_price);
        productImage = (ImageView) itemView.findViewById(R.id.iv_product_image);
        productInformation = (RelativeLayout) itemView.findViewById(R.id.product_information);
    }
}
