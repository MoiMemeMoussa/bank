package sn.oneclic.bank.banksn;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import sn.oneclic.bank.banksn.model.*;

@SpringBootApplication
public class MainApplication {


    public static void main(String[] args) {

        BankUtils.welcomeMessage();

        Bank bank = BankUtils.createBank();

        Agency agency = bank.createAgency();

        Manager manager = bank.createManager(agency);

        Customer customer = bank.createCustomer(manager);

        bank.getAccountList().get(0).creditAccount();

        bank.getAccountList().get(0).debitAccount();

        Account sender = customer.getAccountList().get(0);

        Account recipient = customer.getAccountList().get(1);

        sender.transfert(recipient);


    }


}
