package sn.oneclic.bank.banksn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sn.oneclic.bank.banksn.model.Bank;

class BankTest {

    @Test
    void test_exception_when_create_new_bank() {
        Assertions.assertThrows(NullPointerException.class, () -> new Bank(1, null));
    }
}
