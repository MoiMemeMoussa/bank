package com.example.firstproject.services;

import com.example.firstproject.models.CompteDto;
import com.example.firstproject.models.OperationCompteDto;

import java.util.List;

public interface BankService {

    CompteDto creerCompte(CompteDto compteDto);

    List<CompteDto> obtenirTousLesComptes();

    CompteDto crediter(OperationCompteDto operationCompteDto);

    CompteDto debiter(OperationCompteDto operationCompteDto);

    CompteDto tranferer(String numeroCompteExpediteur, String numeroCompteDestinataire, Double montantTransfert);

}
