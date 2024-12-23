package com.example.firstproject.utils;

import com.example.firstproject.entities.CompteEntity;
import com.example.firstproject.entities.OperationCompteEntity;
import com.example.firstproject.entities.TypeOperation;
import com.example.firstproject.models.CompteDto;
import com.example.firstproject.models.OperationCompteDto;

public class ResourceTestUtils {

    public static CompteDto getCompte() {
        CompteDto compteDto = new CompteDto();
        compteDto.setFirstName("Alex");
        compteDto.setLastName("Zong");
        compteDto.setNumeroCompte("FR-19852402-1");
        compteDto.setSolde(5_000.00);
        return compteDto;
    }

    public static CompteEntity getCompteEntity() {
        CompteEntity entity = new CompteEntity();
        entity.setNumeroCompte("SN-010285");
        entity.setFirstName("FirstName");
        entity.setLastName("LastName");
        entity.setSolde(4120.00);
        return entity;
    }

    public static OperationCompteEntity getOperationCompteEntity() {
        OperationCompteEntity operationCompteEntity = new OperationCompteEntity();
        operationCompteEntity.setMontantOperation(4500.0);
        operationCompteEntity.setTypeOperation(String.valueOf(TypeOperation.CREDIT));
        operationCompteEntity.setMontantOperation(4500.0);
        return operationCompteEntity;
    }

    public static OperationCompteDto getOperationCompteDto() {
        OperationCompteDto operationCompteDto = new OperationCompteDto();
        operationCompteDto.setMontantOperation(4500.0);
        return operationCompteDto;
    }
}
