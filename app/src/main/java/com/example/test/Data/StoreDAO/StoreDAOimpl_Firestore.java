package com.example.test.Data.StoreDAO;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.test.Model.Store;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class StoreDAOimpl_Firestore implements StoreDao{

    private static final String TAG = "StoreDAOimpl_Firestore";
    private Context _mContext;
    private FirebaseFirestore db;
    private CollectionReference storeCollection;

    public StoreDAOimpl_Firestore(Context context){
        this._mContext = context;
        this.db = FirebaseFirestore.getInstance();
        this.storeCollection = db.collection("Stores");
    }


    @Override
    public void addStore(Store store) {
        storeCollection.whereEqualTo("_MaCH",store.get_MaCH()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size()==0){
                            Map<String,Object> newStore = new HashMap<>();
                            newStore.put("_MaCH",store.get_MaCH());
                            newStore.put("_TenCH",store.getTenCH());
                            newStore.put("_NguoiSoHuu",store.get_NguoiSoHu());
                            newStore.put("_Location",store.get_location());
                            storeCollection.add(newStore).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "Store document added with ID: " + documentReference.getId());
                                    Toast.makeText(_mContext,"Thêm Store_MaCH:"+store.get_MaCH()+" Thành Công",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                        else {
                            Log.w(TAG, "MaCH " + store.get_MaCH() + " already exists");
                            Toast.makeText(_mContext,"Thêm Store_MaCH:"+store.get_MaCH()+" Thất bại",Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }

    @Override
    public void updateStore(Store store,String DocumentID) {
        DocumentReference storeRef = db.collection("Stores").document(DocumentID);

        // Update the "_MaCH" field of the document
        storeRef
                .update("_MaCH", store.get_MaCH())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                        Toast.makeText(_mContext,"Cập nhật Store-MaCH:"+ store.get_MaCH()+"thành công",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                        Toast.makeText(_mContext,"Cập nhật Store-MaCH:"+ store.get_MaCH()+"thất bại",Toast.LENGTH_SHORT).show();
                    }
                });

        // Update the "_NguoiSoHuu" field of the document
        storeRef
                .update("_NguoiSoHuu", store.get_NguoiSoHu())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                        Toast.makeText(_mContext,"Cập nhật Store-MaCH:"+  store.get_NguoiSoHu()+"thành công",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                        Toast.makeText(_mContext,"Cập nhật Store-MaCH:"+ store.get_MaCH()+"thất bại",Toast.LENGTH_SHORT).show();
                    }
                });


        // Update the "_Location" field of the document
        storeRef
                .update("_Location", store.get_NguoiSoHu())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                        Toast.makeText(_mContext,"Cập nhật Store-Location:"+  store.get_location().toString()+"thành công",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                        Toast.makeText(_mContext,"Cập nhật Store-Location:"+  store.get_location().toString()+"thất bại",Toast.LENGTH_SHORT).show();
                    }
                });


    }

    @Override
    public void deleteStore(Store store) {

    }
}
