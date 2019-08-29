package sn.oneclic.bank.banksn.repository;

import sn.oneclic.bank.banksn.model.*;

public class BankRepository {


    public Agency save(Bank bank, Agency agency) {
        bank.getAgencyList().add(agency);
        return agency;
    }

    public Manager saveManager(Bank bank, Manager manager) {
        bank.getManagerList().add(manager);
        //manager.getAccountList().add(account);
        return manager;
    }

    public Customer saveCustomer(Bank bank, Customer customer) {
        bank.getCustomerList().add(customer);
        return customer;
    }

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
        this.debit(sender, sum);
        recipient.setBalance(recipient.getBalance() + sum);
        return sender;
    }
}
