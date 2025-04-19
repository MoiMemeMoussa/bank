package com.example.firstproject.services;

import com.example.firstproject.entities.CompteEntity;
import com.example.firstproject.entities.OperationCompteEntity;
import com.example.firstproject.entities.TypeOperation;
import com.example.firstproject.exceptions.IncorrectOperationException;
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

import java.util.Optional;

import static com.example.firstproject.utils.ResourceTestUtils.getCompte;

@SpringBootTest
class BankServiceImplTest {

    private BankService bankService;

    @Mock
    private CompteRepository compteRepository;

    @Mock
    EntityDtoMapper mapper;

    private CompteDto compteDto;
    private CompteEntity compteEntity;
    private OperationCompteEntity operationCompteEntity;

    @BeforeEach
    void setUp() {
        bankService = new BankServiceImpl(compteRepository, mapper);
        compteDto = ResourceTestUtils.getCompte();
        compteEntity = ResourceTestUtils.getCompteEntity();
        operationCompteEntity = ResourceTestUtils.getOperationCompteEntity();
    }

    @Test
    void creerCompte_retourneSucces() {

        Mockito.when(compteRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(compteRepository.save(Mockito.any())).thenReturn(compteEntity);
        Mockito.when(mapper.toOperationCompteEntity(Mockito.any())).thenReturn(operationCompteEntity);

        OperationCompteDto operationCompteDto = ResourceTestUtils.getOperationCompteDto();
        operationCompteDto.setTypeOperation(TypeOperation.CREDIT);

        Mockito.when(mapper.toOperationCompteDto(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(operationCompteDto);
        Mockito.when(mapper.toCompteDto(Mockito.any())).thenReturn(compteDto);
        Mockito.when(mapper.toCompteEntity(Mockito.any())).thenReturn(compteEntity);

        CompteDto reponse = bankService.creerCompte(getCompte());

        Assertions.assertNotNull(reponse);
        Assertions.assertEquals(TypeOperation.CREDIT, operationCompteDto.getTypeOperation());

    }

    @Test
    void creerCompte_retourneRessourceAlreadyExistException() {
        Mockito.when(compteRepository.findById(Mockito.anyString())).thenReturn(Optional.of(compteEntity));
        Mockito.when(mapper.toCompteDto(Mockito.any())).thenReturn(compteDto);
        Mockito.when(mapper.toCompteEntity(Mockito.any())).thenReturn(compteEntity);

        CompteDto compteToCreate = getCompte();
        Assertions.assertThrows(RessourceAlreadyExistException.class,
                () ->
                        bankService.creerCompte(compteToCreate));
    }

    @Test
    void crediterOuDebiter_retourneSucces() {

        OperationCompteDto operationCompteDto = ResourceTestUtils.getOperationCompteDto();
        operationCompteDto.setTypeOperation(TypeOperation.CREDIT);
        operationCompteDto.setMontantOperation(5_000.0);
        operationCompteDto.setNumeroCompte(getCompte().getNumeroCompte());

        Mockito.when(compteRepository.save(Mockito.any())).thenReturn(compteEntity);
        Mockito.when(mapper.toCompteDto(Mockito.any())).thenReturn(compteDto);
        Mockito.when(mapper.toCompteEntity(Mockito.any())).thenReturn(compteEntity);
        Mockito.when(mapper.toOperationCompteEntity(Mockito.any())).thenReturn(operationCompteEntity);
        Mockito.when(mapper.toOperationCompteDto(Mockito.any(), Mockito.any(), Mockito.anyDouble())).thenReturn(operationCompteDto);

        bankService.creerCompte(getCompte());
        Mockito.when(compteRepository.findById(Mockito.anyString())).thenReturn(Optional.of(compteEntity));

        CompteDto reponse = bankService.crediterOuDebiter(operationCompteDto);
        Assertions.assertNotNull(reponse);
    }

    @Test
    void crediterOuDebiterRenvoieRessourceNotFoundException() {
        OperationCompteDto operationCompteDto = new OperationCompteDto();
        operationCompteDto.setMontantOperation(23_000.0);
        Assertions.assertThrows(RessourceNotFoundException.class,
                () ->
                        bankService.crediterOuDebiter(operationCompteDto));
    }


    @Test
    void crediterOuDebiter_Succes() {
        OperationCompteDto operationCompteDto = ResourceTestUtils.getOperationCompteDto();
        operationCompteDto.setMontantOperation(3_000.0);
        operationCompteDto.setTypeOperation(TypeOperation.DEBIT);
        operationCompteDto.setNumeroCompte(getCompte().getNumeroCompte());

        Mockito.when(compteRepository.save(Mockito.any())).thenReturn(compteEntity);
        Mockito.when(mapper.toCompteDto(Mockito.any())).thenReturn(compteDto);
        Mockito.when(mapper.toCompteEntity(Mockito.any())).thenReturn(compteEntity);
        Mockito.when(mapper.toOperationCompteEntity(Mockito.any())).thenReturn(operationCompteEntity);
        Mockito.when(mapper.toOperationCompteDto(Mockito.any(), Mockito.any(), Mockito.anyDouble())).thenReturn(operationCompteDto);

        bankService.creerCompte(getCompte());
        Mockito.when(compteRepository.save(Mockito.any())).thenReturn(compteEntity);
        Mockito.when(compteRepository.findById(Mockito.anyString())).thenReturn(Optional.of(compteEntity));
        CompteDto response = bankService.crediterOuDebiter(operationCompteDto);
        Assertions.assertNotNull(response);
    }

    @Test
    void crediterOuDebiter_retourneRessourceNotFoundException() {
        OperationCompteDto operationCompteDto = new OperationCompteDto();
        operationCompteDto.setMontantOperation(23_000.0);
        operationCompteDto.setNumeroCompte(getCompte().getNumeroCompte());
        Assertions.assertThrows(RessourceNotFoundException.class,
                () ->
                        bankService.crediterOuDebiter(operationCompteDto));
    }

    @Test
    void crediterOuDebiter_retourneIncorrectOperationException() {
        OperationCompteDto operationCompteDto = new OperationCompteDto();
        operationCompteDto.setMontantOperation(0.00);
        operationCompteDto.setNumeroCompte(getCompte().getNumeroCompte());
        Assertions.assertThrows(IncorrectOperationException.class,
                () ->
                        bankService.crediterOuDebiter(operationCompteDto));
    }

    @Test
    void crediterOuDebiter_retourneRetraitImpossibleException() {

        OperationCompteDto operationCompteDto = ResourceTestUtils.getOperationCompteDto();
        operationCompteDto.setNumeroCompte(getCompte().getNumeroCompte());

        Mockito.when(compteRepository.save(Mockito.any())).thenReturn(compteEntity);
        Mockito.when(mapper.toCompteDto(Mockito.any())).thenReturn(compteDto);
        Mockito.when(mapper.toCompteEntity(Mockito.any())).thenReturn(compteEntity);
        Mockito.when(mapper.toOperationCompteEntity(Mockito.any())).thenReturn(operationCompteEntity);
        Mockito.when(mapper.toOperationCompteDto(Mockito.any(), Mockito.any(), Mockito.anyDouble())).thenReturn(operationCompteDto);

        bankService.creerCompte(getCompte());

        Mockito.when(compteRepository.save(Mockito.any())).thenReturn(compteEntity);
        Mockito.when(compteRepository.findById(Mockito.anyString())).thenReturn(Optional.of(compteEntity));

        operationCompteDto.setTypeOperation(TypeOperation.DEBIT);

        Assertions.assertTrue(compteEntity.getSolde() < operationCompteDto.getMontantOperation());
        Assertions.assertThrows(RetraitImpossibleException.class,
                () -> bankService.crediterOuDebiter(operationCompteDto));
    }

    @Test
    void tranferer_retourneSucces() {
        OperationCompteDto opExpediteur = new OperationCompteDto();
        opExpediteur.setMontantOperation(1000.0);
        opExpediteur.setTypeOperation(TypeOperation.DEBIT);
        opExpediteur.setNumeroCompte(getCompte().getNumeroCompte());

        Mockito.when(compteRepository.save(Mockito.any()))
                .thenReturn(compteEntity);
        Mockito.when(mapper.toCompteDto(Mockito.any())).thenReturn(compteDto);
        Mockito.when(mapper.toCompteEntity(Mockito.any())).thenReturn(compteEntity);
        Mockito.when(mapper.toOperationCompteEntity(Mockito.any())).thenReturn(operationCompteEntity);
        Mockito.when(mapper.toOperationCompteDto(Mockito.any(), Mockito.any(), Mockito.anyDouble())).thenReturn(opExpediteur);

        CompteDto compteExpediteur = bankService.creerCompte(getCompte());

        CompteDto compteDestinataire = getCompte();
        compteDestinataire.setNumeroCompte("FR-010285");
        compteDestinataire = bankService.creerCompte(compteDestinataire);

        // Solde avant transfert
        compteExpediteur.setSolde(10_000.0);
        compteDestinataire.setSolde(3_000.0);

        compteEntity.setSolde(10_000.0);

        Mockito.when(compteRepository.findById(Mockito.anyString())).thenReturn(Optional.of(compteEntity));
        final CompteDto finalCompteDestinataire = compteDestinataire;
        final CompteDto finalCompteExpediteur = compteExpediteur;
        Assertions.assertDoesNotThrow(() ->
                bankService.tranferer(finalCompteExpediteur.getNumeroCompte(),
                        finalCompteDestinataire.getNumeroCompte(), 500.0));
    }
}
