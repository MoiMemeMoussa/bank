package sn.oneclic.bank.banksn.business;

import sn.oneclic.bank.banksn.BankUtils;
import sn.oneclic.bank.banksn.exceptions.AccountException;
import sn.oneclic.bank.banksn.exceptions.AgencyException;
import sn.oneclic.bank.banksn.exceptions.BankException;
import sn.oneclic.bank.banksn.exceptions.ManagerException;
import sn.oneclic.bank.banksn.model.Agency;
import sn.oneclic.bank.banksn.model.Bank;
import sn.oneclic.bank.banksn.model.Manager;
import sn.oneclic.bank.banksn.services.IBankService;
import sn.oneclic.bank.banksn.servicesimpl.BankServiceImpl;

import java.util.logging.Logger;

public class BankBusiness {
    private static Logger logger = Logger.getLogger("Bank");
    private IBankService iBankService = new BankServiceImpl();

    public BankBusiness() {

    }

    public Agency createAgency(Bank bank) {
        String address = BankUtils.doOperation(" Give address of agency ");
        int phone = Integer.parseInt(BankUtils.doOperation(" Give phone number of agency"));
        Agency agency = null;
        try {
            agency = new Agency(address, phone, bank);
            iBankService.createAgency(bank, agency);
            logger.info(" OK >>> Agency created for bank " + bank.getName() + '\n');
        } catch (BankException | AgencyException | AccountException exception) {
            logger.severe(" <<<<   KO !!! Une erreur est survenue >>>>> \t");
            logger.severe(exception.getMessage());
        }
        return agency;
    }


    public Manager createManager(Bank bank) {
        String name = BankUtils.doOperation(" Give name  of the manager ");
        int phone = Integer.parseInt(BankUtils.doOperation(" Give phone number of manager"));
        Agency agency = bank.getAgencyList().get(0);
        Manager manager = null;
        try {
            manager = new Manager(name, phone, bank, agency);
            iBankService.createManager(bank, manager);
            logger.info(" OK >>> Manager created for bank " + bank.getName());

        } catch (BankException | ManagerException exception) {
            logger.severe(" <<<<   KO !!! error happened >>>>> \t");
            logger.severe(exception.getMessage());
        }
        return manager;
    }

}
