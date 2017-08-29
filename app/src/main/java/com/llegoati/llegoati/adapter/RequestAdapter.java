package com.llegoati.llegoati.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.llegoati.llegoati.R;
import com.llegoati.llegoati.adapter.viewholders.RequestHistoryViewHolder;
import com.llegoati.llegoati.models.Request;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Yansel on 5/19/2017.
 */

public class RequestAdapter extends RecyclerView.Adapter<RequestHistoryViewHolder> {
    ArrayList<Request> requestHistories;

    public RequestAdapter() {
        requestHistories = new ArrayList<>();
    }

    @Override
    public RequestHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_list_content, parent, false);
        return new RequestHistoryViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(RequestHistoryViewHolder holder, int position) {
        Request request = requestHistories.get(position);
        holder.setRequestId(request.getId());
        holder.tvIDValue.setText(request.getGeneratedOrderIdentifier());

        holder.tvCostValue.setText(String.format("%.2f cuc", request.getTotalPrice()));

        holder.tvStatusValue.setText(holder.tvStatusValue.getContext().getResources().getStringArray(R.array.request_status)[request.getState()]);
        if (availableForComment(position)) {
            holder.ivCommentNotification.setVisibility(View.VISIBLE);
        }


        try {
            holder.tvDateValue.setText(formatDate(request.getOrderDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private boolean availableForComment(int position) {
        Request request = requestHistories.get(position);

        if (request.getEnabled().trim().toLowerCase().equals("disponible")) {
            return true;
        }
        return false;
    }


    private String formatDate(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateConverted = simpleDateFormat.parse(date.split("T")[0]);
        SimpleDateFormat shortformat = new SimpleDateFormat("dd/MM/yyyy");
        return shortformat.format(dateConverted);
    }

    @Override
    public int getItemCount() {
        return requestHistories.size();
    }

    public void clear() {
        requestHistories.clear();
        this.notifyDataSetChanged();
    }

    public void add(Request request) {
        this.requestHistories.add(request);
    }
}
