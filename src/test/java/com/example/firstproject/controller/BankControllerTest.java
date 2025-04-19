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
    void creerCompte_retourneSucces() {
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
    void crediter_retourneSucces() {
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
    void debiter_retourneSucces() {
        OperationCompteDto operationDebit = getOperationCompteDto();
        operationDebit.setMontantOperation(1_000.00);

        Mockito.when(bankServiceImpl.crediterOuDebiter(Mockito.any())).thenReturn(getCompte());

        ResponseEntity<CompteDto> responseEntity = bankController.debiter(operationDebit);

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
    @DisplayName("Test: tester obtenir un releve de compte")
    void obtenirReleveCompte_retourneSucces() {
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
    void transferer_retourneSucces() {
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
