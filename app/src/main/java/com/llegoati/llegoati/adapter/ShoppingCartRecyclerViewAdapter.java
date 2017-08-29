package com.llegoati.llegoati.adapter;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.Html;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.llegoati.llegoati.App;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.activities.PickDialog;
import com.llegoati.llegoati.activities.ProductDetailActivity;
import com.llegoati.llegoati.fragments.ProductListActivityFragment;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.infrastructure.abstracts.IUserManager;
import com.llegoati.llegoati.infrastructure.concrete.ShoppingCartSharedPreference;
import com.llegoati.llegoati.infrastructure.events.ShoppingCartEvent;
import com.llegoati.llegoati.infrastructure.models.AttributesProductInfo;
import com.llegoati.llegoati.infrastructure.models.FeatureItem;
import com.llegoati.llegoati.infrastructure.models.ProductDetail;
import com.llegoati.llegoati.infrastructure.models.ShoppingCartItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

import static com.llegoati.llegoati.adapter.ProductRecyclerViewAdapter.ARG_PRODUCT_ID;


public class ShoppingCartRecyclerViewAdapter extends Adapter<ShoppingCartRecyclerViewAdapter.ViewHolder> {

    @Inject
    IRepository repository;

    @Inject
    ShoppingCartSharedPreference shoppingCartSharedPreference;

    @Inject
    IUserManager userManagerSharedPreference;

    private final FragmentManager fragmentManager;
    private ProductDetail productDetail;

    public ShoppingCartRecyclerViewAdapter(FragmentManager fragmentManager) {

        this.fragmentManager = fragmentManager;
        App.getInstance().getAppComponent().inject(this);
        shoppingCartSharedPreference.load();

        if (shoppingCartSharedPreference.size() == 0)
            EventBus.getDefault().post(new ShoppingCartEvent(true));
        else
            EventBus.getDefault().post(new ShoppingCartEvent(false));
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_cart_item, parent, false);

