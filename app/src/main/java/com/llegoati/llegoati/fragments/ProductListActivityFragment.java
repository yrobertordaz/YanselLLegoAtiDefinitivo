package com.llegoati.llegoati.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.llegoati.llegoati.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProductListActivityFragment extends Fragment {

    public static final String ARG_SUBCATEGORY_ID = "product_id";
    public static final String ARG_SUBCATEGORY_NAME = "subcategory_name";

    public ProductListActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_list, container, false);
    }
}
