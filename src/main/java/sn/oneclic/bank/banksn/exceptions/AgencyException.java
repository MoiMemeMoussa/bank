package sn.oneclic.bank.banksn.exceptions;

public class AgencyException extends Exception {

    public AgencyException() {
        super(" Cannot create agency without name !!  ");
    }
}
