package sn.oneclic.bank.banksn.model;

import java.util.Objects;

public class Account {

    private Agency agency;
    private String accountNumber;
    private int balance;
    private Customer customer;

    public Account() {

    }

    public Account(Agency agency, String accountNumber) {
        verifyIfNull(agency, accountNumber);
        this.agency = agency;
        this.accountNumber = accountNumber;

    }

    private void verifyIfNull(Agency agency, String accountNumber) {
        if (agency == null)
            throw new NullPointerException(" account cannot be opened without agency ");
        if (accountNumber == null)
            throw new NullPointerException(" account cannot be opened without accountNumber ");

    }

    String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
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

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", balance=" + balance +
                '}';
    }

    public void openAcccount(Customer customer, Account account) {
        account.setCustomer(customer);
    }


}
