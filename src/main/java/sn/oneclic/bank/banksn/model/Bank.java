package sn.oneclic.bank.banksn.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import sn.oneclic.bank.banksn.exceptions.*;

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

    public Bank(String name) throws BankException {
        if (name == null)
            throw new BankException();
        id++;
        this.name = name;
    }


    public void addAgency(String address, int phone) throws BankException, AgencyException {
        Agency agency1;
        agency1 = new Agency(address, phone, this);
        this.agencyList.add(agency1);

    }

    public void addManager(Agency agency, String name, int phone) {
        try {
            this.managerList.add(new Manager(name, phone, this, agency));
        } catch (BankException | ManagerException bankException) {
            bankException.printStackTrace();
        }
    }

    public void affectAccountToManager(Account account, Manager manager) throws AccountManagerException {
        if (!manager.getAccountList().contains(account)) {
            manager.getAccountList().add(account);
            account.setManager(manager);
        } else
            throw new AccountManagerException();
    }

    public Customer addCustomer(int id, String name, String address, int phone) {
        Customer customer = null;
        try {
            customer = new Customer(id, name, address, phone, "1667198400225");
        } catch (CustomerException customerException) {
            customerException.printStackTrace();
        }
        this.customerList.add(customer);
        return customer;
    }

    public void affectAccountToCustomer(Customer customer, String accountNumber, int balance) {
        this.accountList.add(new Account(this, accountNumber, balance, customer));

    }

    public Customer findCustomerByAccountNumber(String accountNumber) {
        StringBuilder stringBuilder = new StringBuilder();
        Customer customer = null;
        for (Account account : accountList) {
            if (account.getAccountNumber().equals(accountNumber)) {
                customer = account.getCustomer();
            }
        }
        return customer;
    }


    public String displayCustomerInformationUserAccountNumber(String accountNumber) {
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

                stringBuilder.append(" Customer Manager  => ");
                stringBuilder.append(account.getManager().getName());
                stringBuilder.append(" | ");
                stringBuilder.append(account.getManager().getPhone());
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
