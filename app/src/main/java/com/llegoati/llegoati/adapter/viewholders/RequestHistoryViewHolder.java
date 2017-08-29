package com.llegoati.llegoati.adapter.viewholders;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.llegoati.llegoati.R;
import com.llegoati.llegoati.activities.RequestDetailActivity;

/**
 * Created by Yansel on 5/19/2017.
 */

public class RequestHistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public static String REQUEST_ID = "request_id";
    public String requestId;
    public final ImageView ivCommentNotification;
    public final TextView tvIDValue;
    public final TextView tvCostValue;
    public final TextView tvStatusValue;
    public final TextView tvDateValue;

    public RequestHistoryViewHolder(View itemView) {
        super(itemView);
        tvIDValue = (TextView) itemView.findViewById(R.id.tv_id_value);
        tvCostValue = (TextView) itemView.findViewById(R.id.tv_cost_value);
        tvStatusValue = (TextView) itemView.findViewById(R.id.tv_status_value);
        tvDateValue = (TextView) itemView.findViewById(R.id.tv_date_value);
        ivCommentNotification = (ImageView) itemView.findViewById(R.id.iv_comment_notification);
        itemView.setOnClickListener(this);
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public void onClick(View v) {

        Bundle bundle = new Bundle();
        bundle.putString(REQUEST_ID, requestId);

        Intent intent = new Intent(v.getContext(), RequestDetailActivity.class);
        intent.putExtras(bundle);
        v.getContext().startActivity(intent);
    }
}
