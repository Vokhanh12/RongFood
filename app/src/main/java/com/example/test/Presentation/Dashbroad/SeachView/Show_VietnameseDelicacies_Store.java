package com.example.test.Presentation.Dashbroad.SeachView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ListView;

import androidx.appcompat.widget.SearchView;

import com.example.test.Data.StoreDAO.StoreDAOimpl_Firestore;
import com.example.test.Data.Vietnamese_Delicacies.VietnameseDelicaciesimpl_Firestore;
import com.example.test.Model.Model_Dashbroad.useSearchView.MenuShow_Store;
import com.example.test.Model.Model_Dashbroad.useSearchView.MenuShow_VietnameseDelicacies;
import com.example.test.Model.Store;
import com.example.test.Model.VietnameseDelicacies;
import com.example.test.Presentation.Dashbroad.ListView.ListViewApdapterSearch_Store;
import com.example.test.Presentation.Dashbroad.ListView.ListViewApdapterSearch_VietnameseDelicacies;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Show_VietnameseDelicacies_Store {

    String Tag = "SearchViewMonAn_VietnameseDelicacies_Store";
    Context _mContext;
    LayoutInflater _inflater;

    ListViewApdapterSearch_VietnameseDelicacies adapterVietnameseDelicacies;
    ListViewApdapterSearch_Store adapterStore;
    ListView _listViewSearch;
    SearchView _editsearch;

    private ArrayList<MenuShow_VietnameseDelicacies> _arrlistMonAn = new ArrayList<>();
    private ArrayList<MenuShow_Store> _arrlist_MonAn_TenCuaHang = new ArrayList<>();

    public Show_VietnameseDelicacies_Store(Context context, ListView listViewSearch, SearchView editSearch) {
        this._mContext = context;
        this._listViewSearch = listViewSearch;
        this._editsearch = editSearch;

        // Đưa danh sách VietnameseDelicacies vào SearchView
        addItemsToSearchView();
    }

    private void addItemsToSearchView() {
        // Khởi tạo StoreDAOimpl_Firestore để lấy tất cả DocumentIDs và Table of Stores
        StoreDAOimpl_Firestore storeDAOimplFirestore = new StoreDAOimpl_Firestore(_mContext);

        // Khởi tạo VietnameseDelicaciesimpl_Firestore để lấy tất cả DocumentIDs và Table of VietnameseDelicacies
        VietnameseDelicaciesimpl_Firestore vietnameseDelicaciesimplFirestore = new VietnameseDelicaciesimpl_Firestore(_mContext);

        // Kết hợp hai tác vụ và đợi cả hai tác vụ hoàn thành
        Task<List<String>> storeDocumentIdsTask = storeDAOimplFirestore.getDocumentIds();
        Task<List<String>> vietnameseDelicaciesDocumentIdsTask = vietnameseDelicaciesimplFirestore.getDocumentIds();

        Tasks.whenAllComplete(storeDocumentIdsTask, vietnameseDelicaciesDocumentIdsTask)
                .addOnSuccessListener(new OnSuccessListener<List<Task<?>>>() {
                    @Override
                    public void onSuccess(List<Task<?>> tasks) {
                        // Lấy kết quả của tác vụ lấy DocumentIDs của Store và VietnameseDelicacies
                        List<String> listStoreDocumentIds = (List<String>) storeDocumentIdsTask.getResult();
                        List<String> listVietnameseDelicaciesDocumentIds = (List<String>) vietnameseDelicaciesDocumentIdsTask.getResult();

                        // Thực hiện lấy dữ liệu từ DocumentIDs của Store
                        List<Task<Store>> storeTasks = new ArrayList<>();
                        for (String storeDocumentId : listStoreDocumentIds) {
                            Task<Store> storeTask = storeDAOimplFirestore.getStore(storeDocumentId);
                            storeTasks.add(storeTask);
                        }

                        // Thực hiện lấy dữ liệu từ DocumentIDs của VietnameseDelicacies
                        List<Task<VietnameseDelicacies>> vietnameseDelicaciesTasks = new ArrayList<>();
                        for (String vietnameseDelicaciesDocumentId : listVietnameseDelicaciesDocumentIds) {
                            Task<VietnameseDelicacies> vietnameseDelicaciesTask = vietnameseDelicaciesimplFirestore.getVietnameseDelicacies(vietnameseDelicaciesDocumentId);
                            vietnameseDelicaciesTasks.add(vietnameseDelicaciesTask);
                        }

                        List<Task<?>> allTasks = new ArrayList<>();
                        allTasks.addAll(storeTasks);
                        allTasks.addAll(vietnameseDelicaciesTasks);

                        Tasks.whenAllComplete(allTasks)
                                .addOnSuccessListener(new OnSuccessListener<List<Task<?>>>() {
                                    @Override
                                    public void onSuccess(List<Task<?>> tasks) {
                                        for (Task<?> task : tasks) {
                                            if (task.isSuccessful()) {
                                                if (task.getResult() instanceof VietnameseDelicacies) {
                                                    VietnameseDelicacies vietnameseDelicacies = (VietnameseDelicacies) task.getResult();

                                                    // Thêm Món ăn vào ArrayList
                                                    if (containsSearchText(vietnameseDelicacies.get_TenMon())) {
                                                        _arrlistMonAn.add(new MenuShow_VietnameseDelicacies(vietnameseDelicacies.get_TenMon()));
                                                    }
                                                }

                                                if (task.getResult() instanceof Store) {
                                                    Store store = (Store) task.getResult();

                                                    // Thêm Tên Cửa hàng vào ArrayList
                                                    if (containsSearchText(store.get_TenCH())) {
                                                        _arrlist_MonAn_TenCuaHang.add(new MenuShow_Store(store.get_TenCH(), "NameStore"));
                                                    }

                                                    LinkedList<VietnameseDelicacies> llMenu_Stores = store.get_llMenu();

                                                    // Thêm Món ăn vào ArrayList
                                                    for (int i = 0; i < llMenu_Stores.size(); i++) {
                                                        VietnameseDelicacies vietnameseDelicacies = llMenu_Stores.get(i);
                                                        if (containsSearchText(vietnameseDelicacies.get_TenMon())) {
                                                            _arrlist_MonAn_TenCuaHang.add(new MenuShow_Store(vietnameseDelicacies.get_TenMon(), "NameFood"));
                                                        }
                                                    }
                                                }
                                            } else {
                                                Log.e(Tag, "Error retrieving data: " + task.getException());
                                            }
                                        }

                                        // Đưa ArrayList vào Adapter
                                        if (_arrlistMonAn.size() > 0 && _arrlist_MonAn_TenCuaHang.size() > 0) {
                                            adapterVietnameseDelicacies = new ListViewApdapterSearch_VietnameseDelicacies(_mContext, _arrlistMonAn);
                                            adapterStore = new ListViewApdapterSearch_Store(_mContext, _arrlist_MonAn_TenCuaHang);
                                            CombinedAdapter combinedAdapter = new CombinedAdapter(adapterStore, adapterVietnameseDelicacies);
                                            _listViewSearch.setAdapter(combinedAdapter);
                                        }
                                    }
                                });
                    }
                });
    }

    private boolean containsSearchText(String text) {
        String searchText = _editsearch.getQuery().toString().toLowerCase();
        return text.toLowerCase().contains(searchText);
    }
}
