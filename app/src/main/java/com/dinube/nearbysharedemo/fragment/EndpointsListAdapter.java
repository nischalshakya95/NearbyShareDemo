package com.dinube.nearbysharedemo.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dinube.nearbysharedemo.R;

import java.util.List;

public class EndpointsListAdapter extends RecyclerView.Adapter<EndpointsListAdapter.RecyclerViewHolder> {

    private List<String> endpoints;

    private View.OnClickListener onClickListener;

    public EndpointsListAdapter(Context context, List<String> endpoints) {
        this.endpoints = endpoints;
    }

    public void onSetClickListener(View.OnClickListener onClickListener){
        this.onClickListener = onClickListener;
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

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.endpointsName);
            itemView.setTag(this);
            itemView.setOnClickListener(onClickListener);
        }
    }

}
