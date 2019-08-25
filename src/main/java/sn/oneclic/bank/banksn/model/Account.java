package sn.oneclic.bank.banksn.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import sn.oneclic.bank.banksn.exceptions.AccountException;


@Slf4j
@Getter
@Setter
public class Account {

    private String accountNumber;
    private int balance;

    private Bank bank;
    private Customer customer;
    private Manager manager; // an account has 1 manager

    public Account() {

    }

    //creation d'un compte temporaire
    protected Account(Bank bank, String accountNumber, int balance, Customer customer) {
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.customer = customer;
    }

    public void credit(int sum) {
        this.balance = this.balance + sum;
    }

    public void debit(int sum) throws AccountException {
        if (this.balance >= sum) {
            this.balance = this.balance - sum;
        } else {
            throw new AccountException(" debit impossible, your balance is " + this.balance);
        }
    }

    public void transfer(Account account, int sum) throws AccountException {
        if (this.balance >= sum) {
            account.setBalance(account.getBalance() + sum);
            this.debit(sum);
        } else {
            throw new AccountException(" transfer impossible, your balance is " + this.balance);
        }

    }


}
