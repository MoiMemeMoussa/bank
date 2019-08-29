package sn.oneclic.bank.banksn.servicesimpl;

import sn.oneclic.bank.banksn.exceptions.AccountException;
import sn.oneclic.bank.banksn.model.*;
import sn.oneclic.bank.banksn.repository.BankRepository;
import sn.oneclic.bank.banksn.services.IBankService;

public class BankServiceImpl implements IBankService {

    private BankRepository bankRepository = new BankRepository();

    public Agency createAgency(Bank bank, Agency agency) {
        return bankRepository.save(bank, agency);
    }

    public Manager createManager(Bank bank, Manager manager) {
        return bankRepository.saveManager(bank, manager);
    }

    public Customer createCustomer(Bank bank, Customer customer) {
        return bankRepository.saveCustomer(bank, customer);
    }

    public void createAccount(Bank bank, Account account, Customer customer) throws AccountException {
        if (bank == null)
            throw new AccountException(" account is defined with a bank !!! ");
        if (customer == null)
            throw new AccountException(" account cannot exist without customer !!! ");


        bankRepository.createAccount(bank, account, customer);
    }

    public Account creditAccount(Account account, int sum) {
        return bankRepository.credit(account, sum);
    }

    public Account debitAccount(Account account, int sum) throws AccountException {
        if (sum > account.getBalance())
            throw new AccountException(" Cannot debit " + sum + " , your balance is " + account.getBalance());
        else
            return bankRepository.debit(account, sum);
    }

    public Account transfer(Account sender, Account recipient, int sum) throws AccountException {
        if (sum > sender.getBalance())
            throw new AccountException(" Your balance is less than " + sum);
        return bankRepository.transfer(sender, recipient, sum);
    }
}
