package com.example.firstproject.services;

import com.example.firstproject.entities.CompteEntity;
import com.example.firstproject.entities.TypeOperation;
import com.example.firstproject.exceptions.RessourceExistanteException;
import com.example.firstproject.mappers.EntityDtoMapper;
import com.example.firstproject.models.CompteDto;
import com.example.firstproject.models.OperationCompteDto;
import com.example.firstproject.repositories.CompteRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Getter
@Slf4j
@RequiredArgsConstructor
@Service
public class BankService implements IBankService {

    private static final String CE_COMPTE_EXISTE_DEJA = "Ce compte existe déja";

    private final CompteRepository compteRepository;
    private final EntityDtoMapper mapper;
    private final CrediterService crediterService;
    private final DebiterService debiterService;
    private final OperationCompteService operationCompteService;

    @Override
    public CompteDto creerCompte(CompteDto compte) {
        compteRepository.findById(compte.getNumeroCompte())
                .ifPresent(compteEntity -> {
                    throw new RessourceExistanteException(CE_COMPTE_EXISTE_DEJA);
                });
        OperationCompteDto operation = mapper.toOperationCompteDto(compte.getNumeroCompte(), TypeOperation.CREDIT.getValeur(), compte.getSolde());
        CompteEntity compteEntity = mapper.toCompteEntity(compte, operation);
        return mapper.toCompteDto(compteRepository.save(compteEntity));
    }

    @Override
    public CompteDto crediter(OperationCompteDto operationCompte) {
        return crediterService.crediter(operationCompte);
    }

    @Override
    public CompteDto debiter(OperationCompteDto operationCompte) {
        return debiterService.debiter(operationCompte);
    }

    @Transactional
    public CompteDto tranferer(String numeroCompteExpediteur, String numeroCompteDestinataire, Double montantTransfert) {
        OperationCompteDto operationCredit = mapper.toOperationCompteDto(numeroCompteDestinataire, TypeOperation.CREDIT.getValeur(), montantTransfert);
        crediterService.crediter(operationCredit);

        OperationCompteDto operationDebit = mapper.toOperationCompteDto(numeroCompteExpediteur, TypeOperation.DEBIT.getValeur(), montantTransfert);
        return debiterService.debiter(operationDebit);
    }

    public CompteDto obtenirReleveCompte(String numeroCompte) {
        CompteEntity entity = operationCompteService.obtenirDetailsCompte(numeroCompte);
        return mapper.toCompteDto(entity);
    }
}