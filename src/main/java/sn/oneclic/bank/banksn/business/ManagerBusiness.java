package sn.oneclic.bank.banksn.business;

import lombok.extern.slf4j.Slf4j;
import sn.oneclic.bank.banksn.exceptions.BankException;
import sn.oneclic.bank.banksn.exceptions.ManagerException;
import sn.oneclic.bank.banksn.model.Agency;
import sn.oneclic.bank.banksn.model.Bank;
import sn.oneclic.bank.banksn.model.Manager;
import sn.oneclic.bank.banksn.services.IManagerService;
import sn.oneclic.bank.banksn.servicesimpl.ManagerServiceImpl;

@Slf4j
public class ManagerBusiness {

    private IManagerService iManagerService = new ManagerServiceImpl();

    public ManagerBusiness() {

    }

    public Manager createManager(Bank bank) {

        Agency agency = bank.getAgencyList().get(0);
        Manager manager = null;
        try {
            manager = new Manager("Saliou FALL", "060555749", bank, agency);
            iManagerService.create(bank, manager);
            log.info(" OK >>> Manager created for bank " + bank.getName());

        } catch (BankException | ManagerException exception) {
            log.error(" <<<<   KO !!! error happened >>>>> \t");
            log.error(exception.getMessage());
        }
        return manager;
    }

}
