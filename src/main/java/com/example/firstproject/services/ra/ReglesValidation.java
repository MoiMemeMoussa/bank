package com.example.firstproject.services.ra;


import com.example.firstproject.exceptions.MontantInvalideException;
import com.example.firstproject.exceptions.OperationInvalideException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReglesValidation {

    private static final String FORMAT_INVALIDE = "Le montant de l'operation doit etre numerique";
    private static final String MONTANT_OPERATION_INVALIDE = "Le montant de l'operation doit etre superieur à 0";

    public void validerMontant(String montant) {
        double montantValide = validerMontantEstNumerique(montant);
        if (montantValide <= 0) {
            throw new MontantInvalideException(MONTANT_OPERATION_INVALIDE);
        }
    }

    private double validerMontantEstNumerique(String montant) {
        try {
            return Double.parseDouble(montant);
        } catch (NumberFormatException exception) {
            throw new OperationInvalideException(FORMAT_INVALIDE);
        }
    }
}
