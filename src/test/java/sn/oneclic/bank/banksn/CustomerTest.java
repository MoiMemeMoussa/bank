package sn.oneclic.bank.banksn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sn.oneclic.bank.banksn.exceptions.AccountException;
import sn.oneclic.bank.banksn.model.Account;
import sn.oneclic.bank.banksn.model.Agency;
import sn.oneclic.bank.banksn.model.Bank;
import sn.oneclic.bank.banksn.model.Customer;

class CustomerTest {

    private Bank bank;
    private Agency agency;

    @BeforeEach
    void setUp() {
        bank = new Bank(1, "CBEAO");
        agency = new Agency("SGBS Pikine  Rue 10", "Pikine Rue 10", bank);

    }

    @Test
    void test_exception_when_create_new_customer_with_null_account() {
        Assertions.assertThrows(NullPointerException.class, () -> new Customer("Moussa", "Pikiene",
                "1667198400225", null));
    }

    @Test
    void test_exception_when_create_new_customer_with_null_identityNumber() {
        Assertions.assertThrows(NullPointerException.class, () -> new Customer("Azou",
                "Pikine", null, new Account(agency, "010285", 15000)));
    }

    @Test
    void test_exception_when_create_new_customer_with_not_good_balance() {
        Assertions.assertThrows(AccountException.class, () -> new Customer("Azou",
                "Pikine", "1667198400225", new Account(agency, "010285", 7500)));
    }
}