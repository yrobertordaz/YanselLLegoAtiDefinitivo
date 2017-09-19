package com.llegoati.llegoati.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.activities.ProductDetailActivity;
import com.llegoati.llegoati.adapter.viewholders.SearchResultViewHolder;
import com.llegoati.llegoati.fragments.ProductListActivityFragment;
import com.llegoati.llegoati.infrastructure.concrete.RemoteRepository;
import com.llegoati.llegoati.infrastructure.models.ProductItem;

import java.util.ArrayList;
import java.util.List;

import static com.llegoati.llegoati.adapter.ProductHomeRecyclerViewAdapter.ARG_PRODUCT_ID;

/**
 * Created by Yansel on 8/8/2017.
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultViewHolder> {
    List<ProductItem> items;

    public SearchResultAdapter() {
        items = new ArrayList<>();
    }

    public void clear() {
        items.clear();
    }

    public void add(ProductItem item) {
        items.add(item);
    }

    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_content, parent, false);
        return new SearchResultViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(SearchResultViewHolder holder, final int position) {
        final ProductItem item = items.get(position);


        Glide.with(holder.ivProductImage.getContext())
                .load((items.get(position).getPhotoUrl() != null) ? item.getPhotoUrl() : null)
                .asBitmap().animate(android.R.anim.slide_in_left)
                .placeholder(R.drawable.ic_subcategory_empty)
                .error(R.drawable.ic_subcategory_empty)
                .into(holder.ivProductImage);

        if (item.getCalifier() != null) {
            holder.ivCalifier.setVisibility(View.VISIBLE);
            holder.ivCalifier.setImageBitmap(RemoteRepository.getBitmap(item.getCalifier().getCalifierImage()));
        } else {
            holder.ivCalifier.setVisibility(View.GONE);
        }
        holder.tvProductPrice.setText(String.format("%.2fcuc + Envío", item.getPrice()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productDetailIntent = new Intent(v.getContext(), ProductDetailActivity.class);
                productDetailIntent.putExtra(ARG_PRODUCT_ID, item.getProductId());
                productDetailIntent.putExtra(ProductListActivityFragment.ARG_SUBCATEGORY_NAME, String.format("%s de %s", item.getSubcategory().getNameSubcategory(), item.getSeller().getName()));
                v.getContext().startActivity(productDetailIntent);
            }
        });
        if (item.getSubcategory() != null)
            holder.tvSubcategoryName.setText(String.format("%s de %s", item.getSubcategory().getNameSubcategory(), item.getSeller().getName()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<ProductItem> getItems() {
        return items;
    }
}
