package com.dinube.nearbysharedemo.fragment;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dinube.nearbysharedemo.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public TextView textView;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.endpointsName);
    }

    public TextView getView(){
        return textView;
    }
}
