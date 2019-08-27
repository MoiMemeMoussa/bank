package sn.oneclic.bank.banksn.services;

import sn.oneclic.bank.banksn.BankUtils;
import sn.oneclic.bank.banksn.exceptions.AgencyException;
import sn.oneclic.bank.banksn.exceptions.BankException;
import sn.oneclic.bank.banksn.model.Agency;
import sn.oneclic.bank.banksn.model.Bank;

import java.util.logging.Logger;

public class BankService {
    private static Logger logger = Logger.getLogger("Bank");

    public BankService() {

    }

    public Agency createAgency(Bank bank) {
        String address = BankUtils.doOperation(" Give address of agency ");
        int phone = Integer.parseInt(BankUtils.doOperation(" Give phone number of agency"));
        Agency agency = null;
        try {
            agency = bank.addAgency(address, phone);
            logger.info(" OK >>> Agency created for bank " + bank.getName() + '\n');
        } catch (BankException | AgencyException exception) {
            logger.severe(" <<<<   KO !!! Une erreur est survenue >>>>> \t");
            logger.severe(exception.getMessage());
        }
        return agency;
    }


}
