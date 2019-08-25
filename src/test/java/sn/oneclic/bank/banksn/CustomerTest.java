package sn.oneclic.bank.banksn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sn.oneclic.bank.banksn.exceptions.AccountManagerException;
import sn.oneclic.bank.banksn.exceptions.AgencyException;
import sn.oneclic.bank.banksn.exceptions.BankException;
import sn.oneclic.bank.banksn.model.Bank;

class CustomerTest {
    private Bank bank = null;

    @BeforeEach
    void setup() {
        try {
            bank = new Bank("BIS");
            bank.addAgency("Pikine", 335669686);
        } catch (BankException | AgencyException bankExcpetion) {
            bankExcpetion.printStackTrace();
        }
    }

    @Test
    void createCustomer() {
        bank.addCustomer(1, "FAYE", "Dakar", 774112141);
        Assertions.assertEquals(1, bank.getCustomerList().size());

    }

    @Test
    void findCustomerByAccountNumber() {
        bank.addManager(bank.getAgencyList().get(0), " BAdji", 774158595);

        bank.addCustomer(1, "Assane FALL", "Mbour", 774556585);
        bank.affectAccountToCustomer(bank.getCustomerList().get(0), "010285", 7500);
        try {
            bank.affectAccountToManager(bank.getAccountList().get(0), bank.getManagerList().get(0));
        } catch (AccountManagerException exception) {
            exception.printStackTrace();
        }
        System.out.println(bank.displayCustomerInformationUserAccountNumber("010285"));
    }

}
