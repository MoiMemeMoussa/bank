package sn.oneclic.bank.banksn.business;

import sn.oneclic.bank.banksn.exceptions.BankException;
import sn.oneclic.bank.banksn.exceptions.ManagerException;
import sn.oneclic.bank.banksn.model.Agency;
import sn.oneclic.bank.banksn.model.Bank;
import sn.oneclic.bank.banksn.model.Manager;
import sn.oneclic.bank.banksn.services.IManagerService;
import sn.oneclic.bank.banksn.servicesimpl.ManagerServiceImpl;

import java.util.logging.Logger;

public class ManagerBusiness {
    private static Logger logger = Logger.getLogger("Bank");

    private IManagerService iManagerService = new ManagerServiceImpl();

    public ManagerBusiness() {

    }

    public Manager createManager(Bank bank) {

        Agency agency = bank.getAgencyList().get(0);
        Manager manager = null;
        try {
            manager = new Manager("Saliou FALL", "060555749", bank, agency);
            iManagerService.create(bank, manager);
            logger.info(" OK >>> Manager created for bank " + bank.getName());

        } catch (BankException | ManagerException exception) {
            logger.severe(" <<<<   KO !!! error happened >>>>> \t");
            logger.severe(exception.getMessage());
        }
        return manager;
    }

}
