package com.example.firstproject.models;

import com.example.firstproject.entities.TypeOperation;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OperationCompteDto {

    @NotNull(message = "Le champs 'numeroCompte' ne peut pas etre null")
    @NotEmpty(message = "Le champs 'numeroCompte' ne peut pas etre vide")
    String numeroCompte;

    @NotNull(message = "Le champs 'montantOperation' ne peut pas etre null")
    @NotEmpty(message = "Le champs 'montantOperation' ne peut pas etre vide")
    Double montantOperation;

    @NotNull(message = "Le champs 'typeOperation' ne peut pas etre null")
    @NotEmpty(message = "Le champs 'typeOperation' ne peut pas etre vide")
    TypeOperation typeOperation;

    LocalDate dateOperation;

}
