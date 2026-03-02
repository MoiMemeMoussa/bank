package com.example.firstproject.services;

import com.example.firstproject.entities.CompteEntity;
import com.example.firstproject.mappers.EntityDtoMapper;
import com.example.firstproject.models.CompteDto;
import com.example.firstproject.models.OperationCompteDto;
import com.example.firstproject.repositories.CompteRepository;
import com.example.firstproject.services.ra.RaValidation;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrediterServiceImpl extends OperationCompteService {

    private final CompteRepository compteRepository;
    private final EntityDtoMapper mapper;
    private final RaValidation raValidation;

    public CompteDto crediter(OperationCompteDto operationCredit) {
        raValidation.validerMontant(operationCredit.getMontantOperation().toString());

        CompteEntity compteValide = obtenirCompte(operationCredit);
        compteValide.setSolde(compteValide.getSolde() + operationCredit.getMontantOperation());
        CompteDto reponse = mapper.toCompteDto(compteRepository.save(compteValide));
       // reponse.setOperations(null);
        return reponse;
    }
}
