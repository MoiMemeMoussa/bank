package sn.oneclic.bank.banksn;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import sn.oneclic.bank.banksn.business.AccountBusiness;
import sn.oneclic.bank.banksn.business.BankBusiness;
import sn.oneclic.bank.banksn.business.ManagerBusiness;
import sn.oneclic.bank.banksn.exceptions.AccountException;
import sn.oneclic.bank.banksn.exceptions.BankException;
import sn.oneclic.bank.banksn.exceptions.CustomerException;
import sn.oneclic.bank.banksn.model.Account;
import sn.oneclic.bank.banksn.model.Bank;
import sn.oneclic.bank.banksn.model.Customer;
import sn.oneclic.bank.banksn.utils.BankUtils;

@SpringBootApplication
@Component
public class MainApplication {

    public static void main(String[] args) {

        BankBusiness bankBusiness = new BankBusiness();
        ManagerBusiness managerBusiness = new ManagerBusiness();
        AccountBusiness accountBusiness = new AccountBusiness();

        BankUtils.welcomeMessage();

        Bank bank = BankUtils.createBank();

        bankBusiness.createAgency(bank);

        managerBusiness.createManager(bank);

        Customer customer = accountBusiness.createCustomerAndAccount(bank);

        Account sender = bank.getAccountList().get(0);

        sender = accountBusiness.creditAccount(sender);

        try {
            sender = accountBusiness.debitAccount(sender);

            Account recipient = new Account(bank, "180318", 12500, customer);

            accountBusiness.transfer(sender, recipient, 5000);

        } catch (BankException | AccountException | CustomerException exception) {
            exception.printStackTrace();
        }
    }


}
