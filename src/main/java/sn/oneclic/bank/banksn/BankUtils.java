package sn.oneclic.bank.banksn;

import sn.oneclic.bank.banksn.exceptions.BankException;
import sn.oneclic.bank.banksn.model.Bank;

import java.util.Scanner;
import java.util.logging.Logger;

public class BankUtils {
    private static Logger logger = Logger.getLogger("MainApplication");

    public static void welcomeMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("============================================================== \n");
        stringBuilder.append(" WELCOME TO OUR APPLICATION ");
        stringBuilder.append("\n");
        stringBuilder.append("==============================================================");

        System.out.println(stringBuilder.toString());
    }

    public static String doOperation(String message) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(message);
        System.out.println(stringBuilder.toString());
        return readLine();

    }

    public static String readLine() {
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }

    public static Bank createBank() {
        String bankName = doOperation("1- Enter tha name of your bank");
        Bank bank = null;
        try {
            bank = new Bank(bankName);
            logger.info("  OK >>> bank " + bankName + " created ");
        } catch (BankException bankException) {
            logger.severe(" <<< KO !!! Une erreur est survenue >>>>> \t");
            logger.severe(bankException.getMessage());
        }
        return bank;
    }

    public static int doTransfert() {
        return Integer.parseInt(BankUtils.doOperation(" Enter sum to transfert to "));

    }


}
