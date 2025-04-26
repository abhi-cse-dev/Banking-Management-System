package models;

import java.sql.Timestamp;

public class Transaction {
    private final int transactionId;
    private final int accountId;
    private final String type;
    private final double amount;
    private final Timestamp date;

    public Transaction(int transactionId, int accountId, String type, double amount, Timestamp date) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.date = date;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Transaction{");
        sb.append("transactionId=").append(transactionId);
        sb.append(", accountId=").append(accountId);
        sb.append(", type='").append(type).append('\'');
        sb.append(", amount=").append(amount);
        sb.append(", date=").append(date);
        sb.append('}');
        return sb.toString();
    }
}
