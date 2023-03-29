package com.example.test.Data;

import com.example.test.Model.Account;

import java.util.List;

public interface AccountDAO {
        public Account getAcccountByUsername(int id);
        public void addAccount(Account account);
        public void updateAccount(Account account);
        public void deleteAccount(int idAccount);


}
