package sn.oneclic.bank.banksn.business;

import lombok.extern.slf4j.Slf4j;
import sn.oneclic.bank.banksn.exceptions.AccountException;
import sn.oneclic.bank.banksn.exceptions.AgencyException;
import sn.oneclic.bank.banksn.exceptions.BankException;
import sn.oneclic.bank.banksn.model.Agency;
import sn.oneclic.bank.banksn.model.Bank;
import sn.oneclic.bank.banksn.services.IBankService;
import sn.oneclic.bank.banksn.servicesimpl.BankServiceImpl;

@Slf4j
public class BankBusiness {
    private IBankService iBankService = new BankServiceImpl();

    public BankBusiness() {

    }

    public Agency createAgency(Bank bank) {
        Agency agency = null;
        try {
            agency = new Agency("Montpellier Comedie Place", "0605527749",
                    new Bank("SGBS"));

            iBankService.createAgency(bank, agency);
            log.info(" OK >>> Agency created for bank " + bank.getName() + '\n');
        } catch (BankException | AgencyException | AccountException exception) {
            log.error(" <<<<   KO !!! Une erreur est survenue >>>>> \t");
            log.error(exception.getMessage());
        }
        return agency;
    }


}
