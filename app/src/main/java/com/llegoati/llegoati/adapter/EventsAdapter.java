package com.llegoati.llegoati.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.llegoati.llegoati.R;
import com.llegoati.llegoati.adapter.viewholders.EventViewHolder;
import com.llegoati.llegoati.infrastructure.concrete.utils.GenericUtils;
import com.llegoati.llegoati.models.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Yansel on 7/21/2017.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventViewHolder> {
    List<Event> events;
    int count;

    public EventsAdapter(int count) {
        events = new ArrayList<>();
        this.count = count;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item, parent, false);

        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        Event item = events.get(position);
        holder.tvTitle.setText(item.getTitle());
        Locale locale = new Locale("es", "ES");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDateConverted = null;
        Date endDateConverted = null;
        if (count > 0) {
            holder.btnSeeMore.setVisibility(View.VISIBLE);

        } else {
            holder.btnSeeMore.setVisibility(View.GONE);
            holder.tvTitle.setOnClickListener(null);
        }
        try {
            startDateConverted = simpleDateFormat.parse(item.getEventStart().split("T")[0]);
            endDateConverted = simpleDateFormat.parse(item.getEventEnd().split("T")[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat shortformat = new SimpleDateFormat("EEEE, MMMM d, yyyy", locale);
        shortformat.format(startDateConverted);

        holder.tvRange.setText(String.format(locale, "Desde %s hasta %s", shortformat.format(startDateConverted), shortformat.format(endDateConverted)));
        holder.tvContent.setText((count > 0) ? GenericUtils.brief(item.getDescription(), 200) : item.getDescription());
        ImagesAdapter imagesAdapter = new ImagesAdapter(item.getImages());
        holder.setImagesAdapter(imagesAdapter);

    }

    public void clear() {
        events.clear();
    }

    public void add(Event event) {
        events.add(event);
    }

    @Override
    public int getItemCount() {
        return count > 0 && count < events.size() ? count : events.size();
    }

}
