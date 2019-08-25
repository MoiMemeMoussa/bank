package sn.oneclic.bank.banksn.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
public class Bank {

    private static int id;
    private String name;

    private List<Agency> agencyList = new ArrayList<>();
    private List<Account> accountList = new ArrayList<>();
    private List<Manager> managerList = new ArrayList<>();
    private List<Customer> customerList = new ArrayList<>();

    public Bank(String name) {
        if (name == null)
            throw new NullPointerException(" a bank must have a name !! ");
        id++;
        this.name = name;
    }


    public void addAgency(String address, int phone) {
        Agency agency1 = new Agency(address, phone, this);
        this.agencyList.add(agency1);
    }

    public void addManager(Agency agency, String name, int phone) {
        this.managerList.add(new Manager(name, phone, this, agency));
    }

    public Customer addCustomer(int id, String name, String address, int phone) {
        Customer customer = new Customer(id, name, address, phone);
        this.customerList.add(customer);
        return customer;
    }

    public void addAccount(Customer customer, String accountNumber, int balance) {
        this.accountList.add(new Account(this, accountNumber, balance, customer));
    }


    public String findCustomerByAccountNumber(String accountNumber) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Account account : accountList) {
            if (account.getAccountNumber().equals(accountNumber)) {
                stringBuilder.append(" Customer name  => ");
                stringBuilder.append(account.getCustomer().getName());
                stringBuilder.append('\n');

                stringBuilder.append(" Customer phone  => ");
                stringBuilder.append(account.getCustomer().getPhone());
                stringBuilder.append('\n');

                stringBuilder.append(" Customer address  => ");
                stringBuilder.append(account.getCustomer().getAddress());
                stringBuilder.append('\n');
            }
        }
        return stringBuilder.toString();
    }

    public void showAgencies() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Agency agency : getAgencyList()) {
            stringBuilder.append(" [ Agency ]");

            stringBuilder.append(" Address = ");
            stringBuilder.append(agency.getAddress());
            stringBuilder.append('\n');

            stringBuilder.append(" Phone = ");
            stringBuilder.append(agency.getPhone());
            stringBuilder.append('\n');
        }
    }

    public void showManagers() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Manager manager : getManagerList()) {
            stringBuilder.append(" [ Manager ]");
            stringBuilder.append(" Name = ");
            stringBuilder.append(manager.getName());
            stringBuilder.append('\n');

            stringBuilder.append(" Phone = ");
            stringBuilder.append(manager.getPhone());
            stringBuilder.append('\n');
        }
    }


}
