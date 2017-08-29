package com.llegoati.llegoati.adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.adapter.viewholders.ImagesViewHolder;
import com.llegoati.llegoati.infrastructure.concrete.RemoteRepository;
import com.llegoati.llegoati.models.ImagesItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Yansel on 7/22/2017.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesViewHolder> {
    List<ImagesItem> imagesItemList;

    public ImagesAdapter(ImagesItem[] images) {
        if(images.length > 0)
            imagesItemList = Arrays.asList(images);
        else
            imagesItemList = new ArrayList<>();
    }

    @Override
    public ImagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_items, parent, false);
        return new ImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImagesViewHolder holder, int position) {
        ImagesItem item = imagesItemList.get(position);

        if (item.getImage()!=null) {
            holder.ivImage.setImageBitmap(RemoteRepository.getBitmap(item.getImage()));
            item.setImage("");
            System.gc();
        }
        else
            Glide.with(holder.itemView.getContext().getApplicationContext())
                .load(item.getImageUrl())
                .asBitmap().animate(android.R.anim.fade_in)
                .into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return imagesItemList.size();
    }
}
