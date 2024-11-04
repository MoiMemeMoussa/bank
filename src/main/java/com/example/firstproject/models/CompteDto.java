package com.example.firstproject.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompteDto {

    @NotNull(message = "Le champs 'numeroCompte' ne peut pas etre null")
    @NotEmpty(message = "Le champs 'numeroCompte' ne peut pas etre vide")
    String numeroCompte;

    @NotNull(message = "Le champs 'firstName' ne peut pas etre null")
    @NotEmpty(message = "Le champs 'firstName' ne peut pas etre vide")
    String firstName;

    @NotNull(message = "Le champs 'lastName' ne peut pas etre null")
    @NotEmpty(message = "Le champs 'lastName' ne peut pas etre vide")
    String lastName;

    @NotNull(message = "Le champs 'solde' ne peut pas etre null")
    @Min(value = 0, message = "Le minimum pour le champs 'solde' est 0")
    Double solde;

}
