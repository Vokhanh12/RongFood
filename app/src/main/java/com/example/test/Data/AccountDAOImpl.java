package com.example.test.Data;

import com.example.test.Model.Account;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.core.database.sqlite.SQLiteDatabaseKt;

public class AccountDAOImpl extends SQLiteOpenHelper implements AccountDAO{

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="rongfood.app";
    private static final String TABLE_ACCOUNTS="Account";

    private static final String COLUMN_USERNAME="username";
    private static final String COLUMN_PASSWORD="password";

    public AccountDAOImpl(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_USERS = "CREATE TABLE "+ TABLE_ACCOUNTS +"("
                + COLUMN_USERNAME + " TEXT PRIMARY KEY,"
                + COLUMN_PASSWORD + " TEXT" + " )";
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);
        onCreate(db);
    }


    @Override
    public Account getAcccountByUsername(int id) {
        return null;
    }

    @Override
    public void addAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_USERNAME,account.getUsername());
        values.put(COLUMN_PASSWORD,account.getPassword());
    }

    @Override
    public void updateAccount(Account account) {

    }

    @Override
    public void deleteAccount(int idAccount) {

    }

}


