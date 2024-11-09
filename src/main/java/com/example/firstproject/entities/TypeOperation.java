package com.example.firstproject.entities;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TypeOperation {
    CREDIT("CREDIT"),
    DEBIT("DEBIT");

    private String valeur;

    TypeOperation(String valeur) {
        this.valeur = valeur;
    }

    public static boolean isInEnum(String cle) {

        return Arrays.stream(TypeOperation.values())
                .anyMatch(typeOperation -> typeOperation.getValeur().equals(cle)
                );
    }

    public static String getValeur(String cle) {
        boolean isIn = isInEnum(cle);
        if (!isIn) {
            throw new IllegalArgumentException("Valeur non autorisee pour ce champs");
        }
        return TypeOperation.valueOf(cle).getValeur();
    }

}
