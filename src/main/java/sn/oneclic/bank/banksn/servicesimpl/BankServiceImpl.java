package sn.oneclic.bank.banksn.servicesimpl;

import sn.oneclic.bank.banksn.model.Agency;
import sn.oneclic.bank.banksn.model.Bank;
import sn.oneclic.bank.banksn.model.Customer;
import sn.oneclic.bank.banksn.model.Manager;
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


}
