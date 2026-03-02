package com.example.firstproject.services;

import com.example.firstproject.entities.CompteEntity;
import com.example.firstproject.mappers.EntityDtoMapper;
import com.example.firstproject.models.CompteDto;
import com.example.firstproject.models.OperationCompteDto;
import com.example.firstproject.repositories.CompteRepository;
import com.example.firstproject.services.ra.RaValidation;
import org.springframework.stereotype.Service;

@Service
public class CrediterService extends OperationCompteService {

    private final CompteRepository compteRepository;
    private final EntityDtoMapper mapper;
    private final RaValidation raValidation;

    public CrediterService(CompteRepository compteRepository, EntityDtoMapper mapper, RaValidation raValidation) {
        super(compteRepository, mapper);

        this.compteRepository = compteRepository;
        this.mapper = mapper;
        this.raValidation = raValidation;
    }


    public CompteDto crediter(OperationCompteDto operationCredit) {
        raValidation.validerMontant(operationCredit.getMontantOperation().toString());

        CompteEntity compteValide = obtenirCompte(operationCredit);
        compteValide.setSolde(compteValide.getSolde() + operationCredit.getMontantOperation());
        return mapper.toCompteDto(compteRepository.save(compteValide));
    }
}
