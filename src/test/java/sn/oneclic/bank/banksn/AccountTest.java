package sn.oneclic.bank.banksn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sn.oneclic.bank.banksn.exceptions.AccountException;
import sn.oneclic.bank.banksn.model.Account;
import sn.oneclic.bank.banksn.model.Agency;
import sn.oneclic.bank.banksn.model.Bank;

class AccountTest {
    private Bank bank;
    private Agency agency;

    @BeforeEach
    void setUp() {
        bank = new Bank(1, "CBEAO");
        agency = new Agency("SGBS Pikine  Rue 10", "Pikine Rue 10", bank);
    }

    @Test
    void test_exception_when_create_new_account_and_agence_is_null() {
        Assertions.assertThrows(NullPointerException.class, () -> new Account(null, "SN010285", 10000));
    }

    @Test
    void test_exception_when_create_new_account_and_balance_less_than_10000() {
        Assertions.assertThrows(AccountException.class, () -> new Account(agency, "SN010285", 5800));
    }


}
