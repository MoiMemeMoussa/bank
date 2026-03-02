package com.example.firstproject.services;

import com.example.firstproject.models.CompteDto;
import com.example.firstproject.models.OperationCompteDto;

public interface BankService {

    CompteDto creerCompte(CompteDto compteDto);

    CompteDto tranferer(String numeroCompteExpediteur, String numeroCompteDestinataire, Double montantTransfert);

    CompteDto crediter(OperationCompteDto operationCompteDto);
    CompteDto debiter (OperationCompteDto operationCompteDto);
   // CompteDto crediterOuDebiter(OperationCompteDto operationCompteDto);
}
