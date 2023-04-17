package com.example.test.Presentation.Dashbroad_Map;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.test.R;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MyAdapterImage extends RecyclerView.Adapter<MyAdapterImage.MyViewHolder> {
    private List<String> imageUrlList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public MyAdapterImage(List<String> imageUrlList, OnItemClickListener listener) {
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
        String imageUrl = imageUrlList.get(position);
        // Use an image loading library like Glide or Picasso to load the image into the ImageView.
        // Here, we're just setting a placeholder image.
        holder.imageView.setImageResource(R.drawable.backgroundmonan);
    }

    @Override
    public int getItemCount() {
        return imageUrlList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            imageView = itemView.findViewById(R.id.caiquan);

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

