package com.example.firstproject.controller;

import com.example.firstproject.models.CompteDto;
import com.example.firstproject.models.OperationCompteDto;
import com.example.firstproject.services.BankService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static com.example.firstproject.utils.ResourceTestUtils.getCompte;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BankControllerTest {

    private BankController bankController;

    @MockBean
    BankService bankService;

    @BeforeEach
    void setUp() {
        bankController = new BankController(bankService);
    }

    @Test
    @DisplayName("Test: créer un compte")
    void creerCompteTest() {
        Mockito.when(bankService.creerCompte(Mockito.any())).thenReturn(getCompte());
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
        Mockito.when(bankService.crediter(Mockito.any())).thenReturn(compte);
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
        Mockito.when(bankService.debiter(Mockito.any())).thenReturn(getCompte());
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

        Mockito.when(bankService.getListeComptes()).thenReturn(Arrays.asList(compte1, compte2,compte3));
        ResponseEntity<List<CompteDto>> responseEntity = bankController.getComptes();

        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        assertEquals(3, responseEntity.getBody().size());

        assertEquals(2_000.0, responseEntity.getBody().get(0).getSolde());
        assertEquals(5_000.0, responseEntity.getBody().get(1).getSolde());
        assertEquals(15_000.0, responseEntity.getBody().get(2).getSolde());
    }

    private OperationCompteDto getOperationCompteDto() {
        OperationCompteDto operationCompteDto = new OperationCompteDto();
        operationCompteDto.setNumeroCompte("FR-19852402-1");
        return operationCompteDto;
    }
}
