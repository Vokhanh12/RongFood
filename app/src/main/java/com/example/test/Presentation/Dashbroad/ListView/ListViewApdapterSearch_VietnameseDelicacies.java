package com.example.test.Presentation.Dashbroad.ListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.test.Model.SearchViewModel_Dashbroad.MenuShow_Store;
import com.example.test.Model.SearchViewModel_Dashbroad.MenuShow_VietnameseDelicacies;
import com.example.test.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewApdapterSearch_VietnameseDelicacies extends BaseAdapter {

    Context _mContext;
    LayoutInflater _inflater;
    private List<MenuShow_VietnameseDelicacies> _listMenu_VietnameseDelicacies = null;

    private ArrayList<MenuShow_VietnameseDelicacies> _arraylistMenu_VietnameseDelicacies;


    public ListViewApdapterSearch_VietnameseDelicacies(Context context, List<MenuShow_VietnameseDelicacies>listMenu_VietnameseDelicacies){
        this._mContext = context;
        this._listMenu_VietnameseDelicacies = listMenu_VietnameseDelicacies;
        this._inflater = LayoutInflater.from(_mContext);
        this._arraylistMenu_VietnameseDelicacies = new ArrayList<MenuShow_VietnameseDelicacies>();
        this._arraylistMenu_VietnameseDelicacies.addAll(_listMenu_VietnameseDelicacies);
    }


    public class ViewHolder {
        TextView name;
    }


    @Override
    public int getCount() {
        return _listMenu_VietnameseDelicacies.size();
    }

    @Override
    public MenuShow_VietnameseDelicacies getItem(int position) {
        return _listMenu_VietnameseDelicacies.get(position);
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
        holder.name.setText(_listMenu_VietnameseDelicacies.get(position).get_MonAn());
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        _listMenu_VietnameseDelicacies.clear();
        if (charText.length() == 0) {
            _listMenu_VietnameseDelicacies.addAll(_arraylistMenu_VietnameseDelicacies);
        } else {
            for (MenuShow_VietnameseDelicacies MonAn : _arraylistMenu_VietnameseDelicacies) {
                if (MonAn.get_MonAn().toLowerCase(Locale.getDefault()).contains(charText)) {
                    _listMenu_VietnameseDelicacies.add(MonAn);
                }
            }
        }
        notifyDataSetChanged();
    }

}
