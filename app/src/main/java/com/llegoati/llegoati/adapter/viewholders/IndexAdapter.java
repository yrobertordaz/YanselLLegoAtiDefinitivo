package com.llegoati.llegoati.adapter.viewholders;

import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.llegoati.llegoati.R;
import com.llegoati.llegoati.dialogs.IndexDialog;
import com.llegoati.llegoati.models.Index;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yansel on 7/21/2017.
 */

public class IndexAdapter extends RecyclerView.Adapter<IndexViewHolder> {
    private final RecyclerView contentRV;

    List<Index> indices;
    private DialogFragment dialog;

    public IndexAdapter(RecyclerView contentRV) {
        indices = new ArrayList<>();
        this.contentRV = contentRV;
    }

    public void add(Index item) {
        indices.add(item);
    }

    @Override
    public IndexViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.index_list_item, parent, false);
        return new IndexViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IndexViewHolder holder, int position) {
        Index item = indices.get(position);
        holder.tvText.setText(item.getText());
        holder.setContentRV(contentRV);
        holder.setDialog(dialog);

    }

    @Override
    public int getItemCount() {
        return indices.size();
    }

    public void clear() {
        indices.clear();
    }

    public void setDialog(IndexDialog dialog) {
        this.dialog = dialog;
    }
}
