package sn.oneclic.bank.banksn.repository;

import sn.oneclic.bank.banksn.model.Account;
import sn.oneclic.bank.banksn.model.Bank;
import sn.oneclic.bank.banksn.model.Manager;

import java.util.List;

public class ManagerRepository {

    public Manager save(Bank bank, Manager manager) {
        bank.getManagerList().add(manager);
        return manager;
    }

    public List<Manager> findAllManager(Bank bank) {
        return bank.getManagerList();
    }

    public Manager affectManagerToAccount(Manager manager, Account account) {
        manager.getAccountList().add(account);
        return manager;
    }

}
