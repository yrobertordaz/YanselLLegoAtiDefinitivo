package com.llegoati.llegoati.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.llegoati.llegoati.App;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.activities.LoginActivity;
import com.llegoati.llegoati.activities.MainActivity;
import com.llegoati.llegoati.activities.ProductDetailActivity;
import com.llegoati.llegoati.adapter.viewholders.HomeRegisterViewHolder;
import com.llegoati.llegoati.adapter.viewholders.ProductNewViewHolder;
import com.llegoati.llegoati.adapter.viewholders.ProductOfferViewHolder;
import com.llegoati.llegoati.adapter.viewholders.ProductPromotionViewHolder;
import com.llegoati.llegoati.fragments.ProductListActivityFragment;
import com.llegoati.llegoati.infrastructure.models.HomeItem;
import com.llegoati.llegoati.infrastructure.models.ProductHomeItem;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Yansel on 1/6/2017.
 */

public class ProductHomeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @Inject
    Context context;

    List<HomeItem> homeItemList;

    public List<HomeItem> getHomeItemList() {
        return homeItemList;
    }

    public void addItem(HomeItem item) {
        homeItemList.add(item);
//        try {
//            this.notifyItemInserted(homeItemList.indexOf(item));
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//        }
    }

    public void clearElements() {
        homeItemList.clear();
        notifyDataSetChanged();
    }

    public static final String ARG_PRODUCT_ID = "productid";

    public ProductHomeRecyclerViewAdapter(List<HomeItem> productItems) {
        homeItemList = productItems;
    }

    @Override
    public int getItemViewType(int position) {
        if (homeItemList.get(position).getType() == ViewType.REGISTER_OR_LOGIN) {
            return ViewType.REGISTER_OR_LOGIN.ordinal();
        } else if (homeItemList.get(position).getType() == ViewType.PRODUCT_NEW) {
            return ViewType.PRODUCT_NEW.ordinal();
        } else if (homeItemList.get(position).getType() == ViewType.PRODUCT_IN_OFFER) {
            return ViewType.PRODUCT_IN_OFFER.ordinal();
        } else {
            return ViewType.PRODUCT_PROMOTION.ordinal();
        }

    }

    public enum ViewType {
        REGISTER_OR_LOGIN,
        PRODUCT_NEW,
        PRODUCT_PROMOTION,
        PRODUCT_IN_OFFER
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        App.getInstance().getAppComponent().inject(this);
        if (viewType == ViewType.REGISTER_OR_LOGIN.ordinal()) {

            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.register_home_item, parent, false);
            return new com.llegoati.llegoati.adapter.viewholders.HomeRegisterViewHolder(view);

        } else if (viewType == ViewType.PRODUCT_NEW.ordinal()) {
// New product
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.new_product_home_list_content, parent, false);
            return new ProductNewViewHolder(view);

        } else if (viewType == ViewType.PRODUCT_IN_OFFER.ordinal()) {
// In offer product
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.offert_product_home_list_content, parent, false);
            return new ProductOfferViewHolder(view);
        } else {
// With promotion product
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.promotion_product_home_list_content, parent, false);
            return new ProductPromotionViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ProductNewViewHolder) {
            ProductHomeItem newProductHomeItem = (ProductHomeItem) homeItemList.get(position);
            ProductNewViewHolder productNewViewHolder = (ProductNewViewHolder) holder;
//            Glide.with(productNewViewHolder.ivProductImage.getContext())
//                    .load((homeItemList.get(position).getPhoto() != null) ? Base64.decode(homeItemList.get(position).getPhoto(), Base64.DEFAULT) : null)
//                    .asBitmap()
//                    .placeholder(R.drawable.ic_subcategory_empty)
//                    .error(R.drawable.ic_subcategory_empty)
//                    .into(productNewViewHolder.ivProductImage);
            productNewViewHolder.nameSubcategory.setText(newProductHomeItem.getName());
            productNewViewHolder.productPrice.setText(String.format("%.2f cuc", newProductHomeItem.getProductPrice()));
            productNewViewHolder.productSeller.setText(newProductHomeItem.getNameSeller());
            productNewViewHolder.mView.setOnClickListener(null);
            productNewViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent productDetailIntent = new Intent(view.getContext(), ProductDetailActivity.class);
                    productDetailIntent.putExtra(ARG_PRODUCT_ID, ((ProductHomeItem) homeItemList.get(position)).getProductId());
                    productDetailIntent.putExtra(ProductListActivityFragment.ARG_SUBCATEGORY_NAME, ((ProductHomeItem) homeItemList.get(position)).getName());
                    view.getContext().startActivity(productDetailIntent);
                }
            });

            Glide.with(productNewViewHolder.mView.getContext().getApplicationContext()).load((newProductHomeItem.getPhoto() != null) ? Base64.decode(newProductHomeItem.getPhoto(), Base64.DEFAULT) : null)
                    .asBitmap().animate(android.R.anim.fade_in)
                    .placeholder(R.drawable.ic_subcategory_empty)
                    .error(R.drawable.ic_subcategory_empty)
                    .into(productNewViewHolder.imageView);

        } else if (holder instanceof ProductOfferViewHolder) {
            ProductHomeItem offerProductHomeItem = (ProductHomeItem) homeItemList.get(position);
            ProductOfferViewHolder productOfferViewHolder = (ProductOfferViewHolder) holder;
            Glide.with(productOfferViewHolder.imageView.getContext())
                    .load((homeItemList.get(position).getPhoto() != null) ? Base64.decode(homeItemList.get(position).getPhoto(), Base64.DEFAULT) : null)
                    .asBitmap()
                    .placeholder(R.drawable.ic_subcategory_empty)
                    .error(R.drawable.ic_subcategory_empty)
                    .into(productOfferViewHolder.imageView);
            productOfferViewHolder.nameSubcategory.setText(offerProductHomeItem.getName());
            productOfferViewHolder.productPrice.setText(String.format("%.2f cuc", offerProductHomeItem.getProductPrice()));
            productOfferViewHolder.productSeller.setText(offerProductHomeItem.getNameSeller());
            productOfferViewHolder.mView.setOnClickListener(null);
            productOfferViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent productDetailIntent = new Intent(view.getContext(), ProductDetailActivity.class);
                    productDetailIntent.putExtra(ARG_PRODUCT_ID, ((ProductHomeItem) homeItemList.get(position)).getProductId());
                    productDetailIntent.putExtra(ProductListActivityFragment.ARG_SUBCATEGORY_NAME, ((ProductHomeItem) homeItemList.get(position)).getName());
                    view.getContext().startActivity(productDetailIntent);
                }
            });


        } else if (holder instanceof ProductPromotionViewHolder) {
            ProductHomeItem promotionProductHomeItem = (ProductHomeItem) homeItemList.get(position);
            ProductPromotionViewHolder productPromotionViewHolder = (ProductPromotionViewHolder) holder;
            Glide.with(productPromotionViewHolder.imageView.getContext())
                    .load((homeItemList.get(position).getPhoto() != null) ? Base64.decode(homeItemList.get(position).getPhoto(), Base64.DEFAULT) : null)
                    .asBitmap()
                    .placeholder(R.drawable.ic_subcategory_empty)
                    .error(R.drawable.ic_subcategory_empty)
                    .into(productPromotionViewHolder.imageView);
            productPromotionViewHolder.nameSubcategory.setText(promotionProductHomeItem.getName());

            productPromotionViewHolder.productPrice.setText(String.format("%.2f cuc", promotionProductHomeItem.getProductPrice()));
            productPromotionViewHolder.productSeller.setText(promotionProductHomeItem.getNameSeller());
            productPromotionViewHolder.mView.setOnClickListener(null);
            productPromotionViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent productDetailIntent = new Intent(view.getContext(), ProductDetailActivity.class);
                    productDetailIntent.putExtra(ARG_PRODUCT_ID, ((ProductHomeItem) homeItemList.get(position)).getProductId());
                    productDetailIntent.putExtra(ProductListActivityFragment.ARG_SUBCATEGORY_NAME, ((ProductHomeItem) homeItemList.get(position)).getName());
                    view.getContext().startActivity(productDetailIntent);
                }
            });
        } else if (holder instanceof HomeRegisterViewHolder) {
            HomeRegisterViewHolder homeRegisterViewHolder = (HomeRegisterViewHolder) holder;
            homeRegisterViewHolder.mView.setOnClickListener(null);
            homeRegisterViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.INSTANCE, LoginActivity.class);
                    MainActivity.INSTANCE.startActivityForResult(i, MainActivity.LOGIN_CODE);
//                    LoginActivity.start();
                }
            });
        }

//        if (productItemList.get(position).getCalifier() != null) {
//            holder.ivCalifier.setVisibility(View.VISIBLE);
//            holder.ivCalifier.setImageBitmap(RemoteRepository.getBitmap(productItemList.get(position).getCalifier().getCalifierImage()));
//        } else {
//            holder.ivCalifier.setVisibility(View.GONE);
//        }
//        holder.tvProductPrice.setText(String.format("%.2fcuc + Envio", homeItemList.get(position).getPrice()));

//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Context context = v.getContext();

//                Intent productDetailIntent = new Intent(context, ProductDetailActivity.class);
//                productDetailIntent.putExtra(ARG_PRODUCT_ID, productItemList.get(position).getProductId());
//                productDetailIntent.putExtra(ProductListActivityFragment.ARG_SUBCATEGORY_NAME, subcategory);
//                context.startActivity(productDetailIntent);
//            }
//        });

    }


    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return homeItemList.size();
    }

}
