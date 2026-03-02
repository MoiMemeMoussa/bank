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
public class BankServiceImpl implements BankService {

    private static final String CE_COMPTE_EXISTE_DEJA = "Ce compte existe déja";
    private static final String CE_COMPTE_EXISTE_PAS = "Ce compte n'existe pas";
    private static final String SOLDE_INSUFFISANT = "Retrait impossible: Solde insuffisant";

    private final CompteRepository compteRepository;
    private final EntityDtoMapper mapper;
    private final CrediterService crediterService;
    private final DebiterService debiterService;
    private final OperationCompteService operationCompteService;

    @Override
    public CompteDto creerCompte(CompteDto compteDto) {
        compteRepository.findById(compteDto.getNumeroCompte())
                .ifPresent(compteEntity -> {
                    throw new RessourceExistanteException(CE_COMPTE_EXISTE_DEJA);
                });
        OperationCompteDto operationCompteDto = mapper.toOperationCompteDto(compteDto.getNumeroCompte(), TypeOperation.CREDIT.getValeur(), compteDto.getSolde());
        CompteEntity compteEntity = mapper.toCompteEntity(compteDto, operationCompteDto);
        return mapper.toCompteDto(compteRepository.save(compteEntity));
    }

    @Override
    public CompteDto crediter(OperationCompteDto operationCompteDto) {
        return crediterService.crediter(operationCompteDto);
    }

    @Override
    public CompteDto debiter(OperationCompteDto operationCompteDto) {
        return debiterService.debiter(operationCompteDto);
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