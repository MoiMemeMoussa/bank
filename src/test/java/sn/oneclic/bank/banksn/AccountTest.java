package sn.oneclic.bank.banksn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sn.oneclic.bank.banksn.exceptions.AgencyException;
import sn.oneclic.bank.banksn.exceptions.BankException;
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

        bank.addCustomer(1, "Assane FALL", "Mbour", 774556585);
        bank.addCustomer(2, "TAFA FALL", "NDIASSANE", 774169685);

        bank.affectAccountToCustomer(bank.getCustomerList().get(0), "010285", 7500);
        bank.affectAccountToCustomer(bank.getCustomerList().get(1), "102589", 18000);

        bank.getAccountList().get(0).credit(3500);
        bank.getAccountList().get(1).credit(2000);

        Assertions.assertEquals(11000, bank.getAccountList().get(0).getBalance());
        Assertions.assertEquals(20000, bank.getAccountList().get(1).getBalance());
    }

    @Test
    void debitAccount() {

        bank.addCustomer(1, "Papa Demba SEMBENE", " Bargny", 776998959);
        bank.addCustomer(2, "Rokhaya CISSE", "PARCELLES", 765412141);

        bank.affectAccountToCustomer(bank.getCustomerList().get(0), "01051985", 1200);
        bank.affectAccountToCustomer(bank.getCustomerList().get(1), "025082014", 4000);

        bank.getAccountList().get(0).debit(600);
        bank.getAccountList().get(1).debit(2000);

        Assertions.assertEquals(600, bank.getAccountList().get(0).getBalance());
        Assertions.assertEquals(2000, bank.getAccountList().get(1).getBalance());

    }

    @Test
    void transferAccountToAccount() {
        bank.addCustomer(1, "Claude Bento", " Montpellier", 605527749);
        bank.addCustomer(2, "Rahim Nouraly", "Occitanie", 741525669);

        bank.affectAccountToCustomer(bank.getCustomerList().get(0), "01051985", 1200);
        bank.affectAccountToCustomer(bank.getCustomerList().get(1), "025082014", 15000);

        // TODO: it might not be possible to transfer more than the balance of account
        // TODO : raise an exception if mountant to transfer is greater than sold

        //transfert of 1000 from account 1 to account 2
        bank.getAccountList().get(0).transfer(
                bank.getAccountList().get(1), 1200);
        Assertions.assertEquals(0, bank.getAccountList().get(0).getBalance());
        Assertions.assertEquals(16200, bank.getAccountList().get(1).getBalance());


    }

}
