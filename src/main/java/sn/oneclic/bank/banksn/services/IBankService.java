package sn.oneclic.bank.banksn.services;

import sn.oneclic.bank.banksn.exceptions.AccountException;
import sn.oneclic.bank.banksn.model.Agency;
import sn.oneclic.bank.banksn.model.Bank;
import sn.oneclic.bank.banksn.model.Customer;

public interface IBankService {

    Agency createAgency(Bank bank, Agency agency) throws AccountException;

    Customer createCustomer(Bank bank, Customer customer);




}
