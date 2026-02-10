package com.example.firstproject.controller;

import com.example.firstproject.entities.TypeOperation;
import com.example.firstproject.models.CompteDto;
import com.example.firstproject.models.OperationCompteDto;
import com.example.firstproject.models.TransfertCompteDto;
import com.example.firstproject.services.BankServiceImpl;
import com.example.firstproject.utils.BankConstantes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping(BankConstantes.URI)
@RestController
public class BankController {

    private static final String ENDPOINT_CREER_COMPTE = "/creer";
    private static final String ENDPOINT_CREDITER_COMPTE = "/crediter";
    private static final String ENDPOINT_DEBITER_COMPTE = "/debiter";
    private static final String ENDPOINT_OBTENIR_RELEVE_COMPTE = "/releves/{numeroCompte}";
    private static final String ENDPOINT_TRANSFERT = "/transferer";

    private final BankServiceImpl bankService;

    @PostMapping(ENDPOINT_CREER_COMPTE)
    public ResponseEntity<CompteDto> creerCompte(@RequestBody @Valid CompteDto compte) {
        log.info(" start - creation du compte ");
        CompteDto resultat = bankService.creerCompte(compte);
        return new ResponseEntity<>(resultat, HttpStatus.CREATED);
    }

    @PatchMapping(ENDPOINT_CREDITER_COMPTE)
    public ResponseEntity<CompteDto> crediter(@RequestBody OperationCompteDto operationCompteDto) {
        log.info(" operation - crediter ");
        operationCompteDto.setTypeOperation(TypeOperation.CREDIT);
        CompteDto resultat = bankService.crediterOuDebiter(operationCompteDto);
        return new ResponseEntity<>(resultat, HttpStatus.OK);
    }

    @PatchMapping(ENDPOINT_DEBITER_COMPTE)
    public ResponseEntity<CompteDto> debiter(@RequestBody OperationCompteDto operationCompte) {
        log.info(" start - debiter ");
        operationCompte.setTypeOperation(TypeOperation.DEBIT);
        CompteDto resultat = bankService.crediterOuDebiter(operationCompte);
        return new ResponseEntity<>(resultat, HttpStatus.OK);
    }

    @PostMapping(ENDPOINT_TRANSFERT)
    public ResponseEntity<CompteDto> transferer(@RequestBody TransfertCompteDto transfert) {
        CompteDto compteDto = bankService.tranferer(transfert.getNumeroCompteExpediteur(), transfert.getNumeroCompteDestinataire(), transfert.getMontantTransfert());
        return new ResponseEntity<>(compteDto, HttpStatus.OK);
    }

    @GetMapping(ENDPOINT_OBTENIR_RELEVE_COMPTE)
    public ResponseEntity<CompteDto> releveCompte(@PathVariable String numeroCompte) {
        CompteDto reponse = bankService.obtenirReleveCompte(numeroCompte);
        return new ResponseEntity<>(reponse, HttpStatus.OK);
    }
}
