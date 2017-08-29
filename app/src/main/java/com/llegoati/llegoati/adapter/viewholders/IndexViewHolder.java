package com.llegoati.llegoati.adapter.viewholders;

import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.llegoati.llegoati.R;

/**
 * Created by Yansel on 7/21/2017.
 */

class IndexViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    final TextView tvText;
    private RecyclerView contentRV;
    private DialogFragment dialog;

    public IndexViewHolder(View itemView) {
        super(itemView);
        tvText = (TextView) itemView.findViewById(R.id.tv_text);
        itemView.setOnClickListener(this);
    }

    public void setContentRV(RecyclerView contentRV) {
        this.contentRV = contentRV;
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        contentRV.smoothScrollToPosition(position);
        this.dialog.dismiss();
    }

    public void setDialog(DialogFragment dialog) {
        this.dialog = dialog;
    }
}
