package com.example.firstproject.services;

import com.example.firstproject.exceptions.RessourceAlreadyExistException;
import com.example.firstproject.exceptions.RessourceNotFoundException;
import com.example.firstproject.exceptions.RetraitImpossibleException;
import com.example.firstproject.models.CompteDto;
import com.example.firstproject.models.OperationCompteDto;
import com.example.firstproject.repositories.CompteRepository;
import com.example.firstproject.utils.ResourceTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.firstproject.utils.ResourceTestUtils.getCompte;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class BankServiceTest {

    private BankService bankService;

    @Mock
    private CompteRepository compteRepository;

    @BeforeEach
    void setUp() {
        bankService = new BankService(compteRepository);
    }

    @Test
    void creerCompteTest() {
        Mockito.when(compteRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(compteRepository.save(Mockito.any())).thenReturn(ResourceTestUtils.getCompteEntity());
        Assertions.assertNotNull(bankService.creerCompte(getCompte()));
    }

    @Test
    void creerCompteQuiExisteDejaRenvoieException() {
        Mockito.when(compteRepository.findById(Mockito.anyString())).thenReturn(Optional.of(ResourceTestUtils.getCompteEntity()));
        assertThrows(RessourceAlreadyExistException.class,
                () ->
                        bankService.creerCompte(getCompte()));
    }

    @Test
    void obtenirTousLesComptesTest() {
        Mockito.when(compteRepository.findAll()).thenReturn(Arrays.asList(ResourceTestUtils.getCompteEntity()));
        List<CompteDto> reponse = bankService.obtenirTousLesComptes();
        Assertions.assertNotNull(reponse);
    }

    @Test
    void crediterCompteTest() {
        Mockito.when(compteRepository.save(Mockito.any())).thenReturn(ResourceTestUtils.getCompteEntity());
        bankService.creerCompte(getCompte());
        OperationCompteDto operationCompteDto = new OperationCompteDto();
        operationCompteDto.setMontantOperation(5_000.0);
        operationCompteDto.setNumeroCompte(getCompte().getNumeroCompte());
        Mockito.when(compteRepository.findById(Mockito.anyString())).thenReturn(Optional.of(ResourceTestUtils.getCompteEntity()));
        CompteDto reponse = bankService.crediter(operationCompteDto);
        Assertions.assertNotNull(reponse);
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
        Mockito.when(compteRepository.save(Mockito.any())).thenReturn(ResourceTestUtils.getCompteEntity());
        bankService.creerCompte(getCompte());
        OperationCompteDto operationCompteDto = new OperationCompteDto();
        operationCompteDto.setMontantOperation(3_000.0);
        operationCompteDto.setNumeroCompte(getCompte().getNumeroCompte());
        Mockito.when(compteRepository.save(Mockito.any())).thenReturn(ResourceTestUtils.getCompteEntity());
        Mockito.when(compteRepository.findById(Mockito.anyString())).thenReturn(Optional.of(ResourceTestUtils.getCompteEntity()));
        CompteDto reponse = bankService.debiter(operationCompteDto);
        Assertions.assertNotNull(reponse);
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
        Mockito.when(compteRepository.save(Mockito.any())).thenReturn(ResourceTestUtils.getCompteEntity());
        bankService.creerCompte(getCompte());
        OperationCompteDto operationCompteDto = new OperationCompteDto();
        operationCompteDto.setMontantOperation(113_000.0);
        operationCompteDto.setNumeroCompte(getCompte().getNumeroCompte());
        Mockito.when(compteRepository.save(Mockito.any())).thenReturn(ResourceTestUtils.getCompteEntity());
        Mockito.when(compteRepository.findById(Mockito.anyString())).thenReturn(Optional.of(ResourceTestUtils.getCompteEntity()));
        assertThrows(RetraitImpossibleException.class,
                () ->
                        bankService.debiter(operationCompteDto));
    }

    @Test
    void transfererTest() {
        Mockito.when(compteRepository.save(Mockito.any()))
                .thenReturn(ResourceTestUtils.getCompteEntity());
        CompteDto compteExpediteur = getCompte();
        compteExpediteur = bankService.creerCompte(compteExpediteur);
        CompteDto compteDestinataire = getCompte();
        compteDestinataire.setNumeroCompte("FR-010285");
        compteDestinataire = bankService.creerCompte(compteDestinataire);
        // Solde avant transfert
        compteExpediteur.setSolde(10_000.0);
        compteDestinataire.setSolde(3_000.0);

        Mockito.when(compteRepository.findById(Mockito.anyString())).thenReturn(Optional.of(ResourceTestUtils.getCompteEntity()));
        CompteDto finalCompteDestinataire = compteDestinataire;
        CompteDto finalCompteExpediteur = compteExpediteur;
        assertDoesNotThrow(
                () ->
                        bankService.tranferer(finalCompteExpediteur.getNumeroCompte(), finalCompteDestinataire.getNumeroCompte(), 500.0));
    }
}
