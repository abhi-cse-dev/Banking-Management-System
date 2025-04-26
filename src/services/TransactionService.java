package services;

import dao.TransactionDao;
import models.Transaction;

import java.util.List;

public class TransactionService {
    private final TransactionDao transactionDao = new TransactionDao();


    public boolean depositMoney(int accountId, double amount) {
        if (amount <= 0) {
            System.out.println("Invalid deposit amount");
            return false;
        }
        return transactionDao.deposit(accountId, amount);
    }

    public boolean withdrawMoney(int accountId, double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawl money");
            return false;
        }
        return transactionDao.withdraw(accountId, amount);


    }

    public boolean transferFunds(int senderId, int recieverId, double amount){
        return transactionDao.transferFunds(senderId,recieverId,amount);
    }

    public List<Transaction> getTransactionHistory(int accountId){
        return transactionDao.getTransactionHistory(accountId);
    }
}
