package com.llegoati.llegoati.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.infrastructure.models.Subcategory;

import java.util.List;

import static com.llegoati.llegoati.infrastructure.concrete.RemoteRepository.getBitmap;

/**
 * Created by Richard on 18/10/2016.
 */
public class SubCategoryAdapter extends BaseAdapter {
    private final LayoutInflater mInflater;
    private List<Subcategory> subCategories;

    public SubCategoryAdapter(Context context, List<Subcategory> subCategories) {
        mInflater = LayoutInflater.from(context);
        this.subCategories = subCategories;
    }

    @Override
    public int getCount() {
        return subCategories.size();
    }

    @Override
    public Subcategory getItem(int i) {
        return subCategories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return subCategories.get(i).getSubCategoryId();
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = mInflater.inflate(R.layout.layout_slider, viewGroup, false);
        ((TextView) v.findViewById(R.id.subcategorie_name)).setText(subCategories.get(i).getNameSubcategory());
        Bitmap subcategoryBitmap = getBitmap(subCategories.get(i).getImage());

        Glide.with(v.getContext())
                .load((subCategories.get(i).getImage() != null) ? Base64.decode(subCategories.get(i).getImage(), Base64.DEFAULT) : null)
                .asBitmap()
                .placeholder(R.drawable.ic_subcategory_empty)
                .error(R.drawable.ic_subcategory_empty)
                .into(((ImageView) v.findViewById(R.id.image_subcategory)));


        return v;
    }


}
