package com.llegoati.llegoati.adapter.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.llegoati.llegoati.R;

/**
 * Created by Yansel on 6/2/2017.
 */

public class CuponViewHolder extends RecyclerView.ViewHolder {
    public final TextView tvCodeValue;
    public final TextView tvDateValue;
    public final TextView tvDiscountValue;

    public CuponViewHolder(View view) {
        super(view);
        tvCodeValue = (TextView) view.findViewById(R.id.tv_code_value);
        tvDateValue = (TextView) view.findViewById(R.id.tv_date_value);
        tvDiscountValue = (TextView) view.findViewById(R.id.tv_discount_value);
    }
}
