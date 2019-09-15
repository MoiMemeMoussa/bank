package sn.oneclic.bank.banksn.business;

import sn.oneclic.bank.banksn.exceptions.AccountException;
import sn.oneclic.bank.banksn.exceptions.BankException;
import sn.oneclic.bank.banksn.exceptions.CustomerException;
import sn.oneclic.bank.banksn.model.Account;
import sn.oneclic.bank.banksn.model.Bank;
import sn.oneclic.bank.banksn.model.Customer;
import sn.oneclic.bank.banksn.model.Manager;
import sn.oneclic.bank.banksn.services.IAccountService;
import sn.oneclic.bank.banksn.services.IBankService;
import sn.oneclic.bank.banksn.services.IManagerService;
import sn.oneclic.bank.banksn.servicesimpl.AccountServiceImpl;
import sn.oneclic.bank.banksn.servicesimpl.BankServiceImpl;
import sn.oneclic.bank.banksn.servicesimpl.ManagerServiceImpl;
import sn.oneclic.bank.banksn.utils.BankUtils;

import java.util.List;
import java.util.logging.Logger;

public class AccountBusiness {
    private static Logger logger = Logger.getLogger("Bank");
    private IBankService iBankService = new BankServiceImpl();
    private IAccountService iAccountService = new AccountServiceImpl();
    private IManagerService iManagerService = new ManagerServiceImpl();

    public AccountBusiness() {

    }

    public Customer createCustomerAndAccount(Bank bank) {

        Customer customer = null;

        try {
            customer = new Customer(1, "Claude Bento", "Montpellier",
                    "06602335589", "010285");

            logger.info(" OK >>> customer created -- lets create account for the customer ");

            //String accountNumber = BankUtils.doOperation(" Give account number ");
            Account account = new Account(bank, "CPT001", 10000, customer);
            List<Manager> listManager = iManagerService.findAllManager(bank);

            iBankService.createCustomer(bank, customer);
            iAccountService.createAccount(bank, account, customer);
            iManagerService.affectManagerToAccount(listManager.get(0), account);

            logger.info(" OK >>> Customer  = " + customer.getName() + " | Account Number = " + bank.getAccountList().get(0).getAccountNumber()
                    + " | Manager = " + bank.getManagerList().get(0).getName() + " | solde = " + bank.getAccountList().get(0).getBalance());

        } catch (BankException | CustomerException | AccountException customerException) {
            customerException.printStackTrace();
        }
        return customer;
    }

    public Account creditAccount(Account account) {
        int sum = Integer.parseInt(BankUtils.doOperation("Enter sum to credit "));
        account = iAccountService.creditAccount(account, sum);
        logger.info(" OK >>> Account credited " + sum + "  >>> new balance = " + account.getBalance());
        return account;
    }

    public Account debitAccount(Account account) throws AccountException {
        int sum = Integer.parseInt(BankUtils.doOperation("Enter sum to debit "));
        if (sum > account.getBalance())
            throw new AccountException(" debit impossible, your balance is " + account.getBalance());
        else {
            account = iAccountService.debitAccount(account, sum);
        }
        logger.info(" OK >>> debit of " + sum + "  on " + account.getAccountNumber() + " |  new balance = " + account.getBalance());
        return account;
    }


    public Account transfer(Account sender, Account recipient, int sum) throws AccountException {
        if (sum > sender.getBalance())
            throw new AccountException(" Operation Failed <<<<<  this sum is not available in your account !!!  ");
        else {
            sender = iAccountService.transfer(sender, recipient, sum);
        }
        logger.info(" Transfert OK  >>> : Sender =  " + sender.getAccountNumber() + " , Sum =  " + sum + " , Recipient =  " + recipient.getAccountNumber());
        logger.info(" New balance >>>  " + sender.getAccountNumber() + " : " + (sender.getBalance()) + " , Recipient =  " + recipient.getAccountNumber() + " : " + (sender.getBalance() + sum));

        return sender;
    }


}
