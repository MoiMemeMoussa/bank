package sn.oneclic.bank.banksn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sn.oneclic.bank.banksn.model.Agency;
import sn.oneclic.bank.banksn.model.Bank;

class BankTest {

    @Test
    void test_exception_when_create_new_bank() {
        Assertions.assertThrows(NullPointerException.class, () -> new Bank(1, null));
    }

    @Test
    void addAgency() {
        Bank bankExpected = new Bank(3, "BIS");
        Agency firstAgency = new Agency("BIS Lamine Gueye", "", bankExpected);
        Agency secondAgency = new Agency("BIS Ponty", "", bankExpected);
        bankExpected.getListAgency().add(firstAgency);
        bankExpected.getListAgency().add(secondAgency);
        Assertions.assertEquals(bankExpected.getListAgency().size(), 2);
    }

    @Test
    void test_exception_when_adding_null_agency() {
        Assertions.assertThrows(NullPointerException.class, () -> new Bank(1, "Pikine").addAgency(null));
    }
}
