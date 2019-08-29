package sn.oneclic.bank.banksn.services;

import sn.oneclic.bank.banksn.exceptions.AccountException;
import sn.oneclic.bank.banksn.model.Account;
import sn.oneclic.bank.banksn.model.Bank;
import sn.oneclic.bank.banksn.model.Customer;

public interface IAccountService {

    void createAccount(Bank bank, Account account, Customer customer) throws AccountException;

    Account creditAccount(Account account, int sum);

    Account debitAccount(Account account, int sum) throws AccountException;

    Account transfer(Account sender, Account recipient, int sum) throws AccountException;


}
