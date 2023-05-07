package com.example.test.Presentation.Dashbroad.ListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.test.Model.MonAn_VietnameseDelicacies;
import com.example.test.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewApdapterSeach extends BaseAdapter {

    Context _mContext;
    LayoutInflater _inflater;
    private List<MonAn_VietnameseDelicacies> _listMonAn = null;
    private ArrayList<MonAn_VietnameseDelicacies> _arraylistMonAn;

    public ListViewApdapterSeach(Context context,List<MonAn_VietnameseDelicacies>listMonAn){
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

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        _listMonAn.clear();
        if (charText.length() == 0) {
            _listMonAn.addAll(_arraylistMonAn);
        } else {
            for (MonAn_VietnameseDelicacies MonAn : _arraylistMonAn) {
                if (MonAn.get_MonAn().toLowerCase(Locale.getDefault()).contains(charText)) {
                    _listMonAn.add(MonAn);
                }
            }
        }
        notifyDataSetChanged();
    }

}
