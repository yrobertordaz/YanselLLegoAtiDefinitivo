package com.llegoati.llegoati.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.llegoati.llegoati.App;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.adapter.PickFeatureRecyclerViewAdapter;
import com.llegoati.llegoati.adapter.ShoppingCartRecyclerViewAdapter;
import com.llegoati.llegoati.infrastructure.concrete.ShoppingCartSharedPreference;
import com.llegoati.llegoati.infrastructure.events.ShoppingCartEvent;
import com.llegoati.llegoati.infrastructure.models.FeatureItem;
import com.llegoati.llegoati.infrastructure.models.ProductDetail;
import com.llegoati.llegoati.infrastructure.models.ShoppingCartItem;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

/**
 * Created by Yansel on 1/3/2017.
 */

public class PickDialog extends DialogFragment implements MaterialDialog.SingleButtonCallback {
    @Inject
    ShoppingCartSharedPreference shoppingCartSharedPreference;

    private View form;
    private RecyclerView recyclerView;
    private PickFeatureRecyclerViewAdapter adapter;
    private List<FeatureItem> items;
    private ProductDetail productDetail;
    private String productImage;
    private boolean editor = false;
    private ShoppingCartRecyclerViewAdapter productAdapter;
    private IOnPickListener onPickListener;

    public void setEditor(boolean editor) {
        this.editor = editor;
    }

    public List<FeatureItem> getItems() {
        return items;
    }

    public void setItems(List<FeatureItem> items) {
        this.items = items;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        form = getActivity().getLayoutInflater().inflate(R.layout.dialog_pick_features, null);
        App.getInstance().getAppComponent().inject(this);

        recyclerView = (RecyclerView) form.findViewById(R.id.pick_feature_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        MaterialStyledDialog.Builder builder1 = new MaterialStyledDialog.Builder(getActivity())
                .setTitle(R.string.pick_dialog_title)
                .setStyle(Style.HEADER_WITH_TITLE)
                .setCustomView(form)
                .autoDismiss(true)
                .setNegativeText(R.string.Cancel)
                .setPositiveText(R.string.Ok)
                .onPositive(this);
        return builder1.show();
//        return (builder.setTitle(R.string.pick_dialog_title)
//                .setView(form)
//                .setPositiveButton(R.string.Ok, this)
//                .setNegativeButton(R.string.Cancel, this)).create();
    }

    private void setupRecyclerView(@android.support.annotation.NonNull RecyclerView recyclerView) {

        adapter = new PickFeatureRecyclerViewAdapter(items);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }

//    @Override
//    public void onClick(DialogInterface dialog, int which) {
//
//        //// TODO: 1/13/2017 Comprobar que todos los parametros son iguales(color, material, etc), por ahora solo se toma en cuenta el id del producto
//
//        if(which == DialogInterface.BUTTON_POSITIVE){
//            int index;
//            for (index = 0; index < items.size(); index++)
//                if (items.get(index).getName().toLowerCase().equals("cantidad"))
//                    break;
//            if(shoppingCartSharedPreference.size() > 0){
//                Iterator<ShoppingCartItem> shoppingCartIterator = shoppingCartSharedPreference.iterator();
//                while (shoppingCartIterator.hasNext()){
//                    ShoppingCartItem item = shoppingCartIterator.next();
//                    if(item.getProductId() == productDetail.getId()){
//
//
//                        if (items.size() == 1) {
//                            ShoppingCartItem shoppingCartItem = new ShoppingCartItem(productImage, Integer.parseInt(items.get(index).getSelectedValue()), productDetail.getId(), productDetail.getUnitPrice(), productDetail.getSubCategory().getNameSubcategory() + " de " + productDetail.getSeller().getName());
//                            shoppingCartSharedPreference.add(shoppingCartItem);
//                        } else {
//                            ShoppingCartItem shoppingCartItem = item;
//                            shoppingCartItem.setQuantity(Integer.parseInt(items.get(index).getSelectedValue()) + (isEditor() ? 0 : shoppingCartItem.getQuantity()));
//                            shoppingCartSharedPreference.add(shoppingCartItem);
//                        }
//                        shoppingCartSharedPreference.save();
//                    }
//                }
//            }else{
//                if (items.size() == 0) {
//                    ShoppingCartItem shoppingCartItem = new ShoppingCartItem(productImage, Integer.parseInt(items.get(index).getSelectedValue()), productDetail.getId(), productDetail.getUnitPrice(), productDetail.getSubCategory().getNameSubcategory() + " de " + productDetail.getSeller().getName());
//                    shoppingCartSharedPreference.add(shoppingCartItem);
//                } else {
//                    ShoppingCartItem shoppingCartItem = item;
//                    shoppingCartItem.setQuantity(Integer.parseInt(items.get(index).getSelectedValue()) + (isEditor() ? 0 : shoppingCartItem.getQuantity()));
//                    shoppingCartSharedPreference.add(shoppingCartItem);
//                }
//                shoppingCartSharedPreference.save();
//            }
//        }
//    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    public void setImageProduct(String s) {
        this.productImage = s;
    }

    public boolean isEditor() {
        return editor;
    }

    @Override
    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
        if (which == DialogAction.POSITIVE) {
            // Verificar que el producto no existe
            int index;

            boolean exist = false;
            for (index = 0; index < items.size(); index++)
                if (items.get(index).getName().toLowerCase().equals("cantidad"))
                    break;

            Iterator<ShoppingCartItem> shoppingCartIterator = shoppingCartSharedPreference.iterator();
            while (shoppingCartIterator.hasNext()) {
                ShoppingCartItem item = shoppingCartIterator.next();
                if (item.getProductId().equals(productDetail.getId())) {

                    if (isEditor())
                        item.setQuantity(Integer.parseInt(items.get(index).getSelectedValue()));
                    else
                        item.setQuantity(Integer.parseInt(items.get(index).getSelectedValue()) + item.getQuantity());
                    shoppingCartSharedPreference.save();
                    exist = true;
                    break;
//                            ShoppingCartItem shoppingCartItem = new ShoppingCartItem(productImage, Integer.parseInt(items.get(index).getSelectedValue()), productDetail.getId(), productDetail.getUnitPrice(), productDetail.getSubCategory().getNameSubcategory() + " de " + productDetail.getSeller().getName());
//                            shoppingCartSharedPreference.add(shoppingCartItem);

//                        if (items.size() == 0) {
//                            ShoppingCartItem shoppingCartItem = new ShoppingCartItem(productImage, Integer.parseInt(items.get(index).getSelectedValue()), productDetail.getId(), productDetail.getUnitPrice(), productDetail.getSubCategory().getNameSubcategory() + " de " + productDetail.getSeller().getName());
//                            shoppingCartSharedPreference.add(shoppingCartItem);
//                        } else {
//                            ShoppingCartItem shoppingCartItem = item;
//                            shoppingCartItem.setQuantity(Integer.parseInt(items.get(index).getSelectedValue()) + (isEditor() ? 0 : shoppingCartItem.getQuantity()));
//                            shoppingCartSharedPreference.add(shoppingCartItem);
//                        }
//                        shoppingCartSharedPreference.save();
                }
            }
            if (!exist) {
                // TODO: 8/5/2017 Adicionar un arreglo de atributos al producto, para enviar los atributos a la orden
                ShoppingCartItem shoppingCartItem = new ShoppingCartItem(productImage,
                        Integer.parseInt(items.get(index).getSelectedValue()),
                        productDetail.getId(), productDetail.getUnitPrice(),
                        productDetail.getSubCategory().getNameSubcategory() + " de " + productDetail.getSeller().getName(),
                        productDetail.getSeller(),
                        productDetail.getPointByUnit());
                shoppingCartSharedPreference.add(shoppingCartItem);
                shoppingCartSharedPreference.save();
            }
            this.onPickListener.onPick();
            EventBus.getDefault().post(new ShoppingCartEvent(false));
        }
    }

    public void setProductAdapter(ShoppingCartRecyclerViewAdapter adapter) {
        this.productAdapter = adapter;
    }

    public void setOnPickListener(IOnPickListener onPickListener) {
        this.onPickListener = onPickListener;
    }

    public interface IOnPickListener {
        void onPick();
    }
}
