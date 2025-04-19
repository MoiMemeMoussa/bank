package com.example.firstproject.utils;

public class BankUtils {

    public static boolean isDouble(String solde) {
        try {
            Double.parseDouble(solde);
        } catch (NumberFormatException exception) {
            return false;
        }
        return true;
    }
}
