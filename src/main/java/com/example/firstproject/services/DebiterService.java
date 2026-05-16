package com.example.firstproject.services;

import com.example.firstproject.entities.CompteEntity;
import com.example.firstproject.exceptions.RetraitImpossibleException;
import com.example.firstproject.mappers.EntityDtoMapper;
import com.example.firstproject.models.CompteDto;
import com.example.firstproject.models.OperationCompteDto;
import com.example.firstproject.repositories.CompteRepository;
import com.example.firstproject.services.ra.ReglesValidation;
import org.springframework.stereotype.Service;

@Service
public class DebiterService extends OperationCompteService {

    private static final String SOLDE_INSUFFISANT = "Retrait impossible: Solde insuffisant";

    private final CompteRepository compteRepository;
    private final EntityDtoMapper mapper;
    private final ReglesValidation reglesValidation;

    public DebiterService(CompteRepository compteRepository, EntityDtoMapper mapper, ReglesValidation reglesValidation) {
        super(compteRepository, mapper);

        this.compteRepository = compteRepository;
        this.mapper = mapper;
        this.reglesValidation = reglesValidation;
    }

    public CompteDto debiter(OperationCompteDto operationDebit) {
        reglesValidation.validerMontant(operationDebit.getMontantOperation().toString());
        CompteEntity compteValide = obtenirCompte(operationDebit);
        verfierMontantDebit(operationDebit.getMontantOperation(), compteValide);
        compteValide.setSolde(compteValide.getSolde() - operationDebit.getMontantOperation());
        return mapper.toCompteDto(compteRepository.save(compteValide));
    }

    private void verfierMontantDebit(Double montantDebit, CompteEntity compteEntity) {
        if (compteEntity.getSolde() < montantDebit) {
            throw new RetraitImpossibleException(SOLDE_INSUFFISANT);
        }
    }
}
