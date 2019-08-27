package sn.oneclic.bank.banksn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sn.oneclic.bank.banksn.exceptions.AccountException;
import sn.oneclic.bank.banksn.exceptions.AgencyException;
import sn.oneclic.bank.banksn.exceptions.BankException;
import sn.oneclic.bank.banksn.exceptions.CustomerException;
import sn.oneclic.bank.banksn.model.Account;
import sn.oneclic.bank.banksn.model.Bank;

class AccountTest {
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
    void creditAccount() {

        try {
            bank.addCustomer(1, "Assane FALL", "Mbour", 774556585);
            bank.addCustomer(2, "TAFA FALL", "NDIASSANE", 774169685);
        } catch (CustomerException customerException) {
            customerException.printStackTrace();
        }

        bank.affectAccountToCustomer(bank.getCustomerList().get(0), "010285", 7500);
        bank.affectAccountToCustomer(bank.getCustomerList().get(1), "102589", 18000);

        bank.getAccountList().get(0).credit(3500);
        bank.getAccountList().get(1).credit(2000);

        Assertions.assertEquals(11000, bank.getAccountList().get(0).getBalance());
        Assertions.assertEquals(20000, bank.getAccountList().get(1).getBalance());
    }

    @Test
    void debitAccount() {

        try {
            bank.addCustomer(1, "Papa Demba SEMBENE", " Bargny", 776998959);
            bank.addCustomer(2, "Rokhaya CISSE", "PARCELLES", 765412141);
        } catch (CustomerException customerException) {
            customerException.printStackTrace();
        }

        bank.affectAccountToCustomer(bank.getCustomerList().get(0), "01051985", 1200);
        bank.affectAccountToCustomer(bank.getCustomerList().get(1), "025082014", 4000);

        try {
            bank.getAccountList().get(0).debit(600);
            bank.getAccountList().get(1).debit(2000);
        } catch (AccountException accountException) {
            accountException.printStackTrace();
        }

        Assertions.assertEquals(600, bank.getAccountList().get(0).getBalance());
        Assertions.assertEquals(2000, bank.getAccountList().get(1).getBalance());

    }

    @Test
    void transferAccountToAccount() {
        try {
            bank.addCustomer(1, "Claude Bento", " Montpellier", 605527749);
            bank.addCustomer(2, "Rahim Nouraly", "Occitanie", 741525669);
        } catch (CustomerException customerException) {
            customerException.printStackTrace();
        }

        bank.affectAccountToCustomer(bank.getCustomerList().get(0), "01051985", 1200);
        bank.affectAccountToCustomer(bank.getCustomerList().get(1), "025082014", 15000);
        Account sender = bank.getAccountList().get(0);
        Account recipient = bank.getAccountList().get(1);
        try {
            sender.transfer(recipient, 1200);
        } catch (AccountException accountException) {
            accountException.printStackTrace();
        }
        Assertions.assertEquals(0, sender.getBalance());
        Assertions.assertEquals(16200, recipient.getBalance());

    }

    @Test
    void test_exception_when_debit_sum_less_than_balance() {
        try {
            bank.addCustomer(1, "Claude Bento", " Montpellier", 605527749);
            bank.addCustomer(2, "Rahim Nouraly", "Occitanie", 741525669);
        } catch (CustomerException customerException) {
            customerException.printStackTrace();
        }
        bank.affectAccountToCustomer(bank.getCustomerList().get(0), "01051985", 1000);
        Assertions.assertThrows(AccountException.class, () -> bank.getAccountList().get(0).debit(2500));

    }

    @Test
    void test_exception_when_transfer_sum_less_than_balance() {
        try {
            bank.addCustomer(1, "Claude Bento", " Montpellier", 605527749);
            bank.addCustomer(2, "Rahim Nouraly", "Occitanie", 741525669);
        } catch (CustomerException customerException) {
            customerException.printStackTrace();
        }

        bank.affectAccountToCustomer(bank.getCustomerList().get(0), "01051985", 1200);
        bank.affectAccountToCustomer(bank.getCustomerList().get(1), "025082014", 15000);
        Account sender = bank.getAccountList().get(0);
        Account recipient = bank.getAccountList().get(1);
        Assertions.assertThrows(AccountException.class, () -> sender.transfer(recipient, 14000));

    }

}
