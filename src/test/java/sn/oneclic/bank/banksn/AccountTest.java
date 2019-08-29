package sn.oneclic.bank.banksn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sn.oneclic.bank.banksn.exceptions.AccountException;
import sn.oneclic.bank.banksn.exceptions.AgencyException;
import sn.oneclic.bank.banksn.exceptions.BankException;
import sn.oneclic.bank.banksn.exceptions.CustomerException;
import sn.oneclic.bank.banksn.model.Account;
import sn.oneclic.bank.banksn.model.Agency;
import sn.oneclic.bank.banksn.model.Bank;
import sn.oneclic.bank.banksn.model.Customer;
import sn.oneclic.bank.banksn.services.IBankService;
import sn.oneclic.bank.banksn.servicesimpl.BankServiceImpl;

class AccountTest {
    private Bank bank = null;
    private Agency agency = null;
    private IBankService bankService = new BankServiceImpl();

    private Customer firstCustomer;
    private Customer secondCustomer;

    private Account sender;
    private Account recipient;


    @BeforeEach
    void setup() {
        try {
            bank = new Bank("BIS");
            agency = new Agency("Pikine", 336552545, bank);
            bank.getAgencyList().add(agency);

            firstCustomer = new Customer(1, "Yamli Diop ", "Dakar", 774025240, "1667198400225");
            secondCustomer = new Customer(2, "Dame SEYE", "Colmar", 774409453, "1667198100633");

            sender = new Account(bank, "010285010285010285010285", 12000, firstCustomer);
            recipient = new Account(bank, "180381180381180381180381", 17000, secondCustomer);


            bankService.createCustomer(bank, firstCustomer);
            bankService.createCustomer(bank, secondCustomer);

        } catch (BankException | AgencyException | CustomerException | AccountException exception) {
            exception.printStackTrace();
        }

    }

    @Test
    void creditAccount() {


        bankService.createCustomer(bank, firstCustomer);
        bankService.createCustomer(bank, secondCustomer);

        try {
            bankService.createAccount(bank, sender, firstCustomer);
            bankService.createAccount(bank, recipient, secondCustomer);
        } catch (AccountException exception) {
            exception.printStackTrace();
        }

        bankService.creditAccount(sender, 13000);
        bankService.creditAccount(recipient, 3000);

        Assertions.assertEquals(25000, bank.getAccountList().get(0).getBalance());
        Assertions.assertEquals(20000, bank.getAccountList().get(1).getBalance());
    }

    @Test
    void debitAccount() {


        try {
            bankService.createAccount(bank, sender, firstCustomer);
            bankService.createAccount(bank, recipient, secondCustomer);

            bankService.debitAccount(bank.getAccountList().get(0), 8000);
            bankService.debitAccount(bank.getAccountList().get(1), 2000);

        } catch (AccountException exception) {
            exception.printStackTrace();
        }

        Assertions.assertEquals(4000, bank.getAccountList().get(0).getBalance());
        Assertions.assertEquals(15000, bank.getAccountList().get(1).getBalance());

    }

    @Test
    void transferAccountToAccount() {

        bankService.createCustomer(bank, firstCustomer);
        bankService.createCustomer(bank, secondCustomer);

        try {
            bankService.createAccount(bank, sender, firstCustomer);
            bankService.createAccount(bank, recipient, secondCustomer);
            bankService.transfer(sender, recipient, 8000);
        } catch (AccountException accountException) {
            accountException.printStackTrace();
        }

        Assertions.assertEquals(4000, sender.getBalance());
        Assertions.assertEquals(25000, recipient.getBalance());

    }

    @Test
    void test_exception_when_debit_sum_less_than_balance() {

        Assertions.assertThrows(AccountException.class, () -> bankService.debitAccount(sender, 25000));

    }

    @Test
    void test_exception_when_transfer_sum_more_than_balance() {

        Assertions.assertThrows(AccountException.class, () -> bankService.transfer(sender, recipient, 58000));

    }

}
