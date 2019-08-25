package sn.oneclic.bank.banksn.exceptions;

public class AccountManagerException extends Exception {

    public AccountManagerException() {
        super(" Cannot affect same account to a Manager 2 times !!! ");
    }
}
