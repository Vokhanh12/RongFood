package com.example.test.Data.Vietnamese_Delicacies;

import android.content.Context;
import com.example.test.Model.VietnameseDelicacies;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class VietnameseDelicaciesimpl_Firestore implements VietnameseDelicaciesDao{

    private static final String TAG = "VietnameseDelicaciesimpl_Firestore";
    private FirebaseFirestore db;
    private Context mContext;
    private CollectionReference VietnameseDelicaciesCollection;


    public VietnameseDelicaciesimpl_Firestore(Context mContext) {

        this.mContext = mContext;
        db = FirebaseFirestore.getInstance();
        this.VietnameseDelicaciesCollection = db.collection("VietnameseDelicacies Collection");
    }

    @Override
    public void addVietnameseDelicaciesDao(VietnameseDelicaciesDao vietnameseDelicaciesDao) {

    }

    @Override
    public void deleteVietnameseDelicaciesDao(VietnameseDelicacies vietnameseDelicacies) {

    }

    @Override
    public void updateVietnameseDelicaciesDao(VietnameseDelicacies vietnameseDelicacies) {

    }
}
