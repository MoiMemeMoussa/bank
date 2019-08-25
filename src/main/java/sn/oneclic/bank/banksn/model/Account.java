package sn.oneclic.bank.banksn.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

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

    public void credit(int mountant) {
        this.balance = this.balance + mountant;
    }

    public void takeFromAcount(int mountant) {
        this.balance = this.balance - mountant;
    }

    public void transfer(Account account, int mountant) {
        account.setBalance(account.getBalance() + mountant);
    }


}
