package com.llegoati.llegoati.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.llegoati.llegoati.R;
import com.llegoati.llegoati.infrastructure.models.FeatureItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yansel on 1/3/2017.
 */

public class PickFeatureRecyclerViewAdapter extends RecyclerView.Adapter<PickFeatureRecyclerViewAdapter.PickFeatureViewHolder> {

    List<FeatureItem> featureItems = new ArrayList<>();

    public PickFeatureRecyclerViewAdapter(List<FeatureItem> featureItems) {
        this.featureItems = featureItems;
    }

    @Override
    public PickFeatureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.feature_list_content, parent, false);

        return new PickFeatureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PickFeatureViewHolder holder, final int position) {
        final FeatureItem item = featureItems.get(position);
        holder.property.setText(item.getName());
        ArrayAdapter<String> spinnerAdapter = null;
        if (item.getValues() != null) {

            spinnerAdapter = new ArrayAdapter<String>(holder.spinner_property.getContext(),
                    R.layout.custom_spinner_item, item.getValues());
        } else {
            spinnerAdapter = new ArrayAdapter<String>(holder.spinner_property.getContext(),
                    R.layout.custom_spinner_item, holder.spinner_property.getResources().getStringArray(R.array.count));
        }

        holder.spinner_property.setAdapter(spinnerAdapter);
        int positionSelected = spinnerAdapter.getPosition(item.getSelectedValue());
        holder.spinner_property.setSelection(positionSelected);
        holder.spinner_property.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                if (item.getValues() == null)
                    item.setValues(view.getResources().getStringArray(R.array.count));
                item.setSelectedValue(item.getValues()[pos]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        new android.widget.AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
//                Log.d("slfkg",item.toString());
////                item.setSelectedValue(item.getValues()[pos]);
//            }
//        }
    }

    @Override
    public int getItemCount() {
        return featureItems.size();
    }


    public class PickFeatureViewHolder extends RecyclerView.ViewHolder {
        public final TextView property;
        public final Spinner spinner_property;

        public PickFeatureViewHolder(View itemView) {
            super(itemView);
            this.property = (TextView) itemView.findViewById(R.id.label_property);
            this.spinner_property = (Spinner) itemView.findViewById(R.id.spinner_property);

        }
    }

}
