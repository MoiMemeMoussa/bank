package sn.oneclic.bank.banksn.services;

import sn.oneclic.bank.banksn.exceptions.AccountException;
import sn.oneclic.bank.banksn.model.*;

public interface IBankService {

    Agency createAgency(Bank bank, Agency agency) throws AccountException;

    Manager createManager(Bank bank, Manager manager);

    Customer createCustomer(Bank bank, Customer customer);

    void createAccount(Bank bank, Account account, Customer customer) throws AccountException;

    Account creditAccount(Account account, int sum);

    Account debitAccount(Account account, int sum) throws AccountException;

    Account transfer(Account sender, Account recipient, int sum) throws AccountException;


}
