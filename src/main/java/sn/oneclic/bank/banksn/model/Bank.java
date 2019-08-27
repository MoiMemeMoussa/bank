package sn.oneclic.bank.banksn.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import sn.oneclic.bank.banksn.BankUtils;
import sn.oneclic.bank.banksn.exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Slf4j
@Getter
public class Bank {

    private static int id;
    private String name;

    private List<Agency> agencyList = new ArrayList<>();
    private List<Account> accountList = new ArrayList<>();
    private List<Manager> managerList = new ArrayList<>();
    private List<Customer> customerList = new ArrayList<>();

    private static Logger logger = Logger.getLogger("Bank");

    public Bank(String name) throws BankException {
        if (name == null)
            throw new BankException();
        id++;
        this.name = name;
    }


    public Agency addAgency(String address, int phone) throws BankException, AgencyException {
        Agency agency1 = new Agency(address, phone, this);
        this.agencyList.add(agency1);
        return agency1;
    }

    public Manager addManager(Agency agency, String name, int phone) throws BankException, ManagerException {
        Manager manager = new Manager(name, phone, this, agency);
        this.managerList.add(manager);
        return manager;
    }


    public void affectAccountToManager(Account account, Manager manager) throws AccountManagerException {
        if (!manager.getAccountList().contains(account)) {
            manager.getAccountList().add(account);
            account.setManager(manager);
        } else
            throw new AccountManagerException();
    }

    public Customer addCustomer(int id, String name, String address, int phone) throws CustomerException {
        Customer customer = new Customer(id, name, address, phone, "1667198400225");
        this.customerList.add(customer);
        return customer;
    }

    public void affectAccountToCustomer(Customer customer, String accountNumber, int balance) {
        this.accountList.add(new Account(this, accountNumber, balance, customer));
        customer.getAccountList().add(new Account(this, accountNumber, balance, customer));

    }


    public String displayCustomerInformationByAccountNumber(String accountNumber) {
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

//    public void showAgencies() {
//        StringBuilder stringBuilder = new StringBuilder();
//        for (Agency agency : getAgencyList()) {
//            stringBuilder.append(" [ Agency ]");
//
//            stringBuilder.append(" Address = ");
//            stringBuilder.append(agency.getAddress());
//            stringBuilder.append('\n');
//
//            stringBuilder.append(" Phone = ");
//            stringBuilder.append(agency.getPhone());
//            stringBuilder.append('\n');
//        }
//    }

//    public void showManagers() {
//        StringBuilder stringBuilder = new StringBuilder();
//        for (Manager manager : getManagerList()) {
//            stringBuilder.append(" [ Manager ]");
//            stringBuilder.append(" Name = ");
//            stringBuilder.append(manager.getName());
//            stringBuilder.append('\n');
//
//            stringBuilder.append(" Phone = ");
//            stringBuilder.append(manager.getPhone());
//            stringBuilder.append('\n');
//        }
//    }

    public Agency createAgency() {
        String address = BankUtils.doOperation(" Give address of agency ");
        int phone = Integer.parseInt(BankUtils.doOperation(" Give phone number of agency"));
        Agency agency = null;
        try {
            agency = this.addAgency(address, phone);
            logger.info(" OK >>> Agency created for bank " + this.getName() + '\n');
        } catch (BankException | AgencyException exception) {
            logger.severe(" <<<<   KO !!! Une erreur est survenue >>>>> \t");
            logger.severe(exception.getMessage());
        }
        return agency;
    }

    public Manager createManager(Agency agency) {
        String name = BankUtils.doOperation(" Give name  of the manager ");
        int phone = Integer.parseInt(BankUtils.doOperation(" Give phone number of manager"));
        Manager manager = null;
        try {
            manager = this.addManager(agency, name, phone);
            logger.info(" OK >>> Manager created for bank " + this.getName());

        } catch (BankException | ManagerException exception) {
            logger.severe(" <<<<   KO !!! Une erreur est survenue >>>>> \t");
            logger.severe(exception.getMessage());
        }
        return manager;
    }

    public Customer createCustomer(Manager manager) {
        String name = BankUtils.doOperation(" Give name  of the customer ");
        String address = BankUtils.doOperation(" Give address  of customer");
        int phone = Integer.parseInt(BankUtils.doOperation(" Give phone number of customer"));
        Customer customer = null;
        try {
            customer = this.addCustomer(1, name, address, phone);
            this.affectAccountToCustomer(customer, "SN010285", 10000);
            this.affectAccountToCustomer(customer, "SN180381", 25000);
            this.affectAccountToManager(this.getAccountList().get(0), manager);
            this.affectAccountToManager(this.getAccountList().get(1), manager);
            logger.info(" OK >>> Customer  = " + customer.getName() + " | Account Number = " + this.getAccountList().get(0).getAccountNumber()
                    + " | Manager = " + manager.getName());

        } catch (CustomerException | AccountManagerException customerException) {
            customerException.printStackTrace();
        }
        return customer;
    }

}