        return new ViewHolder(view);
    }

    public Iterator<ShoppingCartItem> getShoppingCartItems() {
        return this.shoppingCartSharedPreference.iterator();
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mItem = shoppingCartSharedPreference.get(position);
        Glide.with(holder.itemView.getContext()).load((holder.mItem.getPhoto() != null) ? Base64.decode(holder.mItem.getPhoto(), Base64.DEFAULT) : null)
                .asBitmap().animate(android.R.anim.fade_in)
                .placeholder(R.drawable.ic_subcategory_empty)
                .error(R.drawable.ic_subcategory_empty)
                .override(200, 200)
                .fitCenter()
                .into(holder.productImage);
        holder.productPrice.setText(String.format("%.2f cuc", holder.mItem.getPrice()));
        holder.productQuantity.setText(Html.fromHtml(String.format("<strong>Cantidad:</strong> %s", holder.mItem.getQuantity())));
        holder.productName.setText(holder.mItem.getName());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//        totalTextView.setText(String.format("%.2f cuc", getTotal()));
        if (userManagerSharedPreference.isUserPermanentAuthenticated()) {
            if (holder.mItem.getQuantity() >= holder.mItem.getSeller().getLowerParameter())
                holder.ivDiscountQuantityNotification.setVisibility(View.GONE);
            else {
                holder.ivDiscountQuantityNotification.setVisibility(View.VISIBLE);
                holder.ivDiscountQuantityNotification.setAnimation(holder.animation_set);
            }
        } else
            holder.ivDiscountQuantityNotification.setVisibility(View.GONE);
    }

    public double getTotal() {
        double total = 0;

        for (ShoppingCartItem item :
                shoppingCartSharedPreference) {
            total += item.getPrice() * item.getQuantity();

        }
        return total;
    }

    @Override
    public int getItemCount() {
        return shoppingCartSharedPreference.size();
    }

    public void clear() {
        this.shoppingCartSharedPreference.clear();
        this.shoppingCartSharedPreference.save();
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener {
        public final View mView;
        public final ImageView productImage;
        public final TextView productName;
        public final TextView productPrice;
        public final TextView productQuantity;
        public final ImageView ivDiscountQuantityNotification;
        public ShoppingCartItem mItem;
        public final View shopping_popup;
        public final AnimationSet animation_set;


        public ViewHolder(final View view) {
            super(view);

            mView = view;
            productName = (TextView) view.findViewById(R.id.product_name);
            productPrice = (TextView) view.findViewById(R.id.tv_product_price);
            productQuantity = (TextView) view.findViewById(R.id.product_quantity);
            productImage = (ImageView) view.findViewById(R.id.iv_product_image);
            shopping_popup = view.findViewById(R.id.btn_shopping_popup);
            ivDiscountQuantityNotification = (ImageView) view.findViewById(R.id.tv_discount_quantity_notification);


            final PopupMenu popupMenu = new PopupMenu(view.getContext(), shopping_popup);
            popupMenu.inflate(R.menu.shopping_menu);
            popupMenu.setOnMenuItemClickListener(this);

            shopping_popup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupMenu.show();
                }
            });

            animation_set = new AnimationSet(view.getContext(), null);
            animation_set.addAnimation(AnimationUtils.loadAnimation(view.getContext(), R.anim.blink));
            ivDiscountQuantityNotification.setAnimation(animation_set);
            ivDiscountQuantityNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MaterialStyledDialog.Builder builder = new MaterialStyledDialog.Builder(view.getContext())
                            .setStyle(Style.HEADER_WITH_ICON)
                            .setIcon(R.drawable.ic_info)
                            .autoDismiss(true)
                            .setCancelable(false)
                            .setDescription(String.format(Locale.US, v.getContext().getString(R.string.notification_discount_quantity),
                                    mItem.getQuantity(),
                                    mItem.getSeller().getName(),
                                    mItem.getSeller().getLowerParameter() - mItem.getQuantity(),
                                    mItem.getSeller().getLowerPrice()
                            ))
                            .setPositiveText(R.string.btn_ok);
                    builder.show();

                }
            });

            productImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailIntent = new Intent(view.getContext(), ProductDetailActivity.class);
                    productDetailIntent.putExtra(ARG_PRODUCT_ID, mItem.getProductId());
                    productDetailIntent.putExtra(ProductListActivityFragment.ARG_SUBCATEGORY_NAME, mItem.getName().substring(0, mItem.getName().lastIndexOf(mItem.getSeller().getName()) - 4));
                    view.getContext().startActivity(productDetailIntent);
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + productPrice.getText() + "'";
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {

            if (item.getItemId() == R.id.delete) {
                shoppingCartSharedPreference.remove(getAdapterPosition());
                Toast.makeText(mView.getContext(), "Producto eliminado satisfactoriamente.", Toast.LENGTH_SHORT).show();
                ShoppingCartRecyclerViewAdapter.this.notifyItemRemoved(getAdapterPosition());
                shoppingCartSharedPreference.save();
                if (shoppingCartSharedPreference.size() == 0)
                    EventBus.getDefault().post(new ShoppingCartEvent(true));
                else
                    EventBus.getDefault().post(new ShoppingCartEvent(false));

            } else if (item.getItemId() == R.id.edit) {
                // Llamar al PickDialog
                ShoppingCartItem shoppingCartItem = shoppingCartSharedPreference.get(getAdapterPosition());

                FillFeaturesAsyncTask fillFeaturesAsyncTask = new FillFeaturesAsyncTask(ShoppingCartRecyclerViewAdapter.this);
                fillFeaturesAsyncTask.execute(shoppingCartItem.getProductId(), String.valueOf(shoppingCartItem.getQuantity()));


//                JsonObjectRequest request = new JsonObjectRequest(
//                        String.format("%s%s?productId=%s", mView.getContext().getString(R.string.llegoati_service), "Product/GetProductDetails", shoppingCartItem.getProductId()),
//                        null,
//                        new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject jsonObject) {
//                                String result = jsonObject.toString();
//                                Gson gson = new Gson();
//                                productDetail = gson.fromJson(result, ProductDetail.class);
//
//
//                                Map<String, String> dicAttr = new HashMap<>();
//                                for (AttributesProductInfo at :
//                                        productDetail.getAttributesProduct()) {
//                                    if (dicAttr.containsKey(at.getAttributeName())) {
//                                        dicAttr.put(at.getAttributeName(), dicAttr.get(at.getAttributeName()) + ", " + at.getAttributeProduct());
//                                    } else {
//                                        dicAttr.put(at.getAttributeName(), at.getAttributeProduct());
//                                    }
//                                }
//                                for (String key : dicAttr.keySet()) {
//                                    featureItems.add(new FeatureItem(key, dicAttr.get(key).split(","), dicAttr.get(key).split(",")[0]));
//                                }
//                                // Fill Feature item
//                                FeatureItem fItem = new FeatureItem("Cantidad", null, null);
//                                fItem.setSelectedValue(String.valueOf(shoppingCartItem.getQuantity()));
//                                featureItems.add(fItem);
//                                PickDialog pickDialog = new PickDialog();
//                                pickDialog.setEditor(true);
//                                pickDialog.setItems(featureItems);
////                                        if(images.size()> 0) {
////                                            pickDialog.setImageProduct(images.get(0));
////                                        }
//                                pickDialog.setProductDetail(productDetail);
//                                pickDialog.show(fragmentManager, "");
//
//
//                            }
//
//
//                        },
//                        null
//                );
//                request.setShouldCache(true);
//                request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                VolleySingleton.getInstance(itemView.getContext()).getRequestQueue().add(request);


            }
            return false;
        }

        private class FillFeaturesAsyncTask extends AsyncTask<String, Void, List<FeatureItem>> {
            private final ShoppingCartRecyclerViewAdapter adapter;
            List<FeatureItem> featureItems = new ArrayList<>();

            public FillFeaturesAsyncTask(ShoppingCartRecyclerViewAdapter shoppingCartRecyclerViewAdapter) {
                this.adapter = shoppingCartRecyclerViewAdapter;
            }

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(List<FeatureItem> featureItems) {

                PickDialog pickDialog = new PickDialog();
                pickDialog.setEditor(true);
                pickDialog.setItems(featureItems);
                pickDialog.setImageProduct(productDetail.getPhoto1());

                pickDialog.setProductDetail(productDetail);
                pickDialog.setProductAdapter(adapter);
                pickDialog.show(fragmentManager, "");
                super.onPostExecute(featureItems);
            }

            @Override
            protected List<FeatureItem> doInBackground(String... strings) {
                try {
                    productDetail = repository.productDetail(strings[0]);
                    Map<String, String> dicAttr = new HashMap<>();
                    for (AttributesProductInfo at :
                            productDetail.getAttributesProduct()) {
                        if (dicAttr.containsKey(at.getAttributeName())) {
                            dicAttr.put(at.getAttributeName(), dicAttr.get(at.getAttributeName()) + ", " + at.getAttributeProduct());
                        } else {
                            dicAttr.put(at.getAttributeName(), at.getAttributeProduct());
                        }
                    }
                    for (String key : dicAttr.keySet()) {
                        featureItems.add(new FeatureItem(key, dicAttr.get(key).split(","), dicAttr.get(key).split(",")[0]));
                    }

                    // Fill Feature item
                    FeatureItem fItem = new FeatureItem("Cantidad", null, null);
                    fItem.setSelectedValue(strings[1]);
                    featureItems.add(fItem);
                    return featureItems;
                } catch (IOException e) {

                }
                return null;
            }
        }

    }
}
