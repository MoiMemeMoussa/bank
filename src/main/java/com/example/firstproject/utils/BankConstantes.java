package com.example.firstproject.utils;

import java.time.LocalDate;
import java.time.ZoneId;

public class BankConstantes {

    public static final String URI = "clic";
    private static final String ZONE_ID_MONTREAL = "America/Montreal";

    private BankConstantes() {
    }

    public static LocalDate getDateDuJour() {
        int annee = LocalDate.now(ZoneId.of(ZONE_ID_MONTREAL)).getYear();
        int mois = LocalDate.now().getMonthValue();
        int jour = LocalDate.now().getDayOfMonth();
        return LocalDate.of(annee, mois, jour);
    }
}
