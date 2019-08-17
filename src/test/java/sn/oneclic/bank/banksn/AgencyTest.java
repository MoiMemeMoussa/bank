package sn.oneclic.bank.banksn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sn.oneclic.bank.banksn.model.Agency;
import sn.oneclic.bank.banksn.model.Bank;

class AgencyTest {

    @Test
    void test_exception_when_create_new_agency_with_null_name() {
        Assertions.assertThrows(NullPointerException.class, () -> new Agency("SGBS Pikine", "Pikine", null));
        Bank bank = new Bank(1, "SGBS");
        Assertions.assertThrows(NullPointerException.class, () -> new Agency(null, "Pikine", bank));

    }

    @Test
    void test_exception_when_create_new_agency_with_null_linked_bank() {
        Assertions.assertThrows(NullPointerException.class, () -> new Agency("SGBS Pikine", "Pikine", null));
        Bank bank = new Bank(1, "SGBS");
    }

}
