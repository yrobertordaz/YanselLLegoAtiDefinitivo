package com.llegoati.llegoati.adapter.viewholders;

import android.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.llegoati.llegoati.R;
import com.llegoati.llegoati.dialogs.CommentDialog;
import com.llegoati.llegoati.infrastructure.models.RequestProductItem;
import com.llegoati.llegoati.models.Request;

/**
 * Created by Yansel on 6/2/2017.
 */

public class RequestProductListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final FragmentManager fragmentManager;
    private final String requestId;
    public RequestProductItem productItem;
    public final ImageView ivPhotoRequestProduct;
    public final TextView tvSkuValue;
    public final TextView tvOwnerNameValue;
    public final TextView tvProductCount;
    public final TextView tvProductCost;
    public final ImageView ivCommentNotification;
    private Request request;

    public RequestProductListViewHolder(View itemView, FragmentManager fragmentManager, String requestId) {
        super(itemView);
        ivPhotoRequestProduct = (ImageView) itemView.findViewById(R.id.iv_photo);
        tvSkuValue = (TextView) itemView.findViewById(R.id.tv_sku_value);
        tvOwnerNameValue = (TextView) itemView.findViewById(R.id.tv_owner_name_value);
        tvProductCount = (TextView) itemView.findViewById(R.id.tv_count_value);
        tvProductCost = (TextView) itemView.findViewById(R.id.tv_cost_value);
        ivCommentNotification = (ImageView) itemView.findViewById(R.id.iv_comment_notification);

        ivCommentNotification.setOnClickListener(this);
        this.fragmentManager = fragmentManager;
        this.requestId = requestId;
    }

    @Override
    public void onClick(View v) {
        CommentDialog commentDialog = new CommentDialog();
        commentDialog.setRequestId(requestId);
        commentDialog.setProductId(productItem.getIdProduct());
        commentDialog.show(fragmentManager, "");
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
