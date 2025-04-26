import models.Account;

import models.Transaction;
import models.User;
import services.AccountService;
import services.AuthenticationService;
import services.TransactionService;

import java.util.List;
import java.util.Scanner;

public class BankingMain {

    public static void main(String[] args) {
        AuthenticationService authService = new AuthenticationService();
        AccountService accountService = new AccountService();
        TransactionService transactionService = new TransactionService();
        Scanner sc = new Scanner(System.in);

        while(true) {

            System.out.println("****** WELCOME TO CLI BANKING SYSTEM *******");
            System.out.println("1. Sign Up");
            System.out.println("2. Sign In");
            System.out.println("Choose an option:");
            int choice = sc.nextInt();
            sc.nextLine();

            //User user = null;
            switch(choice) {

                case 1:
                    if (choice == 1) {
                        System.out.println("Enter username: ");
                        String username = sc.nextLine();
                        System.out.println("Enter password: ");
                        String password = sc.nextLine();

                        if (authService.registerUser(username, password)) {
                            System.out.println("User registered successfully");
                        } else {
                            System.out.println("Error: could not register user");
                            return;
                        }
                    }
                    break;
                //else if (choice == 2)

                //System.out.println("Now, You can login");
                case 2:
                    System.out.println("Enter username: ");
                    String username = sc.nextLine();
                    System.out.println("Enter password: ");
                    String password = sc.nextLine();

                    User user = authService.loginUser(username, password);
                    if (user == null) {
                        System.out.println("Invalid credential");
                        return;
                    }
                    System.out.println("User login successfully");

                    //check if user has an account
                    Account account = accountService.getAccount(user.getUserId());
                    if (account == null) {
                        System.out.println("You don't have an account yet.");
                        System.out.println("Do you want to create one? (yes/no) : ");
                        String response = sc.nextLine();
                        if (response.equalsIgnoreCase("Yes")) {
                            System.out.println("Choose an account type (SAVINGS/CURRENT): ");
                            String accountType = sc.nextLine().toUpperCase();

                            if (!accountType.equals("SAVINGS") && !accountType.equals("CURRENT")) {
                                System.out.println("Invalid account type, Please restart");
                                return;
                            }
                            if (accountService.createAccount(user.getUserId(), accountType)) {
                                System.out.println(accountType + "account created sucessfully!");
                                account = accountService.getAccount(user.getUserId());
                            } else {
                                System.out.println("Error. could not create account");
                            }
                        }

                    } else {
                        System.out.println("You already have a " + account.getAccountType() + " account");
                    }


                    while (true) {
                        System.out.println("\nBanking menu: ");
                        System.out.println("1. Deposit money : ");
                        System.out.println("2. Withdraw money : ");
                        System.out.println("3. Check Balance");
                        System.out.println("4. Fund Transfer");
                        System.out.println("5. View Transaction History");
                        System.out.println("6. Exit...  ");
                        System.out.println("Choose an option: ");
                        int option = sc.nextInt();

                        switch (option) {
                            case 1:
                                System.out.println("Enter  amount to deposit: ");
                                double depositAmount = sc.nextDouble();
                                if (transactionService.depositMoney(account.getAccountId(), depositAmount)) {
                                    System.out.println("Deposit successfully");
                                } else {
                                    System.out.println("Deposit failed");
                                }
                                break;
                            case 2:
                                System.out.println("Enter amount to withdraw: ");
                                double withdrawAmount = sc.nextDouble();

                                if (transactionService.withdrawMoney(account.getAccountId(), withdrawAmount)) {
                                    System.out.println("Withdraw successfully");
                                } else {
                                    System.out.println("Withdraw failed");
                                }

                                break;

                            case 3:
                                System.out.println("Enter your accountId to to check balance: ");
                                int accountId = sc.nextInt();
                                double balance = accountService.checkBalance(accountId);
                                if(balance != -1){
                                    System.out.println("Your current balance is " +balance);
                                }else{
                                    System.out.println("Account not found or error retrieving balance");

                                }
                              break;

                            case 4:

                                System.out.println("Enter your accounId (Sender): ");
                                int senderId = sc.nextInt();

                                System.out.println("Enter reciever's accountId: ");
                                int recieverId = sc.nextInt();

                                System.out.println("Enter amount to transfer: ");
                                double amount = sc.nextDouble();

                                 boolean success = transactionService.transferFunds(senderId , recieverId, amount);
                                if(success) {
                                    System.out.println("Fund transfer sucessfull!");
                                }
                                    else{
                                        System.out.println("Fund transfer failed, Try again");

                                }
                                    break;


                            case 5:

                                System.out.println("Enter your accountId to view history: ");
                                 accountId = sc.nextInt();
                                List<Transaction> history = transactionService.getTransactionHistory(accountId);
                                if(history.isEmpty()){
                                    System.out.println("No transaction found ");
                                }else{
                                    System.out.println("Transaction History: ");
                                    for(Transaction transaction : history){
                                        System.out.println(transaction);
                                    }
                                }

                                break;

                            case 6:
                                System.out.println("Exiting System");
                                int i=5;
                                while(i!=0){
                                    System.out.print(".");
                                    try {
                                        Thread.sleep(450);
                                    }catch (InterruptedException e){
                                        System.out.println(e.getMessage());
                                    }
                                    i--;
                                }
                                System.out.println();
                                System.out.println("!!! *** Thank-you for visit our application *** !!!");
                                return;

                            default:
                                System.out.println("Invalid choice, Try again !!!");

                        }


                    }

            }
        }}

}


