package com.example.test.SystemConfig;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class storeFirebase {

    public static FirebaseFirestore db;
    public static CollectionReference storesCollectionRef;
    public static String TAG = "storeFirebase";


    //static list_storeDocumentIds
    public final static List<String> list_storeDocumentIds = getAllDocumentID();



    public storeFirebase() {
    }

    public static List<String> getAllDocumentID() {


        storesCollectionRef = db.collection("Stores");
        db = FirebaseFirestore.getInstance();



        List<String> list_storeDocumentIds = new ArrayList<>();
        storesCollectionRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    list_storeDocumentIds.add(document.getId());
                }
            }
        });


        return list_storeDocumentIds;


    }



}
