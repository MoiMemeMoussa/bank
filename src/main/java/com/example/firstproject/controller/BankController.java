package com.example.firstproject.controller;

import com.example.firstproject.models.CompteDto;
import com.example.firstproject.models.OperationCompteDto;
import com.example.firstproject.models.TransfertCompteDto;
import com.example.firstproject.services.BankService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.example.firstproject.entities.TypeOperation.CREDIT;
import static com.example.firstproject.entities.TypeOperation.DEBIT;
import static com.example.firstproject.utils.BankConstantes.URI;

@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping(URI)
@RestController
public class BankController {

    private static final String ENDPOINT_CREER_COMPTE = "/creer";
    private static final String ENDPOINT_CREDITER_COMPTE = "/crediter";
    private static final String ENDPOINT_DEBITER_COMPTE = "/debiter";
    private static final String ENDPOINT_OBTENIR_RELEVE_COMPTE = "/releves/{numeroCompte}";
    private static final String ENDPOINT_TRANSFERT = "/transferer";

    private final BankService bankService;

    @PostMapping(ENDPOINT_CREER_COMPTE)
    public ResponseEntity<CompteDto> creerCompte(@RequestBody @Valid CompteDto compteDto) {
        log.info(" opération - créer un compte ");
        CompteDto compte = bankService.creerCompte(compteDto);
        return new ResponseEntity<>(compte, HttpStatus.CREATED);
    }

    @PatchMapping(ENDPOINT_CREDITER_COMPTE)
    public ResponseEntity<CompteDto> crediter(@RequestBody OperationCompteDto operationCompteDto) {
        log.info(" opération - créditer un compte ");
        operationCompteDto.setTypeOperation(CREDIT);
        CompteDto compte = bankService.crediter(operationCompteDto);
        return new ResponseEntity<>(compte, HttpStatus.OK);
    }

    @PatchMapping(ENDPOINT_DEBITER_COMPTE)
    public ResponseEntity<CompteDto> debiter(@RequestBody OperationCompteDto operationCompte) {
        log.info(" opération - débiter un compte ");
        operationCompte.setTypeOperation(DEBIT);
        CompteDto resultat = bankService.debiter(operationCompte);
        return new ResponseEntity<>(resultat, HttpStatus.OK);
    }

    @PostMapping(ENDPOINT_TRANSFERT)
    public ResponseEntity<CompteDto> transferer(@RequestBody TransfertCompteDto transfert) {
        log.info(" opération - transfert compte à compte");
        CompteDto compteDto = bankService.tranferer(transfert.getNumeroCompteExpediteur(), transfert.getNumeroCompteDestinataire(), transfert.getMontantTransfert());
        return new ResponseEntity<>(compteDto, HttpStatus.OK);
    }

    @GetMapping(ENDPOINT_OBTENIR_RELEVE_COMPTE)
    public ResponseEntity<CompteDto> releveCompte(@PathVariable String numeroCompte) {
        log.info(" opération - obtenir relevé d\'un compte");
        CompteDto compte = bankService.obtenirReleveCompte(numeroCompte);
        return new ResponseEntity<>(compte, HttpStatus.OK);
    }
}
