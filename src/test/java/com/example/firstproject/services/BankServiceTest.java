package com.example.firstproject.services;

import com.example.firstproject.exceptions.RessourceAlreadyExistException;
import com.example.firstproject.exceptions.RessourceNotFoundException;
import com.example.firstproject.exceptions.RetraitImpossibleException;
import com.example.firstproject.models.CompteDto;
import com.example.firstproject.models.OperationCompteDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.firstproject.utils.ResourceTestUtils.getCompte;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class BankServiceTest {


    private BankService bankService;

    @BeforeEach
    void setUp() {
        bankService = new BankService();
        bankService.getListeComptes().clear();
    }

    @Test
    void creerCompteTest() {
        bankService.creerCompte(getCompte());
        assertEquals(1, bankService.getListeComptes().size());
    }

    @Test
    void creerCompteQuiExisteDejaRenvoieException() {
        bankService.creerCompte(getCompte());
        assertThrows(RessourceAlreadyExistException.class,
                () ->
                        bankService.creerCompte(getCompte()));
    }

    @Test
    void crediterCompteTest() {
        bankService.creerCompte(getCompte());
        OperationCompteDto operationCompteDto = new OperationCompteDto();
        operationCompteDto.setMontantOperation(5_000.0);
        operationCompteDto.setNumeroCompte(getCompte().getNumeroCompte());
        bankService.crediter(operationCompteDto);
        assertEquals(10_000.0, bankService.getListeComptes().get(0).getSolde());
    }

    @Test
    void crediterUnCompteQuiExistePasRenvoieException() {
        OperationCompteDto operationCompteDto = new OperationCompteDto();
        operationCompteDto.setMontantOperation(23_000.0);

        assertThrows(RessourceNotFoundException.class,
                () ->
                        bankService.crediter(operationCompteDto));
    }


    @Test
    void debiterCompteTest() {
        bankService.creerCompte(getCompte());
        OperationCompteDto operationCompteDto = new OperationCompteDto();
        operationCompteDto.setMontantOperation(3_000.0);
        operationCompteDto.setNumeroCompte(getCompte().getNumeroCompte());
        bankService.debiter(operationCompteDto);
        assertEquals(2_000.0, bankService.getListeComptes().get(0).getSolde());
    }

    @Test
    void debiterUnCompteQuiExistePasRenvoieException() {
        OperationCompteDto operationCompteDto = new OperationCompteDto();
        operationCompteDto.setMontantOperation(23_000.0);
        operationCompteDto.setNumeroCompte(getCompte().getNumeroCompte());
        assertThrows(RessourceNotFoundException.class,
                () ->
                        bankService.debiter(operationCompteDto));
    }

    @Test
    void debiterPlusQueLeSoldeDuCompteRenvoieException() {
        bankService.creerCompte(getCompte());
        OperationCompteDto operationCompteDto = new OperationCompteDto();
        operationCompteDto.setMontantOperation(453_000.0);
        operationCompteDto.setNumeroCompte(getCompte().getNumeroCompte());
        assertThrows(RetraitImpossibleException.class,
                () ->
                        bankService.debiter(operationCompteDto));
    }

    @Test
    void transfererTest() {
        CompteDto compteExpediteur = getCompte();
        CompteDto compteDestinataire = getCompte();
        compteDestinataire.setNumeroCompte("FR-010285");

        compteExpediteur = bankService.creerCompte(compteExpediteur);
        compteDestinataire = bankService.creerCompte(compteDestinataire);

        // Solde avant transfert
        compteExpediteur.setSolde(10_000.0);
        compteDestinataire.setSolde(3_000.0);

        bankService.tranferer(compteExpediteur.getNumeroCompte(), compteDestinataire.getNumeroCompte(), 5_000.0);
        assertEquals(5_000, compteExpediteur.getSolde());
        assertEquals(8_000, compteDestinataire.getSolde());
    }
}
