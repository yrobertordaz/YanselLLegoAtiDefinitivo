package com.llegoati.llegoati.adapter.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.llegoati.llegoati.R;

/**
 * Created by Yansel on 8/8/2017.
 */

public class SearchResultViewHolder extends RecyclerView.ViewHolder {

    public final ImageView ivProductImage;
    public final ImageView ivCalifier;
    public final TextView tvProductPrice;
    public final TextView tvSubcategoryName;

    public SearchResultViewHolder(View itemView) {
        super(itemView);
        ivProductImage = (ImageView) itemView.findViewById(R.id.iv_product_image);
        ivCalifier = (ImageView) itemView.findViewById(R.id.iv_califier);
        tvProductPrice = (TextView) itemView.findViewById(R.id.tv_subcategory_price);
        tvSubcategoryName = (TextView) itemView.findViewById(R.id.tv_subcategory_name);
    }
}
