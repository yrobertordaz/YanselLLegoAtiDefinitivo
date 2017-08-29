package com.llegoati.llegoati.explorer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.llegoati.llegoati.R;

import java.io.File;

/**
 * Created by Richard on 8/15/2017.
 */

public class AdapterDatabaseList   extends  RecyclerView.Adapter<AdapterDatabaseList.FileViewHolder>{

    LLegoDataBaseFactory[] mElements;

    public AdapterDatabaseList(LLegoDataBaseFactory[] mElements) {
        this.mElements = mElements;
    }

    @Override
    public FileViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_list_db, viewGroup, false);
        return new AdapterDatabaseList.FileViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FileViewHolder holder, int position) {

        holder.mName.setText(mElements[position].getmName());
        holder.mPosition.setText(String.valueOf(position+1)+"-");

        File mF = new File(mElements[position].getmUbication() + mElements[position].getmName());

        if (!mF.exists())
            holder.mState.setVisibility(View.GONE);


    }

    @Override
    public int getItemCount() {
        return mElements.length;
    }

    public class FileViewHolder extends RecyclerView.ViewHolder {


        TextView mPosition;
        TextView mName;

        TextView mState;

        public FileViewHolder(View itemView) {
            super(itemView);

            mName = (TextView) itemView.findViewById(R.id.db_name);
            mState = (TextView) itemView.findViewById(R.id.db_state);
            mPosition = (TextView) itemView.findViewById(R.id.db_position);



        }
    }
}
