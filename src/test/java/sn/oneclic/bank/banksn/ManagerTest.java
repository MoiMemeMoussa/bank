package sn.oneclic.bank.banksn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sn.oneclic.bank.banksn.exceptions.*;
import sn.oneclic.bank.banksn.model.*;
import sn.oneclic.bank.banksn.services.IBankService;
import sn.oneclic.bank.banksn.services.IManagerService;
import sn.oneclic.bank.banksn.servicesimpl.BankServiceImpl;
import sn.oneclic.bank.banksn.servicesimpl.ManagerServiceImpl;

class ManagerTest {

    private Manager manager = null;
    private Bank bank = null;
    private Agency agency = null;
    private Customer customer;
    private Account account = null;

    private IBankService bankService = new BankServiceImpl();
    private IManagerService managerService = new ManagerServiceImpl();


    @BeforeEach
    void setup() {
        try {
            bank = new Bank("BIS");
            agency = new Agency("Pikine", "336552545", bank);
            bankService.createAgency(bank, agency);

            manager = new Manager("Fatima", 774405986, bank, bank.getAgencyList().get(0));

            customer = new Customer(1, "Moussa", "Pikine", 774402141, "252628");
            account = new Account(bank, "010285", 12500, customer);

        } catch (BankException | AgencyException | CustomerException | ManagerException | AccountException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    void createManager() {
        try {
            Manager manager = new Manager("Souleymane DIOP", 775902010, bank, agency);
            managerService.create(bank, manager);
        } catch (BankException | ManagerException exception) {
            exception.printStackTrace();
        }

        Assertions.assertEquals(1, bank.getManagerList().size());
        Assertions.assertEquals(manager.getBank().getName(), bank.getName());
        Assertions.assertEquals(manager.getAgency().getPhone(), bank.getAgencyList().get(0).getPhone());
    }


}
