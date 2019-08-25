package sn.oneclic.bank.banksn.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Slf4j
@Getter
public class Customer {

    private int id;
    private String name;
    private String address;
    private int phone;

    private Bank bank;
    private ArrayList<Account> accountList = new ArrayList<>();

    protected Customer(int id, String name, String address, @NotNull int phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public void verify(Account account, Manager manager, String name, int phone) {
        if (account == null)
            throw new NullPointerException(" customer must have account !!! ");
        else if (name == null)
            throw new NullPointerException(" customer must have name  !!! ");
    }


}
