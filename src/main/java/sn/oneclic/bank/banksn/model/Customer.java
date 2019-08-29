package sn.oneclic.bank.banksn.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import sn.oneclic.bank.banksn.exceptions.CustomerException;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Slf4j
@Getter
@Setter
public class Customer {

    private static final int IDENTITY_CARD_NUMBER_CHARACTER = 6;
    private int id;
    private String name;
    private String address;
    private int phone;
    private String identityCard;
    private Bank bank;
    private ArrayList<Account> accountList = new ArrayList<>();

    public Customer(int id, String name, String address, @NotNull int phone, String identityCard)
            throws CustomerException {
        verifyIfNull(name, address, identityCard);
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.identityCard = identityCard;
    }


    private void verifyIfNull(String name, String address, String identityCard) throws CustomerException {
        if (identityCard == null) {
            throw new CustomerException(" Customer Null => cannot create customer withour identity number !!");
        } else if (identityCard.length() != IDENTITY_CARD_NUMBER_CHARACTER) {
            throw new CustomerException(" Customer Format => identity number must have " + IDENTITY_CARD_NUMBER_CHARACTER + " caracters");
        } else if (address == null) {
            throw new CustomerException(" Customer Null => customer must have address");

        }
    }


}
