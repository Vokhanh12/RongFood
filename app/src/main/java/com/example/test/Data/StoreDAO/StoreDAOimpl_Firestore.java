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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import org.jetbrains.annotations.NotNull;

public class StoreDAOimpl_Firestore implements StoreDao{

    private static final String TAG = "StoreDAOimpl_Firestore";
    private Context _mContext;
    private FirebaseFirestore db;

    public StoreDAOimpl_Firestore(Context context){
        this._mContext = context;
        db = FirebaseFirestore.getInstance();
    }


    @Override
    public void addStore(Store store) {
        db.collection("Stores")
                .add(store)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(_mContext,"Thêm Store Thành công",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        Toast.makeText(_mContext,"Thêm Store Thất bại",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void updateStore(Store store,String DocumentID) {
        DocumentReference storeRef = db.collection("Stores").document(DocumentID);

        // Update the "NguoiSoHuu" field of the document
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

        storeRef
                .update("_Location", store.get_NguoiSoHu())
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


    }

    @Override
    public void deleteStore(Store store) {

    }
}
