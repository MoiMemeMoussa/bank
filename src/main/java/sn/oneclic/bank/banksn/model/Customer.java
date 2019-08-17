package sn.oneclic.bank.banksn.model;

import sn.oneclic.bank.banksn.exceptions.AccountException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer {
    private List<Account> listAccount = new ArrayList<>();
    private int customerNumber;
    private String name;
    private String address;
    private String identityNumber;

    public Customer(String name, String address, String identityNumber, Account account) throws NullPointerException, AccountException {
        verifyIfNull(name, identityNumber, account);
        this.name = name;
        this.address = address;
        this.identityNumber = identityNumber;
        this.listAccount.add(account);

    }

    private void verifyIfNull(String name, String identityNumber, Account account) throws AccountException {
        if (account == null)
            throw new NullPointerException(" customer must have an account !!! ");
        if (account.getBalance() < 10000)
            throw new AccountException();
        if (identityNumber == null)
            throw new NullPointerException(" customer must have an identity number !!! ");
        if (name == null)
            throw new NullPointerException(" customer must have a name !!! ");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return customerNumber == customer.customerNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerNumber);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerNumber=" + customerNumber +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", identityNumber='" + identityNumber + '\'' +
                '}';
    }
}
