package com.example.test.Presentation.Dashbroad.SeachView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.example.test.Presentation.Dashbroad.ListView.ListViewApdapterSearch_Store;
import com.example.test.Presentation.Dashbroad.ListView.ListViewApdapterSearch_VietnameseDelicacies;

public class CombinedAdapter extends BaseAdapter {
    private ListViewApdapterSearch_Store adapterStore;
    private ListViewApdapterSearch_VietnameseDelicacies adapterVietnameseDelicacies;

    public CombinedAdapter(ListViewApdapterSearch_Store adapterStore, ListViewApdapterSearch_VietnameseDelicacies adapterVietnameseDelicacies) {
        this.adapterStore = adapterStore;
        this.adapterVietnameseDelicacies = adapterVietnameseDelicacies;
    }

    @Override
    public int getCount() {
        // Số lượng item trong adapter tổng hợp là tổng của số lượng item trong hai adapter gốc
        return adapterStore.getCount() + adapterVietnameseDelicacies.getCount();
    }

    @Override
    public Object getItem(int position) {
        // Trả về item tại vị trí position
        // Kiểm tra vị trí position để xác định item thuộc adapterStore hay adapterVietnameseDelicacies
        if (position < adapterStore.getCount()) {
            return adapterStore.getItem(position);
        } else {
            return adapterVietnameseDelicacies.getItem(position - adapterStore.getCount());
        }
    }

    @Override
    public long getItemId(int position) {
        // Trả về ID của item tại vị trí position
        // Kiểm tra vị trí position để xác định item thuộc adapterStore hay adapterVietnameseDelicacies
        if (position < adapterStore.getCount()) {
            return adapterStore.getItemId(position);
        } else {
            return adapterVietnameseDelicacies.getItemId(position - adapterStore.getCount());
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Lấy view của item tại vị trí position
        // Kiểm tra vị trí position để xác định item thuộc adapterStore hay adapterVietnameseDelicacies
        if (position < adapterStore.getCount()) {
            return adapterStore.getView(position, convertView, parent);
        } else {
            return adapterVietnameseDelicacies.getView(position - adapterStore.getCount(), convertView, parent);
        }
    }

    @Override
    public int getViewTypeCount() {
        // Trả về số lượng loại view hiển thị trong adapter tổng hợp
        return adapterStore.getViewTypeCount() + adapterVietnameseDelicacies.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        // Trả về loại view của item tại vị trí position
        // Kiểm tra vị trí position để xác định item thuộc adapterStore hay adapterVietnameseDelicacies
        if (position < adapterStore.getCount()) {
            return adapterStore.getItemViewType(position);
        } else {
            return adapterVietnameseDelicacies.getItemViewType(position - adapterStore.getCount()) + adapterStore.getViewTypeCount();
        }
    }
}