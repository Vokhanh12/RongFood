package com.example.test.Data.StoreDAO;

import com.example.test.Model.Store;

public interface StoreDao {
    public void addStore(Store store);
    public void updateStore(Store store,String DocumentID);
    public void deleteStore(Store store);

}
