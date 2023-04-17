package com.example.test.Data;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.test.Model.Location;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import org.jetbrains.annotations.NotNull;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

public class LocationDAOimpl_Firestore implements LocationDAO {

    private FirebaseFirestore db;
    private Context mContext;

    public LocationDAOimpl_Firestore(Context mContext){
        this.mContext = mContext;
        this.db = FirebaseFirestore.getInstance();
    }

    @Override
    public boolean addLocation(Location location) {
        db.collection("Locations")
                .add(location)
                .addOnCompleteListener((Activity) mContext,new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(mContext,"Thêm thành công",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext,"Thêm thất bại",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return true;
    }

    @Override
    public void updateLocation(Location location) {

    }

    @Override
    public void deleteLocation(Location location) {

    }
}
