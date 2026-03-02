package com.example.firstproject.services;

import com.example.firstproject.entities.CompteEntity;
import com.example.firstproject.entities.OperationCompteEntity;
import com.example.firstproject.exceptions.RessourceNonTrouveException;
import com.example.firstproject.mappers.EntityDtoMapper;
import com.example.firstproject.models.OperationCompteDto;
import com.example.firstproject.repositories.CompteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
//@NoArgsConstructor(force = true)
public class OperationCompteService {

    private static final String CE_COMPTE_EXISTE_PAS = "Ce compte n'existe pas";

    private final CompteRepository compteRepository;
    private final EntityDtoMapper mapper;

    public CompteEntity obtenirCompte(OperationCompteDto operationCompteDto) {
        CompteEntity compteEntityExistant = obtenirDetailsCompte(operationCompteDto.getNumeroCompte());

        assert this.mapper != null;
        OperationCompteEntity operation = this.mapper.toOperationCompteEntity(operationCompteDto);
        compteEntityExistant.getOperations().add(operation);
        return compteEntityExistant;
    }

    protected CompteEntity obtenirDetailsCompte(String numeroCompte) {
        return compteRepository.findById(numeroCompte)
                .orElseThrow(() -> new RessourceNonTrouveException(CE_COMPTE_EXISTE_PAS));
    }
}
