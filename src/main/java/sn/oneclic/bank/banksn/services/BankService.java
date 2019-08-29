package sn.oneclic.bank.banksn.services;

import sn.oneclic.bank.banksn.BankUtils;
import sn.oneclic.bank.banksn.exceptions.*;
import sn.oneclic.bank.banksn.model.*;
import sn.oneclic.bank.banksn.servicesimpl.BankServiceImpl;

import java.util.logging.Logger;

public class BankService {
    private static Logger logger = Logger.getLogger("Bank");
    private IBankService iBankService = new BankServiceImpl();

    public BankService() {

    }

    public Agency createAgency(Bank bank) {
        String address = BankUtils.doOperation(" Give address of agency ");
        int phone = Integer.parseInt(BankUtils.doOperation(" Give phone number of agency"));
        Agency agency = null;
        try {
            agency = new Agency(address, phone, bank);
            iBankService.createAgency(bank, agency);
            logger.info(" OK >>> Agency created for bank " + bank.getName() + '\n');
        } catch (BankException | AgencyException | AccountException exception) {
            logger.severe(" <<<<   KO !!! Une erreur est survenue >>>>> \t");
            logger.severe(exception.getMessage());
        }
        return agency;
    }


    public Manager createManager(Bank bank) {
        String name = BankUtils.doOperation(" Give name  of the manager ");
        int phone = Integer.parseInt(BankUtils.doOperation(" Give phone number of manager"));
        Agency agency = bank.getAgencyList().get(0);
        Manager manager = null;
        try {
            manager = new Manager(name, phone, bank, agency);
            iBankService.createManager(bank, manager);
            logger.info(" OK >>> Manager created for bank " + bank.getName());

        } catch (BankException | ManagerException exception) {
            logger.severe(" <<<<   KO !!! error happened >>>>> \t");
            logger.severe(exception.getMessage());
        }
        return manager;
    }


    public Customer createCustomerAndAccount(Bank bank) {
        String name = BankUtils.doOperation(" Give name  of the customer ");
        String address = BankUtils.doOperation(" Give address  of customer");
        String identityCardNumber = BankUtils.doOperation(" Give identity number ");
        int phone = Integer.parseInt(BankUtils.doOperation(" Give phone number of customer"));

        Customer customer = null;

        try {
            //customer = this.addCustomer(1, name, address, phone);
            customer = new Customer(1, name, address, phone, identityCardNumber);
            logger.info(" OK >>> customer created -- lets create account for the customer ");

            String accountNumber = BankUtils.doOperation(" Give account number ");
            Account account = new Account(bank, accountNumber, 10000, customer);

            iBankService.createCustomer(bank, customer);
            iBankService.createAccount(bank, account, customer);

            logger.info(" OK >>> Customer  = " + customer.getName() + " | Account Number = " + bank.getAccountList().get(0).getAccountNumber()
                    + " | Manager = " + bank.getManagerList().get(0).getName() + " | solde = " + bank.getAccountList().get(0).getBalance());

        } catch (BankException | CustomerException | AccountException customerException) {
            customerException.printStackTrace();
        }
        return customer;
    }


    public Account creditAccount(Account account) {
        int sum = Integer.parseInt(BankUtils.doOperation("Enter sum to credit "));
        account = iBankService.creditAccount(account, sum);
        logger.info(" OK >>> Account credited " + sum + "  >>> new balance = " + account.getBalance());
        return account;
    }

    public Account debitAccount(Account account) throws AccountException {
        int sum = Integer.parseInt(BankUtils.doOperation("Enter sum to debit "));
        if (sum > account.getBalance())
            throw new AccountException(" debit impossible, your balance is " + account.getBalance());
        else {
            account = iBankService.debitAccount(account, sum);
        }
        logger.info(" OK >>>debit of" + sum + "  on " + account.getAccountNumber() + " |  new balance = " + account.getBalance());
        return account;
    }

    public Account transfert(Account sender, Account recipient) throws AccountException {
        int sum = Integer.parseInt(BankUtils.doOperation("Enter sum to debit "));
        if (sum > sender.getBalance())
            throw new AccountException(" Operation Failed <<<<<  this sum is not available in your account !!!  ");
        else {
            sender = iBankService.transfer(sender, recipient, sum);
        }
        logger.info(" OK >>> Transfert of " + sum + "  >>> from = " + sender.getAccountNumber() + " to " + recipient.getAccountNumber());
        return sender;
    }


}
