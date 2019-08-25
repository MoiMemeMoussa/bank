package sn.oneclic.bank.banksn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sn.oneclic.bank.banksn.exceptions.AgencyException;
import sn.oneclic.bank.banksn.exceptions.BankException;
import sn.oneclic.bank.banksn.model.Bank;


class AgencyTest {

    private Bank bank = null;

    @BeforeEach
    void setup() {
        try {
            bank = new Bank("BIS");
        } catch (BankException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    void createAgency() {
        System.out.println("bank = " + bank);
        try {
            bank.addAgency("Pikine", 335669686);
            bank.addAgency("Ouakam", 336547788);
        } catch (AgencyException | BankException agencyException) {
            agencyException.printStackTrace();
        }
        Assertions.assertEquals(2, bank.getAgencyList().size());
    }

    @Test
    void test_exception_when_creating_agency_with_null_address() {
        Assertions.assertThrows(AgencyException.class, () -> bank.addAgency(null, 336998978));
    }
}
