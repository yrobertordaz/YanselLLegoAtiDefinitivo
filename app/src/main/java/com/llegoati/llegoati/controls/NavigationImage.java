package com.llegoati.llegoati.controls;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.llegoati.llegoati.R;


public class NavigationImage extends RelativeLayout implements View.OnClickListener {


    private ImageView mImageNext;
    private ImageView mImagePrevious;
    private RecyclerView mImagesRecyclerView;
    int count = 0;

    public NavigationImage(Context context) {
        super(context);
    }

    public NavigationImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.navigation_image, this, true);
        View rootView = getChildAt(0);
        mImagePrevious = (ImageView) rootView.findViewById(R.id.iv_previous);
        mImagesRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_images);
        mImageNext = (ImageView) rootView.findViewById(R.id.iv_next);
        mImageNext.setOnClickListener(this);
        mImagePrevious.setOnClickListener(this);

    }

    public NavigationImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public NavigationImage(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public RecyclerView getImagesRV() {
        return mImagesRecyclerView;
    }

    @Override
    public void onClick(View v) {
        if (v.equals(mImageNext)) {
            if (count >= mImagesRecyclerView.getAdapter().getItemCount()) {
                count = 0;
            } else
                count++;
        } else {
            if (count == 0)
                count = mImagesRecyclerView.getAdapter().getItemCount();
            count--;
        }
        getImagesRV().smoothScrollToPosition(count);
    }
}
