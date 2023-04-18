package com.example.test.Data.AccountDAO;

import com.example.test.Model.Account;

public interface AccountDAO {
        public Account getAcccountByUsername(String Username);
        public boolean addAccount(Account account);
        public void updateAccount(Account account);
        public void deleteAccount(int idAccount);


}
