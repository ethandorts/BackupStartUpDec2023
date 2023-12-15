package com.example.back4appstartup;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ResultHolder extends RecyclerView.ViewHolder {
    TextView name;
    public ResultHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.textViewName);
    }
}
