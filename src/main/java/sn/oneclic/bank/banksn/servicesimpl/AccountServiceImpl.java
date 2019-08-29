package sn.oneclic.bank.banksn.servicesimpl;

import sn.oneclic.bank.banksn.exceptions.AccountException;
import sn.oneclic.bank.banksn.model.Account;
import sn.oneclic.bank.banksn.model.Bank;
import sn.oneclic.bank.banksn.model.Customer;
import sn.oneclic.bank.banksn.repository.AccountRepository;
import sn.oneclic.bank.banksn.services.IAccountService;

public class AccountServiceImpl implements IAccountService {

    private AccountRepository accountRepository = new AccountRepository();

    public void createAccount(Bank bank, Account account, Customer customer) throws AccountException {
        if (bank == null)
            throw new AccountException(" account is defined with a bank !!! ");
        if (customer == null)
            throw new AccountException(" account cannot exist without customer !!! ");


        accountRepository.createAccount(bank, account, customer);
    }

    public Account creditAccount(Account account, int sum) {
        return accountRepository.credit(account, sum);
    }

    public Account debitAccount(Account account, int sum) throws AccountException {
        if (sum > account.getBalance())
            throw new AccountException(" Cannot debit " + sum + " , your balance is " + account.getBalance());
        else
            return accountRepository.debit(account, sum);
    }

    public Account transfer(Account sender, Account recipient, int sum) throws AccountException {
        if (sum > sender.getBalance())
            throw new AccountException(" Your balance is less than " + sum);
        return accountRepository.transfer(sender, recipient, sum);
    }

}
