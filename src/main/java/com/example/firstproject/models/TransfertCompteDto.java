package com.example.firstproject.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransfertCompteDto {

    @NotNull(message = "Le champs 'numeroCompteExpediteur' ne peut pas etre null")
    @NotEmpty(message = "Le champs 'numeroCompteExpediteur' ne peut pas etre vide")
    String numeroCompteExpediteur;

    @NotNull(message = "Le champs 'numeroCompteDestinataire' ne peut pas etre null")
    @NotEmpty(message = "Le champs 'numeroCompteDestinataire' ne peut pas etre vide")
    String numeroCompteDestinataire;

    @NotNull(message = "Le champs 'montantOperation' ne peut pas etre null")
    @NotEmpty(message = "Le champs 'montantOperation' ne peut pas etre vide")
    Double montantTransfert;

}
