package com.example.mindabloom.marvelapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mindabloom.marvelapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahmed Abdelaziz on 11/10/2016.
 */

public class MarvelHistoryAdapter extends RecyclerView.Adapter<MarvelHistoryAdapter.GroupViewHolder> {

    /*
    * A click listener interface to handle the item clickability in the acticity itself to use the presenter directly
    */
    public interface OnItemClickListener {
        void onItemClick(String name);
    }

    private final OnItemClickListener listener;
    List<String> names = new ArrayList<String>();
    LayoutInflater layoutInflater;
    Context context;

    public MarvelHistoryAdapter(Context context, List<String> names, OnItemClickListener listener) {
        this.context = context;
        this.names = names;
        this.listener = listener;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View row = layoutInflater.inflate(R.layout.history_single_item, parent, false);
        GroupViewHolder holder = new GroupViewHolder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(final GroupViewHolder holder, final int position) {

        final String name = names.get(position);

        holder.name.setText(name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(name);
            }
        });
    }

    public String getItem(int pos) {
        return names.get(pos);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public GroupViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
        }
    }
}

