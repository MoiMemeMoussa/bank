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
import sn.oneclic.bank.banksn.services.IAccountService;
import sn.oneclic.bank.banksn.services.IBankService;
import sn.oneclic.bank.banksn.servicesimpl.AccountServiceImpl;
import sn.oneclic.bank.banksn.servicesimpl.BankServiceImpl;

class AccountTest {

    private Bank bank = null;
    private Agency agency = null;

    private Customer firstCustomer = null;
    private Customer secondCustomer = null;

    private Account sender;
    private Account recipient;

    private IBankService bankService = new BankServiceImpl();
    private IAccountService accountService = new AccountServiceImpl();

    @BeforeEach
    void setup() {
        try {
            bank = new Bank("BIS");
            agency = new Agency("Pikine", "336552545", bank);
            bank.getAgencyList().add(agency);

            firstCustomer = new Customer(1, "Yamli Diop ", "Dakar",
                    "07705527749", "180381");
            secondCustomer = new Customer(2, "Dame SEYE", "Colmar",
                    "07755528899", "120384");

            sender = new Account(bank, "240285", 12000, firstCustomer);
            recipient = new Account(bank, "120390", 17000, secondCustomer);

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
            accountService.createAccount(bank, sender, firstCustomer);
            accountService.createAccount(bank, recipient, secondCustomer);
        } catch (AccountException exception) {
            exception.printStackTrace();
        }
        accountService.creditAccount(sender, 13000);
        accountService.creditAccount(recipient, 3000);

        Assertions.assertEquals(25000, bank.getAccountList().get(0).getBalance());
        Assertions.assertEquals(20000, bank.getAccountList().get(1).getBalance());
    }

    @Test
    void debitAccount() {
        try {
            accountService.createAccount(bank, sender, firstCustomer);
            accountService.createAccount(bank, recipient, secondCustomer);

            accountService.debitAccount(bank.getAccountList().get(0), 8000);
            accountService.debitAccount(bank.getAccountList().get(1), 2000);

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
            accountService.createAccount(bank, sender, firstCustomer);
            accountService.createAccount(bank, recipient, secondCustomer);
            accountService.transfer(sender, recipient, 8000);
        } catch (AccountException accountException) {
            accountException.printStackTrace();
        }

        Assertions.assertEquals(4000, sender.getBalance());
        Assertions.assertEquals(25000, recipient.getBalance());

    }

    @Test
    void test_exception_when_debit_sum_less_than_balance() {

        Assertions.assertThrows(AccountException.class, () -> accountService.debitAccount(sender, 25000));

    }

    @Test
    void test_exception_when_transfer_sum_more_than_balance() {

        Assertions.assertThrows(AccountException.class, () -> accountService.transfer(sender, recipient, 58000));

    }

}
