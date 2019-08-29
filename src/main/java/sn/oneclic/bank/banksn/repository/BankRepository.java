package sn.oneclic.bank.banksn.repository;

import sn.oneclic.bank.banksn.model.Agency;
import sn.oneclic.bank.banksn.model.Bank;
import sn.oneclic.bank.banksn.model.Customer;


public class BankRepository {


    public Agency save(Bank bank, Agency agency) {
        bank.getAgencyList().add(agency);
        return agency;
    }

    public Customer saveCustomer(Bank bank, Customer customer) {
        bank.getCustomerList().add(customer);
        return customer;
    }


}
