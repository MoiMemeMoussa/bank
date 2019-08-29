package sn.oneclic.bank.banksn.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import sn.oneclic.bank.banksn.exceptions.BankException;
import sn.oneclic.bank.banksn.exceptions.ManagerException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Setter
public class Manager {

    private static int id;
    private int phone;
    private String name;

    private Bank bank;
    private Agency agency;  // a manager is affected in one agency
    private List<Account> accountList = new ArrayList<>();

    public Manager(String name, int phone, Bank bank, Agency agency) throws BankException, ManagerException {
        verifyIfNull(bank, name);
        id++;
        this.name = name;
        this.phone = phone;
        this.bank = bank;
        this.agency = agency;
    }

    private void verifyIfNull(Bank bank, String name) throws BankException, ManagerException {
        if (bank == null)
            throw new BankException();
        else if (name == null)
            throw new ManagerException();

    }


}
