package com.dinube.supermarket.banks.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dinube.supermarket.R;
import com.dinube.supermarket.banks.model.Banks;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private List<Banks> banks;
    private Context context;

    private View.OnClickListener onClickListener;

    public CustomAdapter(Context context, List<Banks> dataList) {
        this.context = context;
        this.banks = dataList;
    }

    public void onSetClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public CustomAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.banks_row, parent, false);
        view.setOnClickListener(onClickListener);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.CustomViewHolder holder, int position) {
        holder.txtTitle.setText(banks.get(position).getFullname());
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(banks.get(position).getImage())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.coverImage);

    }

    @Override
    public int getItemCount() {
        return banks.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        public TextView txtTitle;

        public ImageView coverImage;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtTitle = mView.findViewById(R.id.bankName);
            coverImage = mView.findViewById(R.id.bankImage);

            mView.setTag(this);
            mView.setOnClickListener(onClickListener);
        }
    }

}