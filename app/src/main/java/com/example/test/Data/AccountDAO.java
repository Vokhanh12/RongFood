package com.example.test.Data;

import android.accounts.Account;

import java.util.List;

public interface AccountDAO {
    public List<Account> getAllAccount();
    public Account getAccountById(int id);
    public void addAccount();
    public void updateAccount();
    public void deleteAccount();
}
