package com.dinube.nearbysharedemo.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dinube.nearbysharedemo.R;

import java.util.List;

public class EndpointsListAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<String> endpoints;

    public EndpointsListAdapter(Context context, List<String> endpoints) {
        this.endpoints = endpoints;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_endpoints, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.textView.setText(endpoints.get(position));
    }

    @Override
    public int getItemCount() {
        return endpoints.size();
    }
}
