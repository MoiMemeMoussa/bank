package sn.oneclic.bank.banksn.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import sn.oneclic.bank.banksn.exceptions.AccountException;
import sn.oneclic.bank.banksn.exceptions.BankException;
import sn.oneclic.bank.banksn.exceptions.CustomerException;


@Slf4j
@Getter
@Setter
public class Account {

    private static final int ACCOUNT_NUMBER_CHARACTER = 6;
    private String accountNumber;
    private int balance;
    private Bank bank;
    private Customer customer;
    private Manager manager; // an account has 1 manager

    public Account() {

    }

    public Account(Bank bank, String accountNumber, int balance, Customer customer) throws BankException, AccountException, CustomerException {
        verify(bank, accountNumber, balance, customer);
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.customer = customer;
    }

    private void verify(Bank bank, String accountNumber, int balance, Customer customer)
            throws BankException, AccountException, CustomerException {
        if (bank == null) {
            throw new BankException();
        } else if (customer == null) {
            throw new CustomerException("cannot create account without customer !!!! ");
        } else if (accountNumber == null) {
            throw new AccountException("Account number cannot be null !!! ");
        } else if (accountNumber.length() != ACCOUNT_NUMBER_CHARACTER) {
            throw new AccountException(" Account number " + accountNumber + " must be 6 caracters !!!! ");
        } else if (balance < 10000) {
            throw new AccountException(" account is opened at least with 10000 !!! ");
        }

    }

}
