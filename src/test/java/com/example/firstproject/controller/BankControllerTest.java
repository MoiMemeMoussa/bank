package com.example.firstproject.controller;

import com.example.firstproject.models.CompteDto;
import com.example.firstproject.models.OperationCompteDto;
import com.example.firstproject.models.TransfertCompteDto;
import com.example.firstproject.services.BankServiceImpl;
import com.example.firstproject.utils.ResourceTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.example.firstproject.utils.ResourceTestUtils.getCompte;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BankControllerTest {

    private BankController bankController;

    @MockBean
    BankServiceImpl bankServiceImpl;

    @BeforeEach
    void setUp() {
        bankController = new BankController(bankServiceImpl);
    }

    @Test
    @DisplayName("Test: créer un compte")
    void creerCompteTest() {
        Mockito.when(bankServiceImpl.creerCompte(Mockito.any())).thenReturn(getCompte());
        ResponseEntity<CompteDto> responseEntity = bankController.creerCompte(getCompte());

        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getSolde());
        assertNotNull(responseEntity.getBody().getFirstName());
        assertNotNull(responseEntity.getBody().getLastName());
        assertNotNull(responseEntity.getBody().getNumeroCompte());

        assertEquals(HttpStatusCode.valueOf(201), responseEntity.getStatusCode());
        assertEquals("Alex", responseEntity.getBody().getFirstName());
        assertEquals("Zong", responseEntity.getBody().getLastName());
        assertEquals("FR-19852402-1", responseEntity.getBody().getNumeroCompte());
        assertEquals(5_000.00, (double) responseEntity.getBody().getSolde());
    }

    @Test
    @DisplayName("Test: créditer un compte")
    void crediterCompteDevraitAugmenterLeSolde() {
        CompteDto compte = getCompte();
        compte.setSolde(20_000.00);
        Mockito.when(bankServiceImpl.crediterOuDebiter(Mockito.any())).thenReturn(compte);
        ResponseEntity<CompteDto> responseEntity = bankController.crediter(getOperationCompteDto());

        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getSolde());
        assertNotNull(responseEntity.getBody().getFirstName());
        assertNotNull(responseEntity.getBody().getLastName());
        assertNotNull(responseEntity.getBody().getNumeroCompte());

        assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        assertEquals(20_000.00, responseEntity.getBody().getSolde());
    }

    @Test
    @DisplayName("Test: débiter un compte")
    void debiterCompteDevraitDiminuerLeSolde() {
        OperationCompteDto compte = getOperationCompteDto();
        compte.setMontantOperation(1_000.00);
        Mockito.when(bankServiceImpl.crediterOuDebiter(Mockito.any())).thenReturn(getCompte());
        ResponseEntity<CompteDto> responseEntity = bankController.debiter(compte);

        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getSolde());
        assertNotNull(responseEntity.getBody().getFirstName());
        assertNotNull(responseEntity.getBody().getLastName());
        assertNotNull(responseEntity.getBody().getNumeroCompte());

        assertEquals(5_000.00, responseEntity.getBody().getSolde());
    }

    @Test
    @DisplayName("Test: afficher tous les comptes")
    void afficherTousLesComptes() {

        CompteDto compte1 = getCompte();
        CompteDto compte2 = getCompte();
        CompteDto compte3 = getCompte();

        compte1.setSolde(2_000.0);
        compte2.setSolde(5_000.0);
        compte3.setSolde(15_000.0);

        Mockito.when(bankServiceImpl.obtenirTousLesComptes()).thenReturn(Arrays.asList(compte1, compte2, compte3));
        ResponseEntity<List<CompteDto>> responseEntity = bankController.getComptes();

        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().get(0));
        assertNotNull(responseEntity.getBody().get(0).getSolde());
        assertNotNull(responseEntity.getBody().get(1).getSolde());
        assertNotNull(responseEntity.getBody().get(2).getSolde());

        assertEquals(3, responseEntity.getBody().size());

        assertEquals(compte1.getSolde(), responseEntity.getBody().get(0).getSolde());
        assertEquals(compte2.getSolde(), responseEntity.getBody().get(1).getSolde());
        assertEquals(compte3.getSolde(), responseEntity.getBody().get(2).getSolde());
    }

    @Test
    @DisplayName("Test: afficher tous les comptes retourne vide")
    void afficherTousLesComptesRetourneVide() {
        Mockito.when(bankServiceImpl.obtenirTousLesComptes()).thenReturn(Collections.emptyList());
        Assertions.assertDoesNotThrow(
                () -> bankController.getComptes());
        ResponseEntity<List<CompteDto>> resultat = bankController.getComptes();
        Assertions.assertTrue(resultat.getBody().isEmpty());
        Assertions.assertEquals(HttpStatus.NO_CONTENT, resultat.getStatusCode());

    }

    @Test
    @DisplayName("Test: tester obtenir un releve de compte")
    void obtenirReleveCompteTest() {
        CompteDto compteDto = ResourceTestUtils.getCompte();
        Mockito.when(bankServiceImpl.obtenirReleveCompte(Mockito.anyString())).thenReturn(compteDto);
        Assertions.assertDoesNotThrow(
                () -> bankController.obtenirReleveCompte("SN-12031984"));
        ResponseEntity<CompteDto> responseEntity = bankController.obtenirReleveCompte("SN-12031984");
        Assertions.assertNotNull(responseEntity);
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertNotNull(responseEntity.getStatusCode());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void transfererTest() {
        Mockito.when(bankServiceImpl.obtenirReleveCompte(Mockito.any())).thenReturn(ResourceTestUtils.getCompte());
        TransfertCompteDto transfertCompteDto = new TransfertCompteDto();
        transfertCompteDto.setNumeroCompteExpediteur("SN-18031981");
        transfertCompteDto.setNumeroCompteDestinataire("FR-24021985");
        transfertCompteDto.setMontantTransfert(15_000.00);
        Assertions.assertDoesNotThrow(() -> bankController
                .transferer(transfertCompteDto));
        ResponseEntity<CompteDto> resultat = bankController.transferer(transfertCompteDto);
        Assertions.assertNotNull(resultat);
        Assertions.assertNotNull(resultat.getStatusCode());
        Assertions.assertEquals(HttpStatus.OK, resultat.getStatusCode());
    }

    private OperationCompteDto getOperationCompteDto() {
        OperationCompteDto operationCompteDto = new OperationCompteDto();
        operationCompteDto.setNumeroCompte("FR-19852402-1");
        return operationCompteDto;
    }
}
