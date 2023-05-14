package com.example.test.Presentation.Store.ListView;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;

import java.util.List;

public class ButtonListAdapterActivity extends RecyclerView.Adapter<ButtonListAdapterActivity.ButtonViewHolder> {

    private List<String> mButtonNames;
    private OnButtonClickListener mListener;

    public ButtonListAdapterActivity(List<String> buttonNames, OnButtonClickListener listener) {
        mButtonNames = buttonNames;
        mListener = listener;
    }

    @NonNull
    @Override
    public ButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_button_list_adapter, parent, false);
        return new ButtonViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonViewHolder holder, int position) {
        String buttonName = mButtonNames.get(position);
        holder.mButton.setText(buttonName);
    }

    @Override
    public int getItemCount() {
        return mButtonNames.size();
    }

    public void removeItem(int position) {
        mButtonNames.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mButtonNames.size());
    }

    public interface OnButtonClickListener {
        void onButtonClicked(int position);
    }

    public static class ButtonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public Button mButton;
        private OnButtonClickListener mListener;

        public ButtonViewHolder(@NonNull View itemView, OnButtonClickListener listener) {
            super(itemView);
            mListener = listener;
            mButton = itemView.findViewById(R.id.button);
            mButton.setBackgroundColor(Color.BLACK);
            mButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onButtonClicked(getAdapterPosition());
        }
    }
}