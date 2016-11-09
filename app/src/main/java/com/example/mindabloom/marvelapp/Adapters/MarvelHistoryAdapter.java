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
 * Created by Ahmed Abdelaziz on 11/9/2016.
 */

public class MarvelHistoryAdapter extends RecyclerView.Adapter<MarvelHistoryAdapter.GroupViewHolder> {

    List<String> names = new ArrayList<String>();
    LayoutInflater layoutInflater;
    Context context;

    public MarvelHistoryAdapter(Context context, List<String> names) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.names = names;
        //Toast.makeText(context, images.size()+"", Toast.LENGTH_SHORT).show();
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
            }
        });
    }

    public String getItem(int pos){
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

