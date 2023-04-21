package com.example.test.Data.Vietnamese_Delicacies;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.example.test.Model.VietnameseDelicacies;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class VietnameseDelicaciesimpl_Firestore implements VietnameseDelicaciesDao {

    private static final String TAG = "VietnameseDelicaciesimpl_Firestore";
    private FirebaseFirestore db;
    private Context _mContext;
    private CollectionReference VietnameseDelicaciesCollection;


    public VietnameseDelicaciesimpl_Firestore(Context _mContext) {

        this._mContext = _mContext;
        db = FirebaseFirestore.getInstance();
        this.VietnameseDelicaciesCollection = db.collection("VietnameseDelicacies Collection");
    }


    @Override
    public void addVietnameseDelicacies(VietnameseDelicacies vietnameseDelicacies) {
        VietnameseDelicaciesCollection.whereEqualTo("_TenMon", vietnameseDelicacies.get_TenMon()).get()
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
                            VietnameseDelicaciesCollection.add(newVietnamseDilicacies)
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
}
