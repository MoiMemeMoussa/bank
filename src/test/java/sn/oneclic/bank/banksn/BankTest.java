package sn.oneclic.bank.banksn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sn.oneclic.bank.banksn.exceptions.AgencyException;
import sn.oneclic.bank.banksn.exceptions.BankException;
import sn.oneclic.bank.banksn.model.Bank;

class BankTest {
    private Bank bank = null;

    @BeforeEach
    void setup() {
        try {
            bank = new Bank("BIS");
            bank.addAgency("Pikine", 335669686);
        } catch (BankException | AgencyException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    void create_bank_with_null_name() {
        Assertions.assertThrows(NullPointerException.class, (null));

    }


}
