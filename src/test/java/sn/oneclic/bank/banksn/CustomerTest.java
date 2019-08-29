package sn.oneclic.bank.banksn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sn.oneclic.bank.banksn.exceptions.AgencyException;
import sn.oneclic.bank.banksn.exceptions.BankException;
import sn.oneclic.bank.banksn.exceptions.CustomerException;
import sn.oneclic.bank.banksn.model.Agency;
import sn.oneclic.bank.banksn.model.Bank;
import sn.oneclic.bank.banksn.model.Customer;
import sn.oneclic.bank.banksn.services.IBankService;
import sn.oneclic.bank.banksn.servicesimpl.BankServiceImpl;

class CustomerTest {
    private Agency agency = null;
    private Bank bank = null;
    private IBankService iBankService = new BankServiceImpl();

    private Customer customer;

    @BeforeEach
    void setup() {
        try {
            bank = new Bank("BIS");
            agency = new Agency("Pikine", 336552545, bank);
            customer = new Customer(1, "Moussa", "Pikine", 774402141, "1667198400225");

        } catch (BankException | AgencyException | CustomerException bankExcpetion) {
            bankExcpetion.printStackTrace();
        }
    }

    @Test
    void createCustomer() {
        iBankService.createCustomer(bank, customer);
        Assertions.assertEquals(1, bank.getCustomerList().size());
    }


}
