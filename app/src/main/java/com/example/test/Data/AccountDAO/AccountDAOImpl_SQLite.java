package com.example.test.Data.AccountDAO;

import android.database.Cursor;
import com.example.test.Model.Account;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AccountDAOImpl_SQLite extends SQLiteOpenHelper implements AccountDAO{

    private SQLiteDatabase db;
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="rongfood.db";
    private static final String TABLE_ACCOUNTS="Account";

    private static final String COLUMN_USERNAME="username";
    private static final String COLUMN_PASSWORD="password";

    public AccountDAOImpl_SQLite(Context context){
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
    public Account getAcccountByUsername(String Username) {

        db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ACCOUNTS, new String[]{COLUMN_USERNAME,
                        COLUMN_PASSWORD}, COLUMN_USERNAME + "=?",
                new String[]{Username}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Account account = new Account(cursor.getString(0),
                cursor.getString(1));

        cursor.close();
        db.close();

        return account;
    }

    @Override
    public boolean addAccount(Account account) {
         db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

      //  values.put(COLUMN_USERNAME,account.getUsername());
        values.put(COLUMN_PASSWORD,account.getPassword());

        long result = db.insert(TABLE_ACCOUNTS, null, values);

        db.close();

        return result != -1;
    }

    @Override
    public void updateAccount(Account account) {

    }

    @Override
    public void deleteAccount(int idAccount) {

    }

}


