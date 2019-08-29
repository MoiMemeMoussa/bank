package sn.oneclic.bank.banksn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sn.oneclic.bank.banksn.exceptions.AccountException;
import sn.oneclic.bank.banksn.exceptions.AgencyException;
import sn.oneclic.bank.banksn.exceptions.BankException;
import sn.oneclic.bank.banksn.model.Agency;
import sn.oneclic.bank.banksn.model.Bank;
import sn.oneclic.bank.banksn.services.IBankService;
import sn.oneclic.bank.banksn.servicesimpl.BankServiceImpl;

class BankTest {
    private Bank bank = null;
    private Agency agency = null;
    private IBankService bankService = new BankServiceImpl();

    @BeforeEach
    void setup() {
        try {
            bank = new Bank("BIS");
            agency = new Agency("Pikine", 336552545, bank);
            bankService.createAgency(bank, agency);
        } catch (BankException | AccountException | AgencyException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    void create_bank_with_null_name() {
        Assertions.assertThrows(NullPointerException.class, (null));

    }


}
