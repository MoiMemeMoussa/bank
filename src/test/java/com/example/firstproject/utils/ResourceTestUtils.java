package com.example.firstproject.utils;

import com.example.firstproject.entities.CompteEntity;
import com.example.firstproject.entities.OperationCompteEntity;
import com.example.firstproject.models.CompteDto;

import java.util.ArrayList;
import java.util.List;

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

    public static OperationCompteEntity getOperation() {
        OperationCompteEntity operation = new OperationCompteEntity();
        operation.setDescription("depot initial");
        operation.setTypeOperation("CREDIT");
        operation.setMontantOperation(4120.00);
        return operation;
    }

    public static List<OperationCompteEntity> getListeOperation() {
        List<OperationCompteEntity> list = new ArrayList<>();
        OperationCompteEntity operation = getOperation();
        list.add(operation);
        return list;
    }
}
