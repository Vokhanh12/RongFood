package com.example.test.Data.StoreDAO;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.test.Model.Location;
import com.example.test.Model.Store;
import com.example.test.Model.VietnameseDelicacies;
import com.google.android.gms.tasks.*;
import com.google.firebase.firestore.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class StoreDAOimpl_Firestore implements StoreDao {

    private static final String TAG = "StoreDAOimpl_Firestore";
    private Context _mContext;
    private FirebaseFirestore db;
    private CollectionReference storeCollection;

    public StoreDAOimpl_Firestore(Context context) {
        this._mContext = context;
        this.db = FirebaseFirestore.getInstance();
        this.storeCollection = db.collection("Stores");
    }


    @Override
    public void addStore(Store store, LinkedList<VietnameseDelicacies> vietnameseDelicacies) {
        storeCollection.whereEqualTo("_MaCH", store.get_MaCH()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.size() == 0) {
                            Map<String, Object> newStore = new HashMap<>();

                            newStore.put("_MaCH", store.get_MaCH());
                            newStore.put("_TenCH", store.getTenCH());
                            newStore.put("_NguoiSoHuu", store.get_NguoiSoHu());

                            newStore.put("_Location", store.get_location());

                            newStore.put("_Menu",store.get_Menu());


                            storeCollection.add(newStore).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "Store document added with ID: " + documentReference.getId());
                                    Toast.makeText(_mContext, "Thêm Store_MaCH:" + store.get_MaCH() + " Thành Công", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            Log.w(TAG, "MaCH " + store.get_MaCH() + " already exists");
                            Toast.makeText(_mContext, "Thêm Store_MaCH:" + store.get_MaCH() + " Thất bại", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }

    @Override
    public void updateStore(Store store,VietnameseDelicacies vietnameseDelicacies, String DocumentID) {
        DocumentReference storeRef = db.collection("Stores").document(DocumentID);

        // Update the "_MaCH" field of the document
        storeRef
                .update("_MaCH", store.get_MaCH())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                        Toast.makeText(_mContext, "Cập nhật Store-MaCH:" + store.get_MaCH() + "thành công", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                        Toast.makeText(_mContext, "Cập nhật Store-MaCH:" + store.get_MaCH() + "thất bại", Toast.LENGTH_SHORT).show();
                    }
                });

        // Update the "_NguoiSoHuu" field of the document
        storeRef
                .update("_NguoiSoHuu", store.get_NguoiSoHu())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                        Toast.makeText(_mContext, "Cập nhật Store-MaCH:" + store.get_NguoiSoHu() + "thành công", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                        Toast.makeText(_mContext, "Cập nhật Store-MaCH:" + store.get_MaCH() + "thất bại", Toast.LENGTH_SHORT).show();
                    }
                });


        // Update the "_Location" field of the document
        storeRef
                .update("_Location", store.get_NguoiSoHu())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                        Toast.makeText(_mContext, "Cập nhật Store-Location:" + store.get_location().toString() + "thành công", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                        Toast.makeText(_mContext, "Cập nhật Store-Location:" + store.get_location().toString() + "thất bại", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    @Override
    public void deleteStore(Store store) {

    }

    public Task<Store> getStore(String DocumentId) {
        Location location = new Location(0, 0);
        VietnameseDelicacies Menu = new VietnameseDelicacies("","","","","");
        Store[] store = {new Store("", "", "", location,Menu)};

        DocumentReference storeRef = db.collection("Stores").document(DocumentId);

        return storeRef.get().continueWith(new Continuation<DocumentSnapshot, Store>() {
            @Override
            public Store then(@NonNull @NotNull Task<DocumentSnapshot> task) throws Exception {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();

                    String _MaCH = documentSnapshot.getString("_MaCH");
                    String _NguoiSoHuu = documentSnapshot.getString("_NguoiSoHuu");
                    String _TenCH = documentSnapshot.getString("_TenCH");

                    Map<String, Object> locationMap = (Map<String, Object>) documentSnapshot.get("_Location");
                    Map<String,Object> DelicaciesMenu =(Map<String, Object>) documentSnapshot.get("_Menu");
                    if (locationMap != null) {
                        double latitude = (double) locationMap.get("latitude");
                        double longitude = (double) locationMap.get("longitude");
                        location.setLatitude(latitude);
                        location.setLongitude(longitude);
                    }
                    if (DelicaciesMenu != null){
                        String TenMonAn  = (String)DelicaciesMenu.get("_TenMonAn");
                        String KieuMonAn = (String)DelicaciesMenu.get("_KieuMonAn");
                        String DiaPhuong = (String)DelicaciesMenu.get("_DiaPhuong");
                        String MieuTa = (String)DelicaciesMenu.get("_MieuTa");
                        //Link web ảnh
                        String HinhAnh =(String)DelicaciesMenu.get("_HinhAnh");

                        Menu.set_TenMon(TenMonAn);
                        Menu.set_MieuTa(MieuTa);
                        Menu.set_KieuMonAn(KieuMonAn);
                        Menu.set_HinhAnh(HinhAnh);
                        Menu.set_HinhAnh(HinhAnh);

                    }
                    store[0] = new Store(_MaCH, _NguoiSoHuu, _TenCH, location,Menu);
                    return store[0];
                } else {
                    // Handle the error
                    Exception e = task.getException();
                    Log.e("Firestore Error", e.getMessage());
                    throw e;
                }

            }
        });
    }







    //Để giải quyết vấn đề này, bạn có thể sử dụng Task để đợi cho dữ liệu được
    // trả về trước khi trả về danh sách DocumentID
    public Task<List<String>> getDocumentIds() {
        List<String> list_storeDocumentids = new ArrayList<>();
        Task<QuerySnapshot> task = storeCollection.get();
        return task.continueWith(new Continuation<QuerySnapshot, List<String>>() {
            @Override
            public List<String> then(@NonNull Task<QuerySnapshot> task) throws Exception {
                if (task.isSuccessful()) {
                    QuerySnapshot queryDocumentSnapshots = task.getResult();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        String documentId = documentSnapshot.getId();
                        list_storeDocumentids.add(documentId);
                        Log.d("Document ID", documentId);
                        // do something with the documentId
                    }
                    return list_storeDocumentids;
                } else {
                    // Handle the error
                    Exception e = task.getException();
                    Log.e("Firestore Error", e.getMessage());
                    throw e;
                }
            }
        });
    }
}
