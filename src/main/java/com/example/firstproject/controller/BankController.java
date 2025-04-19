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
        operationCompteDto.setTypeOperation(TypeOperation.CREDIT);
        CompteDto resultat = bankServiceImpl.crediterOuDebiter(operationCompteDto);
        return new ResponseEntity<>(resultat, HttpStatus.OK);
    }

    @PatchMapping("/debiter")
    public ResponseEntity<CompteDto> debiter(@RequestBody OperationCompteDto operationCompteDto) {
        log.info(" start - debiter ");
        operationCompteDto.setTypeOperation(TypeOperation.DEBIT);
        CompteDto resultat = bankServiceImpl.crediterOuDebiter(operationCompteDto);
        return new ResponseEntity<>(resultat, HttpStatus.OK);
    }

    @GetMapping("/transferer")
    public ResponseEntity<CompteDto> transferer(@RequestBody TransfertCompteDto transfertCompteDto) {
        CompteDto compteDto = bankServiceImpl.tranferer(transfertCompteDto.getNumeroCompteExpediteur(), transfertCompteDto.getNumeroCompteDestinataire(), transfertCompteDto.getMontantTransfert());
        return new ResponseEntity<>(compteDto, HttpStatus.OK);
    }

    @GetMapping("/releves/{numeroCompte}")
    public ResponseEntity<CompteDto> obtenirReleveCompte(@PathVariable String numeroCompte) {
        CompteDto reponse = bankServiceImpl.obtenirReleveCompte(numeroCompte);
        return new ResponseEntity<>(reponse, HttpStatus.OK);
    }
}
