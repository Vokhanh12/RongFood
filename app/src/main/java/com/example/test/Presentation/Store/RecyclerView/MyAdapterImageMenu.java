package com.example.test.Presentation.Store.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.test.Model.Menu;
import com.example.test.R;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MyAdapterImageMenu extends RecyclerView.Adapter<MyAdapterImageMenu.MyViewHolder> {
    private List<Menu> imageUrlList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public MyAdapterImageMenu(List<Menu> imageUrlList, OnItemClickListener listener) {
        this.imageUrlList = imageUrlList;
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
        Menu imageUrl = imageUrlList.get(position);
        // Use an image loading library like Glide or Picasso to load the image into the ImageView.
        // Here, we're just setting a placeholder image.
        Glide.with(holder.itemView.getContext())
                .load(imageUrl.getImage())
                .placeholder(R.drawable.backgroundmonan) // set placeholder image
                .into(holder.imageView);
        holder.name.setText(imageUrl.getName());
        holder.price.setText(String.format("$" + imageUrl.getPrice()));
        if(imageUrl.getView() == -1)
            holder.textview.setText("khong co viewer");
        holder.textview.setText(String.format(imageUrl.getView()+"k"));

    }

    @Override
    public int getItemCount() {
        return imageUrlList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView name;
        public TextView price;
        public TextView textview;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            imageView = itemView.findViewById(R.id.caiquan);
            name = itemView.findViewById(R.id.item_name);
            price = itemView.findViewById(R.id.item_price);
            textview = itemView.findViewById(R.id.View);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });

        }
    }
}
