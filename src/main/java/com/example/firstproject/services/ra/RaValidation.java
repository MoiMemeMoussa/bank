package com.example.firstproject.services.ra;


import com.example.firstproject.exceptions.IncorrectMontantException;
import com.example.firstproject.exceptions.IncorrectOperationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RaValidation {

    private static final String MONTANT_OPERATION_INCORRECT = "Le montant de l'operation doit etre superieur à 0";
    private static final String LE_FORMAT_EST_INCORRECT = "Le montant de l'operation doit etre numerique";

    private double validerFormatMontant(String montant) {
        try {
            return Double.parseDouble(montant);
        } catch (NumberFormatException exception) {
            throw new IncorrectOperationException(LE_FORMAT_EST_INCORRECT);
        }
    }

    public void validerMontant(String montant) {
        double montantValide = validerFormatMontant(montant);
        if (montantValide <= 0) {
            throw new IncorrectMontantException(MONTANT_OPERATION_INCORRECT);
        }
    }
}
