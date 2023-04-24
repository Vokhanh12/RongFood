package com.example.test.Data.Vietnamese_Delicacies;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.test.Model.Location;
import com.example.test.Model.MonAn_VietnameseDelicacies;
import com.example.test.Model.Store;
import com.example.test.Model.VietnameseDelicacies;
import com.google.android.gms.tasks.*;
import com.google.firebase.firestore.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class VietnameseDelicaciesimpl_Firestore implements VietnameseDelicaciesDao {

    private static final String TAG = "VietnameseDelicaciesimpl_Firestore";
    private FirebaseFirestore db;
    private Context _mContext;
    private CollectionReference VietnameseDelicaciesCollectionRef;


    public VietnameseDelicaciesimpl_Firestore(Context _mContext) {

        this._mContext = _mContext;
        db = FirebaseFirestore.getInstance();
        this.VietnameseDelicaciesCollectionRef = db.collection("VietnameseDelicacies Collection");
    }


    @Override
    public void addVietnameseDelicacies(VietnameseDelicacies vietnameseDelicacies) {
        VietnameseDelicaciesCollectionRef.whereEqualTo("_TenMon", vietnameseDelicacies.get_TenMon()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.size() == 0) {
                            Map<String, Object> newVietnamseDilicacies = new HashMap<>();
                            newVietnamseDilicacies.put("_KieuMonAn", vietnameseDelicacies.get_KieuMonAn());
                            newVietnamseDilicacies.put("_TenMon", vietnameseDelicacies.get_TenMon());
                            newVietnamseDilicacies.put("_HinhAnh", vietnameseDelicacies.get_HinhAnh());
                            newVietnamseDilicacies.put("_DiaPhuong", vietnameseDelicacies.get_DiaPhuong());
                            newVietnamseDilicacies.put("_MieuTa", vietnameseDelicacies.get_MieuTa());
                            VietnameseDelicaciesCollectionRef.add(newVietnamseDilicacies)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.d(TAG, "Store document added with ID: " + documentReference.getId());
                                            Toast.makeText(_mContext, "Thêm Store_TenMon:" + vietnameseDelicacies.get_TenMon() + " Thành Công", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        } else {
                            Log.w(TAG, "TenMon:" + vietnameseDelicacies.get_TenMon() + " already exists");
                            Toast.makeText(_mContext, "Thêm Store_TenMon:" + vietnameseDelicacies.get_TenMon() + " Thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    @Override
    public void deleteVietnameseDelicacies(VietnameseDelicacies vietnameseDelicacies) {

    }

    @Override
    public void updateVietnameseDelicacies(VietnameseDelicacies vietnameseDelicacies) {

    }

    public void getDocumentCount() {
        VietnameseDelicaciesCollectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int count = task.getResult().size();
                    Log.d(TAG, "Count: " + count);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    //Để giải quyết vấn đề này, bạn có thể sử dụng Task để đợi cho dữ liệu được
    // trả về trước khi trả về danh sách DocumentID
    public Task<List<String>> getDocumentIds() {
        List<String> list_VietnameseDelicaciesDocumentids = new ArrayList<>();
        Task<QuerySnapshot> task = VietnameseDelicaciesCollectionRef.get();
        return task.continueWith(new Continuation<QuerySnapshot, List<String>>() {
            @Override
            public List<String> then(@NonNull Task<QuerySnapshot> task) throws Exception {
                if (task.isSuccessful()) {
                    QuerySnapshot queryDocumentSnapshots = task.getResult();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        String documentId = documentSnapshot.getId();
                        list_VietnameseDelicaciesDocumentids.add(documentId);
                        Log.d("Document ID", documentId);
                        // do something with the documentId
                    }
                    return list_VietnameseDelicaciesDocumentids;
                } else {
                    // Handle the error
                    Exception e = task.getException();
                    Log.e("Firestore Error", e.getMessage());
                    throw e;
                }
            }
        });
    }


    public Task<VietnameseDelicacies> getVietnameseDelicacies(String DocumentId) {
        Location location = new Location(0, 0);
        VietnameseDelicacies[] vietnameseDelicacies = {new VietnameseDelicacies("", "", "", "", "")};

        DocumentReference VietnameseDelicaciesDocumentRef = db.collection("VietnameseDelicacies Collection").document(DocumentId);

        return VietnameseDelicaciesDocumentRef.get().continueWith(new Continuation<DocumentSnapshot, VietnameseDelicacies>() {
            @Override
            public VietnameseDelicacies then(@NonNull @NotNull Task<DocumentSnapshot> task) throws Exception {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    String _KieuMonAn = documentSnapshot.getString("_KieuMonAn");
                    String _TenMon = documentSnapshot.getString("_TenMon");
                    String _MieuTa = documentSnapshot.getString("_MieuTa");
                    String _HinhAnh = documentSnapshot.getString("_HinhAnh");
                    String _DiaPhuong = documentSnapshot.getString("_DiaPhuong");

                    vietnameseDelicacies[0] = new VietnameseDelicacies(_KieuMonAn, _TenMon, _HinhAnh, _DiaPhuong, _MieuTa);
                    return vietnameseDelicacies[0];
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
