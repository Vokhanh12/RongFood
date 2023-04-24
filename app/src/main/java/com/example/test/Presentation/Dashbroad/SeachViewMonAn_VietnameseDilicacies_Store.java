package com.example.test.Presentation.Dashbroad;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ListView;

import android.widget.SearchView;
import com.example.test.Data.StoreDAO.StoreDAOimpl_Firestore;
import com.example.test.Data.Vietnamese_Delicacies.VietnameseDelicaciesimpl_Firestore;
import com.example.test.Model.MonAn_VietnameseDelicacies;
import com.example.test.Model.VietnameseDelicacies;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

public class SeachViewMonAn_VietnameseDilicacies_Store implements SearchView.OnQueryTextListener {

    Context _mContext;
    LayoutInflater _inflater;

    ListViewApdapterSeach adapterVietnamseDelicaccies;

    ListView list;

    private List<String> listDocumentIds = new ArrayList<>();

    private String[] MonAn;
    private List<MonAn_VietnameseDelicacies> _listMonAn = null;
    private ArrayList<MonAn_VietnameseDelicacies> _arraylistMonAn = new ArrayList<>();

    public SeachViewMonAn_VietnameseDilicacies_Store(Context context, ListView listViewSeach, SearchView editsearch){
        this._mContext = context;
        //Khởi tạo StoreDAOimpl_Firestore
        VietnameseDelicaciesimpl_Firestore vietnameseDelicaciesimplFirestore = new VietnameseDelicaciesimpl_Firestore(_mContext);
        vietnameseDelicaciesimplFirestore.getDocumentIds()
                .addOnSuccessListener(new OnSuccessListener<List<String>>() {
                    @Override
                    public void onSuccess(List<String> list_vietnameseDelicaciesDocumentids) {

                        int i = 0;

                        for (String itemDocumentIds : list_vietnameseDelicaciesDocumentids){


                            vietnameseDelicaciesimplFirestore.getVietnameseDelicacies(itemDocumentIds)
                                    .addOnSuccessListener(new OnSuccessListener<VietnameseDelicacies>() {
                                        @Override
                                        public void onSuccess(VietnameseDelicacies vietnameseDelicacies) {

                                            _arraylistMonAn.add(new MonAn_VietnameseDelicacies(vietnameseDelicacies.get_TenMon()));
                                            adapterVietnamseDelicaccies = new ListViewApdapterSeach(_mContext,_arraylistMonAn);
                                            listViewSeach.setAdapter(adapterVietnamseDelicaccies);
                                            editsearch.setOnQueryTextListener((SearchView.OnQueryTextListener) _mContext);




                                        }
                                    });


                        }



                    }
                });



    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapterVietnamseDelicaccies.filter(text);
        return false;
    }
}
