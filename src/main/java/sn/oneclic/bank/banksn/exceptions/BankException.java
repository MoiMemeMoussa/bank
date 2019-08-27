package sn.oneclic.bank.banksn.exceptions;

public class BankException extends Exception {

    public BankException() {
        super(" Cannot create a bank without name !!  ");
    }
}
