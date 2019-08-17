package sn.oneclic.bank.banksn.exceptions;

public class AccountException extends Exception {

    public AccountException() {
        super(" An account must be defined with 10.000 of balance minimum !!!");
    }
}
