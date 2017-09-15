package com.llegoati.llegoati.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.activities.ProductDetailActivity;
import com.llegoati.llegoati.fragments.ProductListActivityFragment;
import com.llegoati.llegoati.infrastructure.concrete.RemoteRepository;
import com.llegoati.llegoati.infrastructure.models.ProductItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yansel on 13/11/2016.
 */
public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductListViewHolder> {

    List<ProductItem> productItemList;
    String subcategory;

    public static final String ARG_PRODUCT_ID = "productid";

    public ProductRecyclerViewAdapter(String subcategory) {
        productItemList = new ArrayList<>();
        this.subcategory = subcategory;
    }

    @Override
    public ProductListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_content, parent, false);
        return new ProductListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ProductListViewHolder holder, final int position) {
        final ProductItem item = productItemList.get(position);
        holder.mView.setOnClickListener(null);
        Glide.with(holder.imageView.getContext())
                .load((item.getPhotoUrl() != null) ? (item.getPhotoUrl()) : null)
                .asBitmap().animate(android.R.anim.slide_in_left)
                .placeholder(R.drawable.ic_subcategory_empty)
                .error(R.drawable.ic_subcategory_empty)
                .into(holder.imageView);

        if (item.getCalifier() != null) {
            holder.califierImageView.setVisibility(View.VISIBLE);
            holder.califierImageView.setImageBitmap(RemoteRepository.getBitmap(item.getCalifier().getCalifierImage()));
        } else {
            holder.califierImageView.setVisibility(View.GONE);
        }

        holder.priceTextView.setText(String.format((item.getMessenger()) ? "%.2fcuc + Envío" : "%.2fcuc", item.getPrice()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();

                Intent productDetailIntent = new Intent(context, ProductDetailActivity.class);
                productDetailIntent.putExtra(ARG_PRODUCT_ID, item.getProductId());
                productDetailIntent.putExtra(ProductListActivityFragment.ARG_SUBCATEGORY_NAME, subcategory);
                context.startActivity(productDetailIntent);
            }
        });
        // TODO: 15/11/2016  Arreglar para la que la subcategoria pueda tenerla aqui
        holder.nameTextView.setText(String.format("%s de %s", item.getSubcategory().getNameSubcategory(), item.getSeller().getName()));
    }


    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return productItemList.size();
    }

    public void clear() {
        this.productItemList.clear();
        notifyDataSetChanged();
    }

    public void addProduct(ProductItem productItem) {
        this.productItemList.add(productItem);
        notifyItemInserted(this.productItemList.size() - 1);
    }

    public List<ProductItem> getItems() {
        return this.productItemList;
    }

    public class ProductListViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imageView;
        public final TextView priceTextView;
        public final TextView nameTextView;
        public final View mView;
        public final ImageView califierImageView;

        public ProductListViewHolder(View itemView) {
            super(itemView);

            this.mView = itemView.findViewById(R.id.content);
            imageView = (ImageView) itemView.findViewById(R.id.iv_product_image);
            califierImageView = (ImageView) itemView.findViewById(R.id.iv_califier);
            priceTextView = (TextView) itemView.findViewById(R.id.tv_subcategory_price);
            nameTextView = (TextView) itemView.findViewById(R.id.tv_subcategory_name);
        }


    }
}