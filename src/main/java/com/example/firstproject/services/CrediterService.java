package com.example.firstproject.services;

import com.example.firstproject.entities.CompteEntity;
import com.example.firstproject.entities.TypeOperation;
import com.example.firstproject.mappers.EntityDtoMapper;
import com.example.firstproject.models.CompteDto;
import com.example.firstproject.models.OperationCompteDto;
import com.example.firstproject.repositories.CompteRepository;
import com.example.firstproject.services.ra.ReglesValidation;
import org.springframework.stereotype.Service;

import static com.example.firstproject.entities.TypeOperation.CREDIT;

@Service
public class CrediterService extends OperationCompteService {

    private final CompteRepository compteRepository;
    private final EntityDtoMapper mapper;
    private final ReglesValidation reglesValidation;

    public CrediterService(CompteRepository compteRepository, EntityDtoMapper mapper, ReglesValidation reglesValidation) {
        super(compteRepository, mapper);

        this.compteRepository = compteRepository;
        this.mapper = mapper;
        this.reglesValidation = reglesValidation;
    }


    public CompteDto crediter(OperationCompteDto operationCredit) {
        reglesValidation.validerMontant(operationCredit.getMontantOperation().toString());

        operationCredit.setTypeOperation(CREDIT);
        CompteEntity compteValide = obtenirCompte(operationCredit);

        compteValide.setSolde(compteValide.getSolde() + operationCredit.getMontantOperation());
        return mapper.toCompteDto(compteRepository.save(compteValide));
    }
}
