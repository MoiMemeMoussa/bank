package sn.oneclic.bank.banksn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sn.oneclic.bank.banksn.model.Bank;
import sn.oneclic.bank.banksn.model.Manager;

class BankTest {

    @Test
    void defaultTest() {
        Assertions.assertTrue(true);
    }

    @Test
    void create_bank_without_name() {
        Assertions.assertThrows(NullPointerException.class, (null));
    }

    @Test
    void createAgency() {
        Bank bank = new Bank("SGBS");
        bank.addAgency("Pikine", 335669686);
        bank.addAgency("Ouakam", 336547788);
        Assertions.assertEquals(2, bank.getAgencyList().size());

    }

    @Test
    void createManager() {
        Bank bank = new Bank("BIS");
        bank.addAgency("Pikine", 335669686);

        bank.addManager(bank.getAgencyList().get(0), "Souleymane DIOP", 775402585);
        Manager manager1 = new Manager("Fatima", 774405986, bank, bank.getAgencyList().get(0));

        Assertions.assertEquals(1, bank.getManagerList().size());
        Assertions.assertEquals(manager1.getBank().getName(), bank.getName());
        Assertions.assertEquals(manager1.getAgency().getPhone(), bank.getAgencyList().get(0).getPhone());
    }


    @Test
    void create_new_agency_with_null_address() {
        Bank bank = new Bank("SGBS");
        Assertions.assertThrows(NullPointerException.class, () -> bank.addAgency(null, 336998978));
    }

    @Test
    void createAccount() {
        Bank bank = new Bank("BIS");
        bank.addAgency("Pikine", 335669686);
        bank.addManager(bank.getAgencyList().get(0), "Souleymane DIOP", 775402585);

    }

    @Test
    void creditAccount() {
        Bank bank = new Bank("BIS");
        bank.addAgency("Pikine", 335669686);
        bank.addCustomer(1, "Assane FALL", "Mbour", 774556585);
        bank.addCustomer(2, "TAFA FALL", "NDIASSANE", 774169685);

        bank.addAccount(bank.getCustomerList().get(0), "010285", 7500);
        bank.addAccount(bank.getCustomerList().get(1), "102589", 18000);

        //creditons le compte de Assane FALL et TAFA FALL
        bank.getAccountList().get(0).credit(3500);
        bank.getAccountList().get(1).credit(2000);

        Assertions.assertEquals(11000, bank.getAccountList().get(0).getBalance());
        Assertions.assertEquals(20000, bank.getAccountList().get(1).getBalance());
    }

    @Test
    void createCustomer() {
        Bank bank = new Bank("BIS");
        bank.addAgency("Pikine", 335669686);
        bank.addCustomer(1, "FAYE", "Dakar", 774112141);
        Assertions.assertEquals(1, bank.getCustomerList().size());

    }

}
