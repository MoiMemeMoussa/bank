package sn.oneclic.bank.banksn.servicesimpl;

import sn.oneclic.bank.banksn.model.Bank;
import sn.oneclic.bank.banksn.model.Manager;
import sn.oneclic.bank.banksn.repository.ManagerRepository;
import sn.oneclic.bank.banksn.services.IManagerService;

public class ManagerServiceImpl implements IManagerService {

    private ManagerRepository managerRepository = new ManagerRepository();


    public Manager create(Bank bank, Manager manager) {
        return managerRepository.save(bank, manager);
    }


}
