package sn.oneclic.bank.banksn.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import sn.oneclic.bank.banksn.exceptions.AgencyException;
import sn.oneclic.bank.banksn.exceptions.BankException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Setter
public class Agency {

    private static int id;
    private int phone;
    private String address;
    private Bank bank;

    private List<Manager> managerList = new ArrayList<>();  // we can can find lot of Manager in one agency

    public Agency(String address, int phone, Bank bank) throws BankException, AgencyException {
        verifyIfNull(bank, address);
        id++;
        this.bank = bank;
        this.address = address;
        this.phone = phone;
    }

    private void verifyIfNull(Bank bank, String address) throws BankException, AgencyException {
        if (bank == null)
            throw new BankException();
        else if (address == null)
            throw new AgencyException();

    }


}
