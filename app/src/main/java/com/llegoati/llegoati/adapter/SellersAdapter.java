package com.llegoati.llegoati.adapter;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.llegoati.llegoati.R;
import com.llegoati.llegoati.activities.SellerProfileActivity;
import com.llegoati.llegoati.adapter.viewholders.SellerViewHolder;
import com.llegoati.llegoati.infrastructure.concrete.RemoteRepository;
import com.llegoati.llegoati.models.PartialSeller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richard on 5/14/2017.
 */

public class SellersAdapter extends RecyclerView.Adapter<SellerViewHolder> {

    private List<PartialSeller> mList;
    private List<PartialSeller> mOriginalList;

    public SellersAdapter(List<PartialSeller> mList) {
        this.mList = mList; this.mOriginalList = mList;
    }

    @Override
    public SellerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_model_seller, parent, false);

        return new SellerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SellerViewHolder holder, final int position) {
        holder.mNameTextView.setText(mList.get(position).getName());


        if (mList.get(position).getImage()==null) {
            String mIcon = String.valueOf(mList.get(position).getName().charAt(0));
            holder.mIconTextView.setText(mIcon.toUpperCase());
        }else {
            holder.mIconTextView.setVisibility(View.GONE);
            holder.mIconImage.setVisibility(View.VISIBLE);
            holder.mIconImage.setImageBitmap(RemoteRepository.getBitmap(mList.get(position).getImage()));
        }



        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent mIntent = new Intent(holder.mView.getContext(), SellerProfileActivity.class);
                mIntent.putExtra("mId", mList.get(position).getId());
                holder.mView.getContext().startActivity(mIntent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void add(List<PartialSeller> mSell) {
        mList.addAll(mSell);
        notifyDataSetChanged();
    }


    mSearch mSearchWork ;

    public void search(String mText) {

        if (mSearchWork!=null)
            mSearchWork.cancel(true);

        mSearchWork = new mSearch(mText);
        mSearchWork.execute();
    }

    private class mSearch extends AsyncTask{

        String searchText;
        final List<PartialSeller> mtemp;
        public mSearch(String searchText) {
            this.searchText = searchText;
            mtemp =  new ArrayList<>();
        }


        @Override
        protected void onPostExecute(Object o) {
            if (mtemp.size()>0){
                mList = mtemp;
                notifyDataSetChanged();
            }
            super.onPostExecute(o);
        }

        @Override
        protected Object doInBackground(Object[] params) {


            for (PartialSeller prt : mOriginalList){
                if (prt.getName().toLowerCase().contains(searchText.toLowerCase())){
                    mtemp.add(prt);
                }
            }

            return null;
        }
    }

}
