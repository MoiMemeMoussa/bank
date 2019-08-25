package sn.oneclic.bank.banksn.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Getter
public class Manager {

    private static int id;
    private int phone;
    private String name;

    private Bank bank;
    private Agency agency;  // a manager is affected in one agency
    private List<Account> accountList;

    public Manager(String name, int phone, Bank bank, Agency agency) {
        verifyIfNull(bank, name);
        id++;
        this.name = name;
        this.phone = phone;
        this.bank = bank;
        this.agency = agency;
    }

    private void verifyIfNull(Bank bank, String name) {
        if (bank == null)
            throw new NullPointerException(" Manager must be in a bank !!");
        else if (name == null)
            throw new NullPointerException(" Manager must have a name!!");

    }


}
