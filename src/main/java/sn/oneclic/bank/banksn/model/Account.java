package sn.oneclic.bank.banksn.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import sn.oneclic.bank.banksn.BankUtils;
import sn.oneclic.bank.banksn.exceptions.AccountException;

import java.util.logging.Logger;


@Slf4j
@Getter
@Setter
public class Account {

    private String accountNumber;
    private int balance;

    private Bank bank;
    private Customer customer;
    private Manager manager; // an account has 1 manager
    private static Logger logger = Logger.getLogger("Account");

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

    public Account creditAccount() {
        int sum = Integer.parseInt(BankUtils.doOperation(" Enter sum to credit  "));
        this.credit(sum);
        logger.info(" OK >>> Account credited " + sum + "  >>> new balance = " + this.getBalance());
        return this;
    }

    public Account debitAccount() {
        int sum = Integer.parseInt(BankUtils.doOperation(" Enter sum to take off   "));
        logger.info(" OK >>> Account debited of " + sum + "  >>> new balance = " + this.getBalance());
        try {
            this.debit(sum);
        } catch (AccountException accountException) {
            accountException.printStackTrace();
        }
        return this;
    }

    public void transfert(Account recipient) {
        int sumTransfert = BankUtils.doTransfert();
        try {
            this.transfer(recipient, sumTransfert);
            logger.info(" OK >>> " + this.getAccountNumber() + " transfert  " + sumTransfert + " to " + recipient.getAccountNumber());
            logger.info(" NEW BALANCE >>> " + this.getAccountNumber() + "  =  " + this.getBalance() + " |  " + recipient.getAccountNumber() + " = " + recipient.getBalance());
        } catch (AccountException accountException) {
            accountException.printStackTrace();
        }

    }
}
