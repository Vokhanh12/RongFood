package com.example.test.Presentation.Store.ListView;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.example.test.Model.MonAn_VietnameseDelicacies;
import com.example.test.R;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class ListViewAdapterSearchMenu extends BaseAdapter implements Filterable {

    Context _mContext;
    LayoutInflater _inflater;
    private List<MonAn_VietnameseDelicacies> _listMonAn = null;
    private ArrayList<MonAn_VietnameseDelicacies> _arraylistMonAn;

    public ListViewAdapterSearchMenu(Context context,List<MonAn_VietnameseDelicacies>listMonAn){
        this._mContext = context;
        this._listMonAn =listMonAn;
        this._inflater = LayoutInflater.from(_mContext);
        this._arraylistMonAn = new ArrayList<MonAn_VietnameseDelicacies>();
        this._arraylistMonAn.addAll(_listMonAn);
    }




    public class ViewHolder {
        TextView name;
    }


    @Override
    public int getCount() {
        return _listMonAn.size();
    }

    @Override
    public MonAn_VietnameseDelicacies getItem(int position) {
        return _listMonAn.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = _inflater.inflate(R.layout.list_view_item, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(_listMonAn.get(position).get_MonAn());
        return view;
    }




    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchTerm = constraint.toString().toLowerCase();
                if (searchTerm.isEmpty()) {
                    return new FilterResults(); // trả về danh sách ban đầu nếu searchTerm trống
                } else {
                    List<MonAn_VietnameseDelicacies> resultList = new ArrayList<>();
                    // Sắp xếp danh sách món ăn để tìm kiếm nhị phân
                    Collections.sort(_arraylistMonAn, new Comparator<MonAn_VietnameseDelicacies>() {
                        @Override
                        public int compare(MonAn_VietnameseDelicacies o1, MonAn_VietnameseDelicacies o2) {
                            return o1.get_MonAn().toLowerCase().compareTo(o2.get_MonAn().toLowerCase());
                        }
                    });
                    int index = Collections.binarySearch(_arraylistMonAn, new MonAn_VietnameseDelicacies(searchTerm), new Comparator<MonAn_VietnameseDelicacies>() {
                        @Override
                        public int compare(MonAn_VietnameseDelicacies o1, MonAn_VietnameseDelicacies o2) {
                            return o1.get_MonAn().toLowerCase().compareTo(o2.get_MonAn().toLowerCase());
                        }
                    });

                    if (index < 0) { // Không tìm thấy chuỗi trùng khớp
                        index = -index - 1;
                    }
                    // Lưu điểm bắt đầu tìm kiếm
                    int startPos = index;

                    do {
                        MonAn_VietnameseDelicacies item = _arraylistMonAn.get(index++);
                        if (item.get_MonAn().toLowerCase().contains(searchTerm)) { // Nếu món ăn tìm thấy chứa searchTerm
                            resultList.add(item); // Thêm món ăn vào danh sách kết quả
                            if (index == _arraylistMonAn.size()) { // Chạy hết danh sách, thoát vòng lặp
                                break;
                            }
                        } else { // Nếu không thì thoát vòng lặp
                            break;
                        }
                    } while (true && resultList.size() <= (_arraylistMonAn.size() - startPos));

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                    return filterResults;
                }
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                _listMonAn = (List<MonAn_VietnameseDelicacies>) results.values;
                notifyDataSetChanged(); // Cập nhật ListView
            }
        };
    }


}