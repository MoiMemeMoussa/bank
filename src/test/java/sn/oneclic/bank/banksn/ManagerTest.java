package sn.oneclic.bank.banksn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sn.oneclic.bank.banksn.exceptions.*;
import sn.oneclic.bank.banksn.model.Bank;
import sn.oneclic.bank.banksn.model.Manager;

class ManagerTest {

    private Manager manager = null;
    private Bank bank = null;

    @BeforeEach
    void setup() {
        try {
            bank = new Bank("BIS");
            bank.addAgency("Pikine", 335669686);
            manager = new Manager("Fatima", 774405986, bank, bank.getAgencyList().get(0));

        } catch (BankException | AgencyException | ManagerException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    void createManager() {

        try {
            bank.addManager(bank.getAgencyList().get(0), "Souleymane DIOP", 775402585);
        } catch (BankException | ManagerException exception) {
            exception.printStackTrace();
        }
        Assertions.assertEquals(1, bank.getManagerList().size());
        Assertions.assertEquals(manager.getBank().getName(), bank.getName());
        Assertions.assertEquals(manager.getAgency().getPhone(), bank.getAgencyList().get(0).getPhone());
    }

    @Test
    void test_affecting_same_account_two_times_to_manager() {
        try {
            bank.addManager(bank.getAgencyList().get(0), " BAdji", 774158595);
            bank.addCustomer(1, "Assane FALL", "Mbour", 774556585);

        } catch (BankException | CustomerException | ManagerException exception) {
            exception.printStackTrace();
        }

        bank.affectAccountToCustomer(bank.getCustomerList().get(0), "010285", 7500);

        try {
            bank.affectAccountToManager(bank.getAccountList().get(0), bank.getManagerList().get(0));

        } catch (AccountManagerException exception) {
            exception.printStackTrace();
        }
        Assertions.assertThrows(AccountManagerException.class,
                () -> bank.affectAccountToManager(bank.getAccountList().get(0), bank.getManagerList().get(0)));
    }
}
