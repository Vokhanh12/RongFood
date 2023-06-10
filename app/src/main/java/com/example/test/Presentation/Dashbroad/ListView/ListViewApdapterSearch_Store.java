package com.example.test.Presentation.Dashbroad.ListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.test.Model.Model_Dashbroad.useSearchView.MenuShow_Store;
import com.example.test.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewApdapterSearch_Store extends BaseAdapter {

    Context _mContext;
    LayoutInflater _inflater;

    private List<MenuShow_Store> _listMenu_Store =null;

    private ArrayList<MenuShow_Store> _arraylistMenu_Store;



    public ListViewApdapterSearch_Store(Context context, List<MenuShow_Store> listMenu_Store) {
        this._mContext = context;
        this._listMenu_Store = listMenu_Store;
        this._inflater = LayoutInflater.from(_mContext);
        this._arraylistMenu_Store = new ArrayList<>(_listMenu_Store);
    }


    public class ViewHolder {
        TextView name;
    }


    @Override
    public int getCount() {
        return _listMenu_Store.size();
    }

    @Override
    public MenuShow_Store getItem(int position) {
        return _listMenu_Store.get(position);
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
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        MenuShow_Store menuShowStore = _listMenu_Store.get(position);
        String tenMonAn = menuShowStore.get_tenMonAn();
        String tenCuaHang = menuShowStore.get_tenCuaHang();
        String displayText = "";
        if (tenMonAn != null && !tenMonAn.isEmpty()) {
            displayText += tenMonAn;
        }
        if (tenCuaHang != null && !tenCuaHang.isEmpty()) {
            if (!displayText.isEmpty()) {
                displayText += " - ";
            }
            displayText += tenCuaHang;
        }
        holder.name.setText(displayText);
        return view;
    }

    //...

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        _listMenu_Store.clear();
        if (charText.length() == 0) {
            _listMenu_Store.addAll(_arraylistMenu_Store);
        } else {
            for (MenuShow_Store menuShowStore : _arraylistMenu_Store) {
                String tenMonAn = menuShowStore.get_tenMonAn();
                String tenCuaHang = menuShowStore.get_tenCuaHang();
                if (tenMonAn != null && tenMonAn.toLowerCase(Locale.getDefault()).contains(charText)) {
                    _listMenu_Store.add(menuShowStore);
                } else if (tenCuaHang != null && tenCuaHang.toLowerCase(Locale.getDefault()).contains(charText)) {
                    _listMenu_Store.add(menuShowStore);
                }
            }
        }
        notifyDataSetChanged();
    }
}