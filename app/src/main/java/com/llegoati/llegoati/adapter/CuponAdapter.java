package com.llegoati.llegoati.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.llegoati.llegoati.R;
import com.llegoati.llegoati.adapter.viewholders.CuponViewHolder;
import com.llegoati.llegoati.infrastructure.models.Cupon;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

import static com.llegoati.llegoati.infrastructure.concrete.utils.DateUtils.formatDate;

/**
 * Created by Yansel on 6/2/2017.
 */

public class CuponAdapter extends RecyclerView.Adapter<CuponViewHolder> {
    ArrayList<Cupon> cupons;

    public CuponAdapter() {
        this.cupons = new ArrayList<>();
    }

    @Override
    public CuponViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lotery_code_content, parent, false);
        return new CuponViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(CuponViewHolder holder, int position) {
        Cupon cupon = cupons.get(position);
        holder.tvCodeValue.setText(cupon.getCode());
        holder.tvDiscountValue.setText(String.format(Locale.US, "%.2f cuc", cupon.getEfective()));
        try {
            holder.tvDateValue.setText(formatDate(cupon.getDateConsumedByClient()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return cupons.size();
    }

    public void clear() {
        cupons.clear();
        this.notifyDataSetChanged();
    }

    public void addCupon(Cupon cupon) {
        cupons.add(cupon);
        this.notifyItemInserted(cupons.indexOf(cupon));

    }
}
