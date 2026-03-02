package com.example.firstproject.services;

import com.example.firstproject.models.CompteDto;
import com.example.firstproject.models.OperationCompteDto;

public interface IBankService {

    CompteDto creerCompte(CompteDto compteDto);

    CompteDto tranferer(String numeroCompteExpediteur, String numeroCompteDestinataire, Double montantTransfert);

    CompteDto crediter(OperationCompteDto operationCompteDto);

    CompteDto debiter(OperationCompteDto operationCompteDto);
}
