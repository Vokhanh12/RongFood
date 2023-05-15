package com.example.test.Data.StoreDAO;

import com.example.test.Model.Store;
import com.example.test.Model.VietnameseDelicacies;
import com.google.android.gms.tasks.Task;

import java.util.LinkedList;
import java.util.List;

public interface StoreDao {
    public void addStore(Store store, LinkedList<VietnameseDelicacies> llVietnameseDelicacies);
    public void updateStore(Store store,VietnameseDelicacies vietnameseDelicacies,String DocumentID);
    public void deleteStore(Store store);

    public void getDocumentIdByTenCHMaCH(String MaCH,String TenCH,DocumentIdCallback callback);

    public Task<Store> getStore(String DocumentId);

    public Task<List<String>> getDocumentIds();

}
