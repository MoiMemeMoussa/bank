package com.example.firstproject.services;


import com.example.firstproject.exceptions.RessourceAlreadyExistException;
import com.example.firstproject.exceptions.RessourceNotFoundException;
import com.example.firstproject.exceptions.RetraitImpossibleException;
import com.example.firstproject.models.CompteDto;
import com.example.firstproject.models.OperationCompteDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankService {

    private List<CompteDto> listeComptes = new ArrayList<>();

    private static final String NUMERO_COMPTE_EXISTE_DEJA = "Ce compte existe déja";
    private static final String NUMERO_COMPTE_EXISTE_PAS = "Ce compte n'existe pas";
    private static final String RETRAIT_IMPOSSIBLE = "Retrait impossible: votre solde est inférieure à ce montant";

    public CompteDto creerCompte(CompteDto compteDto) {
        if (compteExists(compteDto.getNumeroCompte())) {
            throw new RessourceAlreadyExistException(NUMERO_COMPTE_EXISTE_DEJA);
        }
        listeComptes.add(compteDto);
        return compteDto;
    }

    public CompteDto crediter(OperationCompteDto operationCompteDto) {

        CompteDto compteDto = listeComptes
                .stream()
                .filter(compteDto1 -> compteDto1.getNumeroCompte().equals(operationCompteDto.getNumeroCompte()))
                .findAny()
                .orElseThrow(() -> new RessourceNotFoundException(NUMERO_COMPTE_EXISTE_PAS));
        compteDto.setSolde(compteDto.getSolde() + operationCompteDto.getMontantOperation());
        return compteDto;
    }

    public CompteDto debiter(OperationCompteDto operationCompteDto) {
        CompteDto compteDto = listeComptes
                .stream()
                .filter(compteDto1 -> compteDto1.getNumeroCompte().equals(operationCompteDto.getNumeroCompte()))
                .findAny()
                .orElseThrow(() -> new RessourceNotFoundException(NUMERO_COMPTE_EXISTE_PAS));
        if (operationCompteDto.getMontantOperation() > compteDto.getSolde()) {
            throw new RetraitImpossibleException(RETRAIT_IMPOSSIBLE);
        }
        compteDto.setSolde(compteDto.getSolde() - operationCompteDto.getMontantOperation());
        return compteDto;
    }

    public void tranferer(String numeroCompteExpediteur, String numeroCompteDestinataire, Double montantTransfert) {
        OperationCompteDto operationDebit = new OperationCompteDto();
        operationDebit.setNumeroCompte(numeroCompteExpediteur);
        operationDebit.setMontantOperation(montantTransfert);

        // debiter expediteur
        debiter(operationDebit);

        OperationCompteDto operationCredit = new OperationCompteDto();
        operationCredit.setNumeroCompte(numeroCompteDestinataire);
        operationCredit.setMontantOperation(montantTransfert);

        // crediteur destinataire
        crediter(operationCredit);
    }

    private boolean compteExists(String numeroCompte) {
        return this.listeComptes
                .stream()
                .map(CompteDto::getNumeroCompte)
                .toList()
                .contains(numeroCompte);
    }

    public List<CompteDto> getListeComptes() {
        return this.listeComptes;
    }
}
