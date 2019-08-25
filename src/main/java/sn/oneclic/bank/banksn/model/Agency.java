package sn.oneclic.bank.banksn.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
public class Agency {

    private static int id;
    private String address;
    private int phone;
    private Bank bank;
    private List<Manager> managerList = new ArrayList<>();  // we can can find lot of Manager in one agency

    //protected pour imposer la creation de l'agence by class Bank
    protected Agency(String address, int phone, Bank bank) {
        verifyIfNull(address);
        id++;
        this.address = address;
        this.phone = phone;
        this.bank = bank;
    }

    private void verifyIfNull(String address) {
        if (address == null)
            throw new NullPointerException("address must be not null !!");

    }


}
