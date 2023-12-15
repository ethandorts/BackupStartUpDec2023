package com.example.back4appstartup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultHolder> {
    Context context;
    java.util.List<ParseObject> list;

    public ResultAdapter(Context context, List<ParseObject> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.result_cell, parent, false);
        return new ResultHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultHolder holder, int position) {
        ParseObject object = list.get(position);
        if(object.getString("title")!= null) {
            holder.name.setText(object.getString("title"));
        } else if(object.getString("name") != null) {
            holder.name.setText(object.getString("name"));
        } else {
            holder.name.setText("null");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clearList() {
        list = new ArrayList<>();
        notifyDataSetChanged();
    }
}
