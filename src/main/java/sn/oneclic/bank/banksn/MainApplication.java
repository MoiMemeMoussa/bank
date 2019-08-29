package sn.oneclic.bank.banksn;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import sn.oneclic.bank.banksn.exceptions.AccountException;
import sn.oneclic.bank.banksn.exceptions.BankException;
import sn.oneclic.bank.banksn.exceptions.CustomerException;
import sn.oneclic.bank.banksn.model.Account;
import sn.oneclic.bank.banksn.model.Bank;
import sn.oneclic.bank.banksn.model.Customer;
import sn.oneclic.bank.banksn.services.BankService;
import sn.oneclic.bank.banksn.services.IBankService;
import sn.oneclic.bank.banksn.servicesimpl.BankServiceImpl;

@SpringBootApplication
public class MainApplication {


    public static void main(String[] args) {

        BankUtils.welcomeMessage();

        Bank bank = BankUtils.createBank();

        BankService bankService = new BankService();
        IBankService iBankService = new BankServiceImpl();

        bankService.createAgency(bank);


        bankService.createManager(bank);

        Customer customer = bankService.createCustomerAndAccount(bank);

        Account sender = bank.getAccountList().get(0);

        sender = bankService.creditAccount(sender);

        try {
            sender = bankService.debitAccount(sender);


            Account recipient = new Account(bank, "120381120381120381120381",
                    12500, customer);

            iBankService.transfer(sender, recipient, 15000);

        } catch (BankException | AccountException | CustomerException exception) {
            exception.printStackTrace();
        }


//        bank.getAccountList().get(0).debitAccount();
//
//        Account sender = customer.getAccountList().get(0);
//
//        Account recipient = customer.getAccountList().get(1);
//
//        sender.transfert(recipient);

    }

}
