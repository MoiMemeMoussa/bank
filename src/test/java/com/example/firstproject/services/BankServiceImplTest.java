package com.example.firstproject.services;

import com.example.firstproject.entities.CompteEntity;
import com.example.firstproject.entities.OperationCompteEntity;
import com.example.firstproject.entities.TypeOperation;
import com.example.firstproject.exceptions.RessourceAlreadyExistException;
import com.example.firstproject.exceptions.RessourceNotFoundException;
import com.example.firstproject.exceptions.RetraitImpossibleException;
import com.example.firstproject.mappers.EntityDtoMapper;
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

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.firstproject.utils.ResourceTestUtils.getCompte;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BankServiceImplTest {

    private BankService bankService;

    @Mock
    private CompteRepository compteRepository;

    @Mock
    EntityDtoMapper mapper;

    @BeforeEach
    void setUp() {
        bankService = new BankServiceImpl(compteRepository, mapper);
    }

    @Test
    void creerCompteTest() {
        CompteEntity compteEntity = ResourceTestUtils.getCompteEntity();
        OperationCompteEntity operationCompteEntity = ResourceTestUtils.getOperationCompteEntity();

        Mockito.when(compteRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(compteRepository.save(Mockito.any())).thenReturn(compteEntity);
        Mockito.when(mapper.toOperationCompteEntity(Mockito.any())).thenReturn(operationCompteEntity);

        OperationCompteDto operationCompteDto = ResourceTestUtils.getOperationCompteDto();

        Mockito.when(mapper.toOperationCompteDto(Mockito.any())).thenReturn(operationCompteDto);
        Mockito.when(mapper.toCompteDto(Mockito.any())).thenReturn(ResourceTestUtils.getCompte());
        Mockito.when(mapper.toCompteEntity(Mockito.any())).thenReturn(compteEntity);

        CompteDto reponse = bankService.creerCompte(getCompte());

        Assertions.assertNotNull(reponse);
        Assertions.assertNull(reponse.getOperations());
        Assertions.assertEquals(TypeOperation.CREDIT, operationCompteDto.getTypeOperation());

    }

    @Test
    void creerCompteQuiExisteDejaRenvoieException() {
        Mockito.when(compteRepository.findById(Mockito.anyString())).thenReturn(Optional.of(ResourceTestUtils.getCompteEntity()));
        Mockito.when(mapper.toCompteDto(Mockito.any())).thenReturn(ResourceTestUtils.getCompte());
        Mockito.when(mapper.toCompteEntity(Mockito.any())).thenReturn(ResourceTestUtils.getCompteEntity());
        Mockito.when(mapper.toCompteDto(Mockito.any())).thenReturn(ResourceTestUtils.getCompte());
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

        OperationCompteDto operationCompteDto = ResourceTestUtils.getOperationCompteDto();
        operationCompteDto.setTypeOperation(TypeOperation.CREDIT);
        operationCompteDto.setMontantOperation(5_000.0);
        operationCompteDto.setNumeroCompte(getCompte().getNumeroCompte());

        Mockito.when(compteRepository.save(Mockito.any())).thenReturn(ResourceTestUtils.getCompteEntity());
        Mockito.when(mapper.toCompteDto(Mockito.any())).thenReturn(ResourceTestUtils.getCompte());
        Mockito.when(mapper.toCompteEntity(Mockito.any())).thenReturn(ResourceTestUtils.getCompteEntity());
        Mockito.when(mapper.toOperationCompteEntity(Mockito.any())).thenReturn(ResourceTestUtils.getOperationCompteEntity());
        Mockito.when(mapper.toOperationCompteDto(Mockito.any())).thenReturn(operationCompteDto);

        bankService.creerCompte(getCompte());
        Mockito.when(compteRepository.findById(Mockito.anyString())).thenReturn(Optional.of(ResourceTestUtils.getCompteEntity()));


        CompteDto reponse = bankService.crediterOuDebiter(operationCompteDto);
        Assertions.assertNotNull(reponse);
    }

    @Test
    void crediterUnCompteQuiExistePasRenvoieException() {
        OperationCompteDto operationCompteDto = new OperationCompteDto();
        operationCompteDto.setMontantOperation(23_000.0);
        assertThrows(RessourceNotFoundException.class,
                () ->
                        bankService.crediterOuDebiter(operationCompteDto));
    }


    @Test
    void debiterCompteTest() {
        OperationCompteDto operationCompteDto = ResourceTestUtils.getOperationCompteDto();
        operationCompteDto.setMontantOperation(3_000.0);
        operationCompteDto.setTypeOperation(TypeOperation.DEBIT);
        operationCompteDto.setNumeroCompte(getCompte().getNumeroCompte());

        Mockito.when(compteRepository.save(Mockito.any())).thenReturn(ResourceTestUtils.getCompteEntity());
        Mockito.when(mapper.toCompteDto(Mockito.any())).thenReturn(ResourceTestUtils.getCompte());
        Mockito.when(mapper.toCompteEntity(Mockito.any())).thenReturn(ResourceTestUtils.getCompteEntity());
        Mockito.when(mapper.toOperationCompteEntity(Mockito.any())).thenReturn(ResourceTestUtils.getOperationCompteEntity());
        Mockito.when(mapper.toOperationCompteDto(Mockito.any())).thenReturn(operationCompteDto);

        bankService.creerCompte(getCompte());
        Mockito.when(compteRepository.save(Mockito.any())).thenReturn(ResourceTestUtils.getCompteEntity());
        Mockito.when(compteRepository.findById(Mockito.anyString())).thenReturn(Optional.of(ResourceTestUtils.getCompteEntity()));
        CompteDto reponse = bankService.crediterOuDebiter(operationCompteDto);
        Assertions.assertNotNull(reponse);
    }

    @Test
    void debiterUnCompteQuiExistePasRenvoieException() {
        OperationCompteDto operationCompteDto = new OperationCompteDto();
        operationCompteDto.setMontantOperation(23_000.0);
        operationCompteDto.setNumeroCompte(getCompte().getNumeroCompte());
        assertThrows(RessourceNotFoundException.class,
                () ->
                        bankService.crediterOuDebiter(operationCompteDto));
    }

    @Test
    void debiterPlusQueLeSoldeDuCompteRenvoieException() {

        OperationCompteDto operationCompteDto = ResourceTestUtils.getOperationCompteDto();
        operationCompteDto.setNumeroCompte(getCompte().getNumeroCompte());

        Mockito.when(compteRepository.save(Mockito.any())).thenReturn(ResourceTestUtils.getCompteEntity());
        Mockito.when(mapper.toCompteDto(Mockito.any())).thenReturn(ResourceTestUtils.getCompte());
        Mockito.when(mapper.toCompteEntity(Mockito.any())).thenReturn(ResourceTestUtils.getCompteEntity());
        Mockito.when(mapper.toOperationCompteEntity(Mockito.any())).thenReturn(ResourceTestUtils.getOperationCompteEntity());
        Mockito.when(mapper.toOperationCompteDto(Mockito.any())).thenReturn(operationCompteDto);

        bankService.creerCompte(getCompte());

        Mockito.when(compteRepository.save(Mockito.any())).thenReturn(ResourceTestUtils.getCompteEntity());
        Mockito.when(compteRepository.findById(Mockito.anyString())).thenReturn(Optional.of(ResourceTestUtils.getCompteEntity()));

        operationCompteDto.setTypeOperation(TypeOperation.DEBIT);
        assertThrows(RetraitImpossibleException.class,
                () ->
                        bankService.crediterOuDebiter(operationCompteDto));
    }

    @Test
    void transfererTest() {
        OperationCompteDto operationCompteDto = new OperationCompteDto();
        operationCompteDto.setMontantOperation(113_000.0);
        operationCompteDto.setTypeOperation(TypeOperation.DEBIT);
        operationCompteDto.setNumeroCompte(getCompte().getNumeroCompte());

        Mockito.when(compteRepository.save(Mockito.any()))
                .thenReturn(ResourceTestUtils.getCompteEntity());
        Mockito.when(mapper.toCompteDto(Mockito.any())).thenReturn(ResourceTestUtils.getCompte());
        Mockito.when(mapper.toCompteEntity(Mockito.any())).thenReturn(ResourceTestUtils.getCompteEntity());
        Mockito.when(mapper.toOperationCompteEntity(Mockito.any())).thenReturn(ResourceTestUtils.getOperationCompteEntity());
        Mockito.when(mapper.toOperationCompteDto(Mockito.any())).thenReturn(operationCompteDto);

        CompteDto compteExpediteur = getCompte();
        compteExpediteur = bankService.creerCompte(compteExpediteur);

        CompteDto compteDestinataire = getCompte();
        compteDestinataire.setNumeroCompte("FR-010285");
        compteDestinataire = bankService.creerCompte(compteDestinataire);

        // Solde avant transfert
        compteExpediteur.setSolde(10_000.0);
        compteDestinataire.setSolde(3_000.0);

        Mockito.when(compteRepository.findById(Mockito.anyString())).thenReturn(Optional.of(ResourceTestUtils.getCompteEntity()));
        final CompteDto finalCompteDestinataire = compteDestinataire;
        final CompteDto finalCompteExpediteur = compteExpediteur;
        assertDoesNotThrow(
                () ->
                        bankService.tranferer(finalCompteExpediteur.getNumeroCompte(), finalCompteDestinataire.getNumeroCompte(), 500.0));
    }
}
