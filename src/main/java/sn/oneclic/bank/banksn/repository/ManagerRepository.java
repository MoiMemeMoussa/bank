package sn.oneclic.bank.banksn.repository;

import sn.oneclic.bank.banksn.model.Bank;
import sn.oneclic.bank.banksn.model.Manager;

public class ManagerRepository {


    public Manager save(Bank bank, Manager manager) {
        bank.getManagerList().add(manager);
        return manager;
    }


}
