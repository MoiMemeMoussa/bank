package sn.oneclic.bank.banksn.services;

import sn.oneclic.bank.banksn.model.Account;
import sn.oneclic.bank.banksn.model.Bank;
import sn.oneclic.bank.banksn.model.Manager;

import java.util.List;

public interface IManagerService {


    Manager create(Bank bank, Manager manager);

    List<Manager> findAllManager(Bank bank);

    Manager affectManagerToAccount(Manager manager, Account account);

}
