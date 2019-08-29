package sn.oneclic.bank.banksn.repository;

import sn.oneclic.bank.banksn.model.Account;
import sn.oneclic.bank.banksn.model.Bank;
import sn.oneclic.bank.banksn.model.Customer;

public class AccountRepository {


    public void createAccount(Bank bank, Account account, Customer customer) {
        bank.getAccountList().add(account);
        customer.getAccountList().add(account);
    }

    public Account credit(Account account, int sum) {
        account.setBalance(account.getBalance() + sum);
        return account;
    }

    public Account debit(Account account, int sum) {
        account.setBalance(account.getBalance() - sum);
        return account;
    }

    public Account transfer(Account sender, Account recipient, int sum) {
        sender = this.debit(sender, sum);
        recipient.setBalance(recipient.getBalance() + sum);
        return sender;
    }
}
