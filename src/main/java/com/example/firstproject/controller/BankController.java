package com.example.firstproject.controller;


import com.example.firstproject.entities.CompteEntity;
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
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping(BankConstantes.URI)
@RestController
public class BankController {

    private final BankServiceImpl bankServiceImpl;

    @PostMapping("/creer")
    public ResponseEntity<CompteDto> creerCompte(@RequestBody @Valid CompteDto compteDto) {
        log.info(" start - creerUnCompte : {}", compteDto);
        CompteDto resultat = bankServiceImpl.creerCompte(compteDto);
        return new ResponseEntity<>(resultat, HttpStatus.CREATED);
    }


    @PatchMapping("/crediter")
    public ResponseEntity<CompteDto> crediter(@RequestBody OperationCompteDto operationCompteDto) {
        log.info(" start - crediter ");
        CompteDto resultat = bankServiceImpl.crediter(operationCompteDto);
        return new ResponseEntity<>(resultat, HttpStatus.OK);
    }

    @PatchMapping("/debiter")
    public ResponseEntity<CompteDto> debiter(@RequestBody OperationCompteDto operationCompteDto) {
        log.info(" start - debiter ");
        CompteDto resultat = bankServiceImpl.debiter(operationCompteDto);
        return new ResponseEntity<>(resultat, HttpStatus.OK);
    }

    @GetMapping("/comptes")
    public ResponseEntity<List<CompteDto>> getComptes() {
        List<CompteDto> listeComptes = bankServiceImpl.obtenirTousLesComptes();
        if (CollectionUtils.isEmpty(listeComptes)) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listeComptes, HttpStatus.OK);
    }

    @GetMapping("/transferer")
    public ResponseEntity transferer(@RequestBody TransfertCompteDto transfertCompteDto) {
        bankServiceImpl.tranferer(transfertCompteDto.getNumeroCompteExpediteur(), transfertCompteDto.getNumeroCompteDestinataire(), transfertCompteDto.getMontantTransfert());
        return new ResponseEntity<>("Transfert effectué avec succès", HttpStatus.OK);
    }

    @GetMapping("/releves/{numeroCompte}")
    public ResponseEntity<CompteEntity> obtenirReleveCompte(@PathVariable String numeroCompte) {
        CompteEntity reponse = bankServiceImpl.trouverCompteParNumero(numeroCompte);
        return new ResponseEntity<>(reponse, HttpStatus.OK);
    }
}
