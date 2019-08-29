package sn.oneclic.bank.banksn.servicesimpl;

import sn.oneclic.bank.banksn.model.Account;
import sn.oneclic.bank.banksn.model.Bank;
import sn.oneclic.bank.banksn.model.Manager;
import sn.oneclic.bank.banksn.repository.ManagerRepository;
import sn.oneclic.bank.banksn.services.IManagerService;

import java.util.List;

public class ManagerServiceImpl implements IManagerService {

    private ManagerRepository managerRepository = new ManagerRepository();


    public Manager create(Bank bank, Manager manager) {
        return managerRepository.save(bank, manager);
    }

    public List<Manager> findAllManager(Bank bank) {
        return managerRepository.findAllManager(bank);
    }

    public Manager affectManagerToAccount(Manager manager, Account account) {
        return managerRepository.affectManagerToAccount(manager, account);
    }

}
