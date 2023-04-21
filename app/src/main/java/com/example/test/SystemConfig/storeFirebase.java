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
        db = FirebaseFirestore.getInstance();
        storesCollectionRef = db.collection("Stores");
        List<String> list_storeDocumentIds = new ArrayList<>();

        storesCollectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        list_storeDocumentIds.add(document.getId());
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

        return list_storeDocumentIds;

    }



}
