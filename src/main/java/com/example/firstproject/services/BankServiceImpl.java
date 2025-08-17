package com.example.firstproject.services;

import com.example.firstproject.entities.CompteEntity;
import com.example.firstproject.entities.OperationCompteEntity;
import com.example.firstproject.entities.TypeOperation;
import com.example.firstproject.exceptions.RessourceAlreadyExistException;
import com.example.firstproject.exceptions.RessourceNotFoundException;
import com.example.firstproject.exceptions.RetraitImpossibleException;
import com.example.firstproject.mappers.EntityDtoMapper;
import com.example.firstproject.models.CompteDto;
import com.example.firstproject.models.OperationCompteDto;
import com.example.firstproject.repositories.CompteRepository;
import com.example.firstproject.services.ra.RaValidation;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Getter
@Slf4j
@RequiredArgsConstructor
@Service
public class BankServiceImpl implements BankService {

    private static final String DEPOT_INITIAL = "Depot Initial";

    private static final String NUMERO_COMPTE_EXISTE_DEJA = "Ce compte existe déja";
    private static final String NUMERO_COMPTE_EXISTE_PAS = "Ce compte n'existe pas";

    private static final String OPERATION_DEPOT = "Depot";
    private static final String OPERATION_RETRAIT = "Retrait";

    private static final String RETRAIT_IMPOSSIBLE = "Retrait impossible: votre solde est inférieure à ce montant";
    private static final String SOLDE_INSUFFISANT = "Retrait impossible: Solde insuffisant";

    private final RaValidation raValidation;
    private final CompteRepository compteRepository;
    private final EntityDtoMapper mapper;

    @Override
    public CompteDto creerCompte(CompteDto compteDto) {

        raValidation.validerMontant(compteDto.getSolde().toString());

        compteRepository.findById(compteDto.getNumeroCompte())
                .ifPresent(compteEntity -> {
                    throw new RessourceAlreadyExistException(NUMERO_COMPTE_EXISTE_DEJA);
                });

        OperationCompteDto operationCompteDto = mapper.toOperationCompteDto(compteDto.getNumeroCompte(), TypeOperation.CREDIT.getValeur(), compteDto.getSolde());
        OperationCompteEntity operationCompteEntity = mapper.toOperationCompteEntity(operationCompteDto);

        CompteEntity compteEntity = mapper.toCompteEntity(compteDto);
        setOperation(compteEntity, operationCompteEntity);
        return mapper.toCompteDto(compteRepository.save(compteEntity));
    }

    private void setOperation(CompteEntity compteEntity, OperationCompteEntity operationCompteEntity) {
        List<OperationCompteEntity> list = new ArrayList<>();
        list.add(operationCompteEntity);
        compteEntity.setOperations(list);
    }

    @Transactional
    public CompteDto tranferer(String numeroCompteExpediteur, String numeroCompteDestinataire, Double montantTransfert) {

        OperationCompteDto operationCredit = mapper.toOperationCompteDto(numeroCompteDestinataire, TypeOperation.CREDIT.getValeur(), montantTransfert);

        crediterOuDebiter(operationCredit);

        OperationCompteDto operationDebit = mapper.toOperationCompteDto(numeroCompteExpediteur, TypeOperation.DEBIT.getValeur(), montantTransfert);

        return crediterOuDebiter(operationDebit);
    }

    @Override
    public CompteDto crediterOuDebiter(OperationCompteDto operationCompteDto) {

        raValidation.validerMontant(operationCompteDto.getMontantOperation().toString());

        OperationCompteEntity operation = mapper.toOperationCompteEntity(operationCompteDto);

        CompteEntity compteEntityExistant = findCompteByNumero(operationCompteDto.getNumeroCompte());

        compteEntityExistant.getOperations().add(operation);

        if (operationCompteDto.getTypeOperation().equals(TypeOperation.CREDIT)) {
            compteEntityExistant.setSolde(compteEntityExistant.getSolde() + operation.getMontantOperation());
        } else {

            if (compteEntityExistant.getSolde() < operationCompteDto.getMontantOperation()) {
                throw new RetraitImpossibleException(SOLDE_INSUFFISANT);
            }
            compteEntityExistant.setSolde(compteEntityExistant.getSolde() - operation.getMontantOperation());
        }

        CompteDto reponse = mapper.toCompteDto(compteRepository.save(compteEntityExistant));

        reponse.setOperations(null); //dont send informations about operations

        return reponse;
    }

    public CompteDto obtenirReleveCompte(String numeroCompte) {
        CompteEntity entity = findCompteByNumero(numeroCompte);
        return mapper.toCompteDto(entity);
    }

    private CompteEntity findCompteByNumero(String numeroCompte) {
        return compteRepository.findById(numeroCompte)
                .orElseThrow(() -> new RessourceNotFoundException(NUMERO_COMPTE_EXISTE_PAS));
    }

}