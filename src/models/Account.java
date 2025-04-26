package models;

public class Account {
    private int accountId;
    private int userId;
    private String accountType;
    private double balance;



    public Account(int accountId,int userId,String accountType, double balance){
        this.accountId=accountId;
        this.userId=userId;
        this.accountType=accountType;
        this.balance=balance;
    }
    public int getAccountId(){
        return accountId;
    }
    public int getUserId(){
        return userId;
    }
    public String getAccountType(){
        return accountType;
    }
    public double getBalance(){
        return balance;
    }
}
