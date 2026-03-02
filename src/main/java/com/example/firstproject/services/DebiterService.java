package com.example.firstproject.services;

import com.example.firstproject.entities.CompteEntity;
import com.example.firstproject.mappers.EntityDtoMapper;
import com.example.firstproject.models.CompteDto;
import com.example.firstproject.models.OperationCompteDto;
import com.example.firstproject.repositories.CompteRepository;
import com.example.firstproject.services.ra.RaValidation;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
public class DebiterService extends OperationCompteService {

    private final CompteRepository compteRepository;
    private final EntityDtoMapper mapper;
    private final RaValidation raValidation;

    public DebiterService(CompteRepository compteRepository, EntityDtoMapper mapper, RaValidation raValidation) {
        super(compteRepository, mapper);

        this.compteRepository = compteRepository;
        this.mapper = mapper;
        this.raValidation = raValidation;
    }

    public CompteDto debiter(OperationCompteDto operationDebit) {
        raValidation.validerMontant(operationDebit.getMontantOperation().toString());

        CompteEntity compteValide = obtenirCompte(operationDebit);
        compteValide.setSolde(compteValide.getSolde() - operationDebit.getMontantOperation());
        CompteDto reponse = mapper.toCompteDto(compteRepository.save(compteValide));
        //reponse.setOperations(null);
        return reponse;
    }
}
