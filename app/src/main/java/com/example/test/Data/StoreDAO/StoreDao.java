package com.example.test.Data.StoreDAO;

import com.example.test.Model.Store;
import com.google.android.gms.tasks.Task;

import java.util.List;

public interface StoreDao {
    public void addStore(Store store);
    public void updateStore(Store store,String DocumentID);
    public void deleteStore(Store store);

    Task<Store> getStore(String DocumentId);

    public Task<List<String>> getDocumentIds();

}
