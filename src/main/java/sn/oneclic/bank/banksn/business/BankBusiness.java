package sn.oneclic.bank.banksn.business;

import sn.oneclic.bank.banksn.exceptions.AccountException;
import sn.oneclic.bank.banksn.exceptions.AgencyException;
import sn.oneclic.bank.banksn.exceptions.BankException;
import sn.oneclic.bank.banksn.model.Agency;
import sn.oneclic.bank.banksn.model.Bank;
import sn.oneclic.bank.banksn.services.IBankService;
import sn.oneclic.bank.banksn.servicesimpl.BankServiceImpl;

import java.util.logging.Logger;

public class BankBusiness {
    private static Logger logger = Logger.getLogger("Bank");
    private IBankService iBankService = new BankServiceImpl();

    public BankBusiness() {

    }

    public Agency createAgency(Bank bank) {
        Agency agency = null;
        try {
            agency = new Agency("Montpellier Comedie Place", "0605527749",
                    new Bank("SGBS"));

            iBankService.createAgency(bank, agency);
            logger.info(" OK >>> Agency created for bank " + bank.getName() + '\n');
        } catch (BankException | AgencyException | AccountException exception) {
            logger.severe(" <<<<   KO !!! Une erreur est survenue >>>>> \t");
            logger.severe(exception.getMessage());
        }
        return agency;
    }


}
