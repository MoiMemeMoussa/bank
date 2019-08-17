package sn.oneclic.bank.banksn.model;

import sn.oneclic.bank.banksn.exceptions.AccountException;

import java.util.Objects;

public class Account {

    private Agency agency;
    private String accountNumber;
    private int balance;

    public Account() {

    }

    public Account(Agency agency, String accountNumber, int balance) throws AccountException {
        if (agency == null)
            throw new NullPointerException(" account cannot be opened without agency ");
        if (accountNumber == null)
            throw new NullPointerException(" account cannot be opened without accountNumber ");
        if (balance < 10000)
            throw new AccountException();
        this.agency = agency;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    private String getAccountNumber() {
        return accountNumber;
    }

    int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountNumber.equals(account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", balance=" + balance +
                '}';
    }
}
