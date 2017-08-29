package com.llegoati.llegoati.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.llegoati.llegoati.App;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.adapter.ProductRecyclerViewAdapter;
import com.llegoati.llegoati.fragments.ProductListActivityFragment;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.infrastructure.models.AttributesProductInfo;
import com.llegoati.llegoati.infrastructure.models.Comment;
import com.llegoati.llegoati.infrastructure.models.FeatureItem;
import com.llegoati.llegoati.infrastructure.models.ProductDetail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductDetailActivity extends BaseActivity implements PickDialog.IOnPickListener {

    @Inject
    IRepository repository;

    @Bind(R.id.swipe_product_detail)
    SwipeRefreshLayout swpProductDetail;

    @Bind(R.id.name_subcategory)
    TextView subCategoryTextView;

    @Bind(R.id.addToCart)
    FloatingActionButton addToCart;

    @OnClick(R.id.fab_go_shopping_cart)
    public void fabGoShoppingCartOnClick() {
        MainActivity.goShoppingCart = true;
        this.finish();
    }

    @Bind(R.id.product_detail_layout)
    View llcProductDetailLayout;

    @Bind(R.id.main_photo)
    AppCompatImageView mainPhotoImageView;

    @Bind(R.id.sku_value)
    TextView skuTextView;

    @Bind(R.id.tv_product_price)
    TextView priceTextView;

    @Bind(R.id.rating_product)
    AppCompatRatingBar ratingAppCompatRatingBar;

    @Bind(R.id.product_features)
    TextView featuresTextView;

    @Bind(R.id.discount)
    TextView discountTextView;

    @Bind(R.id.product_image_box)
    View productImageBox;

    @Bind(R.id.comment_box)
    LinearLayout commentBox;

    @Bind(R.id.lineComments)
    View lineComments;

    @Bind(R.id.fam_shopping_cart)
    FloatingActionMenu famShoppingCart;

    private ProductDetail productDetail;
    private List<FeatureItem> featureItems;

    @Bind(R.id.image1_product)
    AppCompatImageView photo1ImageView;

    @Bind(R.id.image2_product)
    AppCompatImageView photo2ImageView;

    @Bind(R.id.image3_product)
    AppCompatImageView photo3ImageView;

    @Bind(R.id.image4_product)
    AppCompatImageView photo4ImageView;

    @Bind(R.id.image5_product)
    AppCompatImageView photo5ImageView;

    List<String> images;
    String idProduct;
    String nameSubcategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        idProduct = getIntent().getStringExtra(ProductRecyclerViewAdapter.ARG_PRODUCT_ID);
        nameSubcategory = getIntent().getStringExtra(ProductListActivityFragment.ARG_SUBCATEGORY_NAME);

        App.getInstance().getAppComponent().inject(this);
        ButterKnife.bind(this);
        initializeComponent();

        FillImageAsyncTask fillImageAsyncTask = new FillImageAsyncTask();
        fillImageAsyncTask.execute(idProduct);
    }

    private void initializeComponent() {
        swpProductDetail.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FillImageAsyncTask fillImageAsyncTask = new FillImageAsyncTask();
                fillImageAsyncTask.execute(idProduct);
            }
        });
        subCategoryTextView.setText(nameSubcategory);
        addToCart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PickDialog pickDialog = new PickDialog();
                pickDialog.setOnPickListener(ProductDetailActivity.this);
                pickDialog.setItems(featureItems);
                if (images.size() > 0) {
                    pickDialog.setImageProduct(images.get(0));
                }
                pickDialog.setProductDetail(productDetail);
                pickDialog.show(getSupportFragmentManager(), "");

            }
        });
    }

    @Override
    public void onPick() {
        Snackbar.make(addToCart, "El producto ha sido adicionado satisfactoriamente", Snackbar.LENGTH_LONG).setAction("Ir al carrito de compras", new OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.goShoppingCart = true;
                finish();
            }
        }).show();
    }

    private class FillImageAsyncTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            try {
                images = repository.images(strings[0]);
                productDetail = repository.productDetail(strings[0]);

            } catch (IOException e) {

            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            swpProductDetail.setRefreshing(true);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            fillImages();
            imageEvents();
            fillInformation();
            swpProductDetail.setRefreshing(false);
            llcProductDetailLayout.setVisibility(View.VISIBLE);
            productImageBox.setVisibility(View.VISIBLE);
            addToCart.setVisibility(View.VISIBLE);
            super.onPostExecute(aVoid);
        }

        private void fillInformation() {
            if (productDetail.getMessenger()) {
                famShoppingCart.animate().alpha(1).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        famShoppingCart.setVisibility(View.VISIBLE);
                    }
                });
            } else {
                famShoppingCart.animate().alpha(0).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        famShoppingCart.setVisibility(View.GONE);
                    }
                });
            }
            featureItems = new ArrayList<>();
            skuTextView.setText(productDetail.getSku());
            ratingAppCompatRatingBar.setRating(productDetail.getValueRanking());
            featuresTextView.setText(Html.fromHtml(String.format("<strong>Vendedor:</strong> %s", productDetail.getSeller().getName())));

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
                featuresTextView.setText(Html.fromHtml(featuresTextView.getText() + String.format("<br /><strong>%s:</strong> %s", key, dicAttr.get(key))));
                featureItems.add(new FeatureItem(key, dicAttr.get(key).split(","), dicAttr.get(key).split(",")[0]));
            }

            if (productDetail.getComments()!=null && productDetail.getComments().length > 0) {
                lineComments.setVisibility(View.VISIBLE);

                LayoutInflater layoutInflater = getLayoutInflater();
                for (Comment comment : productDetail.getComments()) {
                    View item = layoutInflater.inflate(R.layout.comment_content_item, commentBox, false);
                    TextView tv = (TextView) item.findViewById(R.id.contentTextView);
                    tv.setText(comment.getTextComment());

                    commentBox.addView(item);

                }
            }

            // Fill Feature item

            FeatureItem fItem = new FeatureItem("Cantidad", null, null);
            fItem.setSelectedValue("1");
            featureItems.add(fItem);
            if (productDetail.getSeller().getLowerPrice() != 0)
                discountTextView.setText(Html.fromHtml(String.format("<strong>RECIBIRÁ UNA REBAJA DE %.2f cuc si compra %.0f o más productos de %s</strong>",
                        productDetail.getSeller().getLowerPrice(),
                        productDetail.getSeller().getLowerParameter(),
                        productDetail.getSeller().getName()
                )));

            else {
                discountTextView.setVisibility(View.GONE);
            }

            priceTextView.setText(Html.fromHtml(String.format(Locale.US, "<strong>%.2f cuc</strong> + Costo de mensajería según el lugar.", productDetail.getUnitPrice())));
        }

        private void fillImages() {

            photo1ImageView = (AppCompatImageView) findViewById(R.id.image1_product);
            photo2ImageView = (AppCompatImageView) findViewById(R.id.image2_product);
            photo3ImageView = (AppCompatImageView) findViewById(R.id.image3_product);
            photo4ImageView = (AppCompatImageView) findViewById(R.id.image4_product);
            photo5ImageView = (AppCompatImageView) findViewById(R.id.image5_product);

            if (images.size() > 0) {
                Glide.with(ProductDetailActivity.this).load((images.get(0) != null) ? Base64.decode(images.get(0), Base64.DEFAULT) : null)
                        .asBitmap().animate(android.R.anim.fade_in)
                        .placeholder(R.drawable.ic_subcategory_empty)
                        .error(R.drawable.ic_subcategory_empty)
                        .into(mainPhotoImageView);

                Glide.with(ProductDetailActivity.this).load((images.get(0) != null) ? Base64.decode(images.get(0), Base64.DEFAULT) : null)
                        .asBitmap().animate(android.R.anim.fade_in)
                        .placeholder(R.drawable.ic_subcategory_empty)
                        .error(R.drawable.ic_subcategory_empty)
                        .override(200, 200)
                        .fitCenter()
                        .into(photo1ImageView);
            } else {
                productImageBox.setVisibility(View.GONE);
            }


            if (images.size() > 1)
                Glide.with(ProductDetailActivity.this).load((images.get(1) != null) ? Base64.decode(images.get(1), Base64.DEFAULT) : null)
                        .asBitmap().animate(android.R.anim.fade_in)
                        .placeholder(R.drawable.ic_subcategory_empty)
                        .error(R.drawable.ic_subcategory_empty)
                        .override(200, 200)
                        .fitCenter()
                        .into(photo2ImageView);
            else
                ((View) photo2ImageView.getParent()).setVisibility(View.GONE);

            if (images.size() > 2)
                Glide.with(ProductDetailActivity.this).load((images.get(2) != null) ? Base64.decode(images.get(2), Base64.DEFAULT) : null)
                        .asBitmap().animate(android.R.anim.fade_in)
                        .placeholder(R.drawable.ic_subcategory_empty)
                        .error(R.drawable.ic_subcategory_empty)
                        .override(200, 200)
                        .fitCenter()
                        .into(photo3ImageView);
            else
                ((View) photo3ImageView.getParent()).setVisibility(View.GONE);

            if (images.size() > 3)
                Glide.with(ProductDetailActivity.this).load((images.get(3) != null) ? Base64.decode(images.get(3), Base64.DEFAULT) : null)
                        .asBitmap().animate(android.R.anim.fade_in)
                        .placeholder(R.drawable.ic_subcategory_empty)
                        .error(R.drawable.ic_subcategory_empty)
                        .override(200, 200)
                        .fitCenter()
                        .into(photo4ImageView);
            else
                ((View) photo4ImageView.getParent()).setVisibility(View.GONE);

            if (images.size() > 4)
                Glide.with(ProductDetailActivity.this).load((images.get(4) != null) ? Base64.decode(images.get(4), Base64.DEFAULT) : null)
                        .asBitmap().animate(android.R.anim.fade_in)
                        .placeholder(R.drawable.ic_subcategory_empty)
                        .error(R.drawable.ic_subcategory_empty)
                        .override(200, 200)
                        .fitCenter()
                        .into(photo5ImageView);
            else
                ((View) photo5ImageView.getParent()).setVisibility(View.GONE);
            imageEvents();
        }

        private void imageEvents() {
            photo1ImageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Glide.with(ProductDetailActivity.this).load((images.size() > 0) ? Base64.decode(images.get(0), Base64.DEFAULT) : null)
                            .asBitmap().animate(android.R.anim.fade_in)
                            .placeholder(R.drawable.ic_subcategory_empty)
                            .error(R.drawable.ic_subcategory_empty)
                            .into(mainPhotoImageView);
                }
            });
            photo2ImageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Glide.with(ProductDetailActivity.this).load((images.size() > 1) ? Base64.decode(images.get(1), Base64.DEFAULT) : null)
                            .asBitmap().animate(android.R.anim.fade_in)
                            .placeholder(R.drawable.ic_subcategory_empty)
                            .error(R.drawable.ic_subcategory_empty)
                            .into(mainPhotoImageView);
                }
            });
            photo3ImageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Glide.with(ProductDetailActivity.this).load((images.size() > 2) ? Base64.decode(images.get(2), Base64.DEFAULT) : null)
                            .asBitmap().animate(android.R.anim.fade_in)
                            .placeholder(R.drawable.ic_subcategory_empty)
                            .error(R.drawable.ic_subcategory_empty)
                            .into(mainPhotoImageView);
                }
            });
            photo4ImageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Glide.with(ProductDetailActivity.this).load((images.size() > 3) ? Base64.decode(images.get(3), Base64.DEFAULT) : null)
                            .asBitmap().animate(android.R.anim.fade_in)
                            .placeholder(R.drawable.ic_subcategory_empty)
                            .error(R.drawable.ic_subcategory_empty)
                            .into(mainPhotoImageView);
                }
            });
            photo5ImageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Glide.with(ProductDetailActivity.this).load((images.size() > 4) ? Base64.decode(images.get(4), Base64.DEFAULT) : null)
                            .asBitmap().animate(android.R.anim.fade_in)
                            .placeholder(R.drawable.ic_subcategory_empty)
                            .error(R.drawable.ic_subcategory_empty)
                            .into(mainPhotoImageView);
                }
            });
        }
    }

}
