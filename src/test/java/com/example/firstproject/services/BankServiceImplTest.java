package com.example.firstproject.services;

import com.example.firstproject.entities.CompteEntity;
import com.example.firstproject.entities.OperationCompteEntity;
import com.example.firstproject.entities.TypeOperation;
import com.example.firstproject.exceptions.IncorrectMontantException;
import com.example.firstproject.exceptions.RessourceExistanteException;
import com.example.firstproject.exceptions.RessourceNonTrouveException;
import com.example.firstproject.exceptions.RetraitImpossibleException;
import com.example.firstproject.mappers.EntityDtoMapper;
import com.example.firstproject.models.CompteDto;
import com.example.firstproject.models.OperationCompteDto;
import com.example.firstproject.repositories.CompteRepository;
import com.example.firstproject.services.ra.RaValidation;
import com.example.firstproject.utils.ResourceTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Optional;

import static com.example.firstproject.utils.ResourceTestUtils.getCompte;

@SpringBootTest
class BankServiceImplTest {

    @Mock
    private CompteRepository compteRepository;

    @Mock
    EntityDtoMapper mapper;

    private CompteDto compteDto;
    private CompteEntity compteEntity;
    private OperationCompteEntity operationCompteEntity;
    private RaValidation raValidation;
    private BankService bankService;

    @BeforeEach
    void setUp() {
        raValidation = new RaValidation();
        bankService = new BankServiceImpl(compteRepository, mapper, raValidation);
        compteDto = ResourceTestUtils.getCompte();
        compteEntity = ResourceTestUtils.getCompteEntity();
        operationCompteEntity = ResourceTestUtils.getOperationCompteEntity();
    }

    @Test
    void creerCompte_retourneSucces() {
        Mockito.when(compteRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(compteRepository.save(Mockito.any())).thenReturn(compteEntity);
        Mockito.when(mapper.toOperationCompteEntity(Mockito.any())).thenReturn(operationCompteEntity);
        Mockito.when(mapper.toCompteDto(Mockito.any())).thenReturn(compteDto);
        Mockito.when(mapper.toCompteEntity(Mockito.any(), Mockito.any())).thenReturn(compteEntity);

        CompteDto reponse = bankService.creerCompte(getCompte());

        Assertions.assertAll(() -> {
            Assertions.assertNotNull(reponse);
            Assertions.assertNotNull(reponse.getNumeroCompte());
            Assertions.assertNotNull(reponse.getOperations());
            Assertions.assertEquals(getCompte().getNumeroCompte(), reponse.getNumeroCompte());
        });

    }

    @Test
    void creerCompte_retourneRessourceAlreadyExistException() {
        Mockito.when(compteRepository.findById(Mockito.anyString())).thenReturn(Optional.of(compteEntity));
        Mockito.when(mapper.toCompteDto(Mockito.any())).thenReturn(compteDto);
        Mockito.when(mapper.toCompteEntity(Mockito.any(), Mockito.any())).thenReturn(compteEntity);

        CompteDto compteToCreate = getCompte();
        Assertions.assertThrows(RessourceExistanteException.class,
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
        Mockito.when(mapper.toCompteEntity(Mockito.any(), Mockito.any())).thenReturn(compteEntity);
        Mockito.when(mapper.toOperationCompteEntity(Mockito.any())).thenReturn(operationCompteEntity);

        bankService.creerCompte(getCompte());
        Mockito.when(compteRepository.findById(Mockito.anyString())).thenReturn(Optional.of(compteEntity));

        CompteDto reponse = bankService.crediterOuDebiter(operationCompteDto);
        Assertions.assertNotNull(reponse);
    }

    @Test
    void crediterOuDebiterRenvoieRessourceNotFoundException() {
        OperationCompteDto operationCompteDto = new OperationCompteDto();
        operationCompteDto.setMontantOperation(23_000.0);
        Assertions.assertThrows(RessourceNonTrouveException.class,
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
        Mockito.when(mapper.toCompteEntity(Mockito.any(), Mockito.any())).thenReturn(compteEntity);
        Mockito.when(mapper.toOperationCompteEntity(Mockito.any())).thenReturn(operationCompteEntity);

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
        Assertions.assertThrows(RessourceNonTrouveException.class,
                () ->
                        bankService.crediterOuDebiter(operationCompteDto));
    }

    @Test
    void crediterOuDebiter_retourneIncorrectMontantException() {
        OperationCompteDto operationCompteDto = new OperationCompteDto();
        operationCompteDto.setMontantOperation(0.00);
        operationCompteDto.setNumeroCompte(getCompte().getNumeroCompte());
        Assertions.assertThrows(IncorrectMontantException.class,
                () ->
                        bankService.crediterOuDebiter(operationCompteDto));
    }

    @Test
    void crediterOuDebiter_retourneRetraitImpossibleException() {

        OperationCompteDto operationCompteDto = ResourceTestUtils.getOperationCompteDto();
        operationCompteDto.setNumeroCompte(getCompte().getNumeroCompte());

        Mockito.when(compteRepository.save(Mockito.any())).thenReturn(compteEntity);
        Mockito.when(mapper.toCompteDto(Mockito.any())).thenReturn(compteDto);
        Mockito.when(mapper.toCompteEntity(Mockito.any(), Mockito.any())).thenReturn(compteEntity);
        Mockito.when(mapper.toOperationCompteEntity(Mockito.any())).thenReturn(operationCompteEntity);

        bankService.creerCompte(getCompte());

        Mockito.when(compteRepository.save(Mockito.any())).thenReturn(compteEntity);
        Mockito.when(compteRepository.findById(Mockito.anyString())).thenReturn(Optional.of(compteEntity));

        operationCompteDto.setTypeOperation(TypeOperation.DEBIT);

        Assertions.assertTrue(compteEntity.getSolde() < operationCompteDto.getMontantOperation());
        Assertions.assertThrows(RetraitImpossibleException.class,
                () -> bankService.crediterOuDebiter(operationCompteDto));
    }

    @Disabled (" Je vais revenir sur ce test")
    @Test
    void tranferer_retourneSucces() {
        OperationCompteDto opExpediteur = new OperationCompteDto();
        opExpediteur.setMontantOperation(1000.0);
        opExpediteur.setTypeOperation(TypeOperation.DEBIT);
        opExpediteur.setNumeroCompte(getCompte().getNumeroCompte());

        Mockito.when(compteRepository.save(Mockito.any()))
                .thenReturn(compteEntity);
        Mockito.when(mapper.toCompteDto(Mockito.any())).thenReturn(compteDto);
        Mockito.when(mapper.toCompteEntity(Mockito.any(), Mockito.any())).thenReturn(compteEntity);
        Mockito.when(mapper.toOperationCompteEntity(Mockito.any())).thenReturn(operationCompteEntity);

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
