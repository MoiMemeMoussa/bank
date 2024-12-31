package com.example.firstproject.mappers;

import com.example.firstproject.entities.CompteEntity;
import com.example.firstproject.entities.OperationCompteEntity;
import com.example.firstproject.models.CompteDto;
import com.example.firstproject.models.OperationCompteDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EntityDtoMapper {

    @Mapping(target = "operations.numeroCompte", ignore = true)
    CompteDto toCompteDto(CompteEntity compteEntity);

    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "operations", ignore = true)
    CompteEntity toCompteEntity(CompteDto compteDto);

    @Mapping(target = "dateOperation", ignore = true)
    @Mapping(target = "numeroCompte", source = "numeroCompte")
    @Mapping(target = "typeOperation", source = "typeOperation")
    @Mapping(target = "montantOperation", source = "montantOperation")
    OperationCompteDto toOperationCompteDto(String numeroCompte, String typeOperation,Double montantOperation);

    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "idOperation", ignore = true)
    @Mapping(target = "dateOperation", ignore = true)
    @Mapping(target = "compte.numeroCompte", source = "operationCompteDto.numeroCompte")
    @Mapping(target = "typeOperation", source = "operationCompteDto.typeOperation")
    @Mapping(target = "description", source = "operationCompteDto.typeOperation")
    OperationCompteEntity toOperationCompteEntity(OperationCompteDto operationCompteDto);

}
