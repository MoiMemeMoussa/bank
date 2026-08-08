package com.example.firstproject.mappers;

import com.example.firstproject.entities.CompteEntity;
import com.example.firstproject.entities.OperationCompteEntity;
import com.example.firstproject.entities.TypeOperation;
import com.example.firstproject.models.CompteDto;
import com.example.firstproject.models.OperationCompteDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface EntityDtoMapper {

    @Mapping(target = "operations.numeroCompte", ignore = true)
    CompteDto toCompteDto(CompteEntity compteEntity);

    @Mapping(target = "operations", expression = "java(obtenirOperations(operationCompteDto))")
    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "numeroCompte", source = "compteDto.numeroCompte")
    CompteEntity toCompteEntity(CompteDto compteDto, OperationCompteDto operationCompteDto);

    @Mapping(target = "dateOperation", ignore = true)
    @Mapping(target = "numeroCompte", source = "compteDto.numeroCompte")
    @Mapping(target = "typeOperation", source = "typeOperation")
    @Mapping(target = "montantOperation", source = "compteDto.solde")
    OperationCompteDto toOperationCompteDto(CompteDto compteDto, TypeOperation typeOperation);

    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "idOperation", ignore = true)
    @Mapping(target = "compte.numeroCompte", source = "operationCompteDto.numeroCompte")
    @Mapping(target = "typeOperation", source = "operationCompteDto.typeOperation")
    @Mapping(target = "description", source = "operationCompteDto.typeOperation")
    OperationCompteEntity toOperationCompteEntity(OperationCompteDto operationCompteDto);

    default List<OperationCompteEntity> obtenirOperations(OperationCompteDto operationCompteDto) {
        List<OperationCompteEntity> list = new ArrayList<>();
        list.add(toOperationCompteEntity(operationCompteDto));
        return list;
    }
}
