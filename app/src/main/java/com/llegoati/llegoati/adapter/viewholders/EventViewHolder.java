package com.llegoati.llegoati.adapter.viewholders;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.llegoati.llegoati.R;
import com.llegoati.llegoati.activities.EventsActivity;
import com.llegoati.llegoati.adapter.ImagesAdapter;
import com.llegoati.llegoati.controls.NavigationImage;
import com.llegoati.llegoati.fragments.EventsActivityFragment;
import com.llegoati.llegoati.infrastructure.concrete.utils.UtilsFunction;

/**
 * Created by Yansel on 7/21/2017.
 */

public class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final TextView tvTitle;
    public final TextView tvContent;
    public final TextView tvRange;

    //    public final RecyclerView rvImages;
    public final NavigationImage rvImages;

    public final Button btnSeeMore;
    private ImagesAdapter imagesAdapter;


    public EventViewHolder(View itemView) {
        super(itemView);
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        tvContent = (TextView) itemView.findViewById(R.id.tv_content);
        tvRange = (TextView) itemView.findViewById(R.id.tv_range);
        rvImages = (NavigationImage) itemView.findViewById(R.id.rv_images);


        btnSeeMore = (Button) itemView.findViewById(R.id.btn_see_more);
        btnSeeMore.setOnClickListener(this);
        tvTitle.setOnClickListener(this);

    }

    private void setupRV() {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);

            rvImages.getImagesRV().setLayoutManager(staggeredGridLayoutManager);
            rvImages.getImagesRV().setAdapter(imagesAdapter);
            rvImages.getImagesRV().setHasFixedSize(true);




    }

    public void setImagesAdapter(ImagesAdapter imagesAdapter) {
        this.imagesAdapter = imagesAdapter;
        setupRV();
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putLong(EventsActivityFragment.SMOOTH_SCROLL_TO_POSITION, getAdapterPosition());
        EventsActivity.start(bundle);
    }
}
