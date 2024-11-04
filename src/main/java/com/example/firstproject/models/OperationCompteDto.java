package com.example.firstproject.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OperationCompteDto {

    @NotNull(message = "Le champs 'numeroCompte' ne peut pas etre null")
    @NotEmpty(message = "Le champs 'numeroCompte' ne peut pas etre vide")
    String numeroCompte;

    @NotNull(message = "Le champs 'montantOperation' ne peut pas etre null")
    @NotEmpty(message = "Le champs 'montantOperation' ne peut pas etre vide")
    Double montantOperation;

}
