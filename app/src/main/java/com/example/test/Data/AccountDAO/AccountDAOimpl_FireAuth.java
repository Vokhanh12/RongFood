package com.example.test.Data.AccountDAO;

import android.app.Activity;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.test.Model.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import org.jetbrains.annotations.NotNull;

import android.content.Context;


public class AccountDAOimpl_FireAuth implements AccountDAO {

    private FirebaseAuth mAuth;
    private Context mContext;

    public AccountDAOimpl_FireAuth(Context context){
        this.mContext=context;
        this.mAuth = FirebaseAuth.getInstance();
    }



    @Override
    public Account getAcccountByUsername(String Username) {


        return null;
    }

    @Override
    public boolean addAccount(Account account) {
        mAuth.createUserWithEmailAndPassword(account.getEmail(), account.getPassword())
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Đăng ký thành công, bạn có thể lưu thông tin tài khoản vào Firestore Database ở đây

                            Toast.makeText(mContext,"Dang ky thanh cong",Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(mContext,"Dang ky that baiadasd",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
        return true;
    }

    @Override
    public void updateAccount(Account account) {

    }

    @Override
    public void deleteAccount(int idAccount) {

    }
}
