package services;

import dao.AccountDao;
import models.Account;

public class AccountService {
    private final AccountDao accountDao = new AccountDao();
    public double checkBalance(int accountId) {
        return accountDao.getBalance(accountId);
    }

    public boolean createAccount(int userId, String accountType){
        Account existingAccount = accountDao.getAccountByUserId(userId);
        if(existingAccount != null){
            System.out.println("User already has an account : "+ existingAccount.getAccountType());
            return false;
        }
        return  accountDao.createAccount(userId, accountType);
    }
public Account getAccount(int userId){
        return  accountDao.getAccountByUserId(userId);
}

}
