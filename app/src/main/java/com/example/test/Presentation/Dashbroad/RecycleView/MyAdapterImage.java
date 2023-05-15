package com.example.test.Presentation.Dashbroad.RecycleView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.test.Model.ShopView;
import com.example.test.R;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MyAdapterImage extends RecyclerView.Adapter<MyAdapterImage.MyViewHolder> {
    private List<ShopView> shopViewList;
    private OnItemClickListener listener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public MyAdapterImage(List<ShopView> shopViewList, OnItemClickListener listener) {
        this.shopViewList = shopViewList;
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new MyViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        ShopView shopView = shopViewList.get(position);

        holder.nameTextView.setText(shopView.getNameFood());
        holder.priceTextView.setText(String.valueOf(shopView.getPrice()));

        // Use an image loading library like Glide or Picasso to load the image into the ImageView.
        // Here, we're just setting a placeholder image.
        Glide.with(holder.itemView.getContext())
                .load(shopView.getUlrImage())
                .placeholder(R.drawable.backgroundmonan) // set placeholder image
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return shopViewList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView nameTextView;
        public TextView priceTextView;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            imageView = itemView.findViewById(R.id.caiquan);
            nameTextView = itemView.findViewById(R.id.title_name);
            priceTextView = itemView.findViewById(R.id.title_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
}