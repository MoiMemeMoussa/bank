package sn.oneclic.bank.banksn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sn.oneclic.bank.banksn.model.Account;
import sn.oneclic.bank.banksn.model.Agency;
import sn.oneclic.bank.banksn.model.Bank;
import sn.oneclic.bank.banksn.model.Customer;

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
        Assertions.assertThrows(NullPointerException.class, () -> new Account(null, "SN010285"));
    }

    @Test
    void openAccount() {
        Account accountExpected = new Account(agency, "104759822");
        Customer customer = new Customer("Fatou Diop", "Sicap", "01021985", accountExpected);
        accountExpected.setCustomer(customer);
        Assertions.assertEquals(accountExpected.getCustomer(), customer.findCustomerByAccountNumber("104759822"));
    }

}
