package com.llegoati.llegoati.smsmodulo.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.llegoati.llegoati.R;
import com.llegoati.llegoati.smsmodulo.Infraestructure.concrete.Repository;
import com.llegoati.llegoati.smsmodulo.Models.SmsMessage;

import com.llegoati.llegoati.smsmodulo.View.SmsHistoryActivity;

import java.util.List;

import static com.llegoati.llegoati.smsmodulo.View.SmsMainActivity.mAdaptTrash;

/**
 * Created by Richard on 3/19/2017.
 */

public class AdapterSmsMain extends RecyclerView.Adapter<AdapterSmsMain.ViewHolder> {


    List<SmsMessage> mItems;

    public AdapterSmsMain(List<SmsMessage> messageList) {
        this.mItems = messageList;
    }


    public void clear() {
        notifyItemRangeRemoved(0,mItems.size());
        mItems.clear();
    }

    public void addAll(List<SmsMessage> lista) {
        notifyItemRangeRemoved(0,mItems.size());
        mItems.clear();

        for (SmsMessage mE: lista
                ) {
            addItem(mE);
        }

    }

    public void addItem(SmsMessage mEvent) {
        mItems.add(mEvent);
        notifyItemInserted(mItems.indexOf(mEvent));
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        final SmsMessage mTempSms = mItems.get(position);
        String first = String.valueOf(mTempSms.getmBody().toCharArray()[0]);
        holder.mInitial.setText(first.toUpperCase());

        if (mTempSms.getmBody().length()>25)
            holder.mTextInitial.setText(mTempSms.getmBody().substring(0,25) + "...");
        else
            holder.mTextInitial.setText(mTempSms.getmBody().substring(0,mTempSms.getmBody().length()));

        if (mTempSms.isNew()){
            holder.mInitial.setBackgroundResource(R.drawable.sms_letter_new);
        }else if (mTempSms.isResponce()) {
            holder.mInitial.setBackgroundResource(R.drawable.sms_letter_reponce);
        }else if (mTempSms.isDelete()) {
            holder.mInitial.setBackgroundResource(R.drawable.sms_letter_deelte);
        }



        holder.mMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.popup.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener{

        final TextView mInitial;
        final TextView mTextInitial;
        final ImageView mMenu;
        final Context mContext;
        final PopupMenu popup;
        Repository mRepo;


        public ViewHolder(final View itemView) {
            super(itemView);

            mContext = itemView.getContext();

            mInitial = (TextView) itemView.findViewById(R.id.sms_initial);
            mTextInitial = (TextView) itemView.findViewById(R.id.sms_initial_text);
            mMenu = (ImageView) itemView.findViewById(R.id.btn_popu);

            popup = new PopupMenu(mContext,mMenu);
            popup.inflate(R.menu.list_message);
            popup.setOnMenuItemClickListener(this);

            mRepo = new Repository(mContext);

            itemView.findViewById(R.id.adapter_action).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mIntent = new Intent(itemView.getContext(),SmsHistoryActivity.class);
                    mIntent.putExtra("sms_id",mItems.get(getPosition()).getmId());
                    itemView.getContext().startActivity(mIntent);
                }
            });

            itemView.findViewById(R.id.adapter_action).setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    popup.show();
                    return true;
                }
            });

        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {

            int id = item.getItemId();
            final int position = getAdapterPosition();

            if (id == R.id.action_ver) {
                Intent mIntent = new Intent(itemView.getContext(),SmsHistoryActivity.class);
                mIntent.putExtra("sms_id",mItems.get(getPosition()).getmId());
                itemView.getContext().startActivity(mIntent);
            } else if (id == R.id.action_delete) {

                if(mItems.get(getPosition()).isDelete()){
                    Snackbar.make(mMenu, R.string.elminar_mensaje,Snackbar.LENGTH_SHORT).setAction(
                            "SI", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mRepo.totalDelete(mItems.get(getPosition()).getmId());
                                    mItems.remove(getPosition());
                                    notifyItemRemoved(getPosition());
                                    List<SmsMessage> mTrash = mRepo.getTrash();
                                    mAdaptTrash.addAll(mTrash);
                                }
                            }
                    ).show();
                }else {
                    Snackbar.make(mMenu, R.string.eliminar_sms,Snackbar.LENGTH_SHORT).setAction(
                            "SI", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                    mRepo.deleteSms(mItems.get(getPosition()).getmId());
                    mItems.remove(getPosition());
                    notifyItemRemoved(getPosition());
                    List<SmsMessage> mTrash = mRepo.getTrash();
                    mAdaptTrash.addAll(mTrash);     }
                            }
                    ).show();
                }
            }

            return false;
        }
    }
}
