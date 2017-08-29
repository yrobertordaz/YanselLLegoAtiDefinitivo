package com.llegoati.llegoati.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.llegoati.llegoati.App;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.infrastructure.abstracts.IUserManager;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yansel on 6/6/2017.
 */

public class CommentDialog extends DialogFragment implements RatingBar.OnRatingBarChangeListener, MaterialDialog.SingleButtonCallback {
    String requestId;
    String productId;
    @Inject
    IUserManager userManager;
    @Inject
    IRepository repository;

    @Bind(R.id.et_comment)
    EditText etComment;
    @Bind(R.id.ratb_msg)
    RatingBar ratbMsg;

    @Bind(R.id.ratb_product)
    RatingBar ratbProduct;

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View form = getActivity().getLayoutInflater().inflate(R.layout.comment_dialog, null);

        ButterKnife.bind(this, form);
        ratbMsg.setOnRatingBarChangeListener(this);
        ratbProduct.setOnRatingBarChangeListener(this);
        App.getInstance().getAppComponent().inject(this);
        MaterialStyledDialog.Builder builder1 = new MaterialStyledDialog.Builder(getActivity())
                .setTitle(R.string.comment_dialog_title)
                .setStyle(Style.HEADER_WITH_TITLE)
                .setCustomView(form)
                .setNegativeText(R.string.Cancel)
                .setPositiveText(R.string.Ok)
                .onPositive(this);
        return builder1.show();
    }
//
//    @Override
//    public void onClick(DialogInterface dialog, int which) {
//        if (which == Dialog.BUTTON_POSITIVE) {
//            CommentAndRateAsync commentAndRateAsync = new CommentAndRateAsync(etComment.getText().toString(), Integer.valueOf(String.valueOf(ratbProduct.getRating())),
//                    Integer.valueOf(String.valueOf(ratbMsg.getRating())));
//            commentAndRateAsync.execute();
//        }
//
//    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        if (ratingBar.getId() == R.id.ratb_msg) {
            ratbMsg.setRating(rating);
        } else {
            ratbProduct.setRating(rating);
        }
    }

    @Override
    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
        if (which == DialogAction.POSITIVE) {
            CommentAndRateAsync commentAndRateAsync = new CommentAndRateAsync(etComment.getText().toString(), Integer.valueOf((int) ratbProduct.getRating()),
                    Integer.valueOf((int) ratbMsg.getRating()));
            commentAndRateAsync.execute();
        }
    }

    private class CommentAndRateAsync extends AsyncTask<Void, Void, Void> {
        private String comment;
        private Integer rateProduct;
        private Integer rateMessenger;
        boolean commented;

        @Override
        protected void onPreExecute() {
            commented = false;
            super.onPreExecute();
        }

        public CommentAndRateAsync(String comment, Integer rateProduct, Integer rateMessenger) {
            this.comment = comment;
            this.rateProduct = rateProduct;
            this.rateMessenger = rateMessenger;
        }

        @Override
        protected Void doInBackground(Void... params) {
            String userId = userManager.user().getId();
            try {
                if (!TextUtils.isEmpty(comment)) {

                    repository.commentProduct(comment, userId, requestId, productId);
                }
                repository.rateMessenger(productId, userId, rateMessenger, requestId);
                repository.rateProduct(productId, userId, requestId, rateProduct);
            } catch (IOException e) {
                commented = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (!commented)
                Toast.makeText(etComment.getContext(), R.string.comment_error_msg, Toast.LENGTH_SHORT).show();
            super.onPostExecute(aVoid);
        }
    }
}
