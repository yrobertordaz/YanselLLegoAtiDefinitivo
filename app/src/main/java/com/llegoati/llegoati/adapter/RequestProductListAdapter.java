package com.llegoati.llegoati.adapter;

import android.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.adapter.viewholders.RequestProductListViewHolder;
import com.llegoati.llegoati.infrastructure.models.RequestProductItem;
import com.llegoati.llegoati.models.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Yansel on 6/2/2017.
 */

public class RequestProductListAdapter extends RecyclerView.Adapter<RequestProductListViewHolder> {
    List<RequestProductItem> productItems;
    FragmentManager fragmentManager;
    private String requestId;
    private Request request;

    public RequestProductListAdapter(FragmentManager fragmentManager) {
        productItems = new ArrayList<>();
        this.fragmentManager = fragmentManager;
    }

    @Override
    public RequestProductListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_product_item_content, parent, false);
        return new RequestProductListViewHolder(itemView, fragmentManager, requestId);
    }

    @Override
    public void onBindViewHolder(RequestProductListViewHolder holder, int position) {

        RequestProductItem productItem = productItems.get(position);
        holder.productItem = productItem;
        holder.tvSkuValue.setText(productItem.getSku());
        holder.tvOwnerNameValue.setText(String.format(Locale.US, "%s de %s", productItem.getSubcategory().getNameSubcategory(), productItem.getSeller().getName()));
        holder.tvProductCost.setText(String.format("%.2f cuc", productItem.getProductPrice()));
        holder.tvProductCount.setText(productItem.getProductCant().toString());
        holder.setRequest(request);

        Glide.with(holder.ivPhotoRequestProduct.getContext())
                .load((productItem.getProductImage() != null) ? Base64.decode(productItem.getProductImage(), Base64.DEFAULT) : null)
                .asBitmap().animate(android.R.anim.fade_in)
                .placeholder(R.drawable.ic_subcategory_empty)
                .error(R.drawable.ic_subcategory_empty)
                .into(holder.ivPhotoRequestProduct);

        if (request.getEnabled() != null && request.getEnabled().trim().equals("disponible"))
            holder.ivCommentNotification.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return productItems.size();
    }

    public void clear() {
        productItems.clear();
        this.notifyDataSetChanged();
    }

    public void addRequestProduct(RequestProductItem productItem) {
        productItems.add(productItem);
        this.notifyItemInserted(productItems.indexOf(productItem));
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
