package com.example.firstproject.utils;

import java.time.LocalDate;
import java.time.ZoneId;

public class BankConstantes {

    public static final String URI = "clic";
    public static final String ZONE_ID_MONTREAL = "America/Montreal";

    //endpoints
    public static final String ENDPOINT_CREER_COMPTE = "/creer";
    public static final String ENDPOINT_CREDITER_COMPTE = "/crediter";
    public static final String ENDPOINT_DEBITER_COMPTE = "/debiter";
    public static final String ENDPOINT_TRANSFERER = "/transferer";
    public static final String ENDPOINT_RELEVE_COMPTE = "/releves/{numeroCompte}";

    private BankConstantes() {
    }

    public static LocalDate getDateDuJour() {
        int annee = LocalDate.now(ZoneId.of(ZONE_ID_MONTREAL)).getYear();
        int mois = LocalDate.now().getMonthValue();
        int jour = LocalDate.now().getDayOfMonth();
        return LocalDate.of(annee, mois, jour);
    }
}
