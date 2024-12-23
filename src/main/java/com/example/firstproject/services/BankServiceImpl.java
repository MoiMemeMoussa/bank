package com.example.firstproject.services;

import com.example.firstproject.entities.CompteEntity;
import com.example.firstproject.entities.OperationCompteEntity;
import com.example.firstproject.entities.TypeOperation;
import com.example.firstproject.exceptions.IncorrectOperationException;
import com.example.firstproject.exceptions.RessourceAlreadyExistException;
import com.example.firstproject.exceptions.RessourceNotFoundException;
import com.example.firstproject.exceptions.RetraitImpossibleException;
import com.example.firstproject.mappers.EntityDtoMapper;
import com.example.firstproject.models.CompteDto;
import com.example.firstproject.models.OperationCompteDto;
import com.example.firstproject.repositories.CompteRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Slf4j
@RequiredArgsConstructor
@Service
public class BankServiceImpl implements BankService {

    private static final String NUMERO_COMPTE_EXISTE_DEJA = "Ce compte existe déja";
    private static final String NUMERO_COMPTE_EXISTE_PAS = "Ce compte n'existe pas";
    private static final String RETRAIT_IMPOSSIBLE = "Retrait impossible: votre solde est inférieure à ce montant";
    private static final String OPERATION_DEPOT = "Depot";
    private static final String OPERATION_RETRAIT = "Retrait";
    private static final String DEPOT_INITIAL = "Depot Initial";
    private static final String SOLDE_INSUFFISANT = "Retrait impossible: Solde insuffisant";
    private static final String MONTANT_TRANSFERT_INCORRECT = "Le montant du transfert doit etre superieur à 0";

    private final CompteRepository compteRepository;
    private final EntityDtoMapper mapper;


    @Override
    public CompteDto creerCompte(CompteDto compteDto) {
        Optional<CompteEntity> compteEntityOptional = compteRepository.findById(compteDto.getNumeroCompte());

        if (compteEntityOptional.isPresent()) {
            throw new RessourceAlreadyExistException(NUMERO_COMPTE_EXISTE_DEJA);
        }

        OperationCompteDto operationCompteDto = mapper.toOperationCompteDto(compteDto);
        operationCompteDto.setTypeOperation(TypeOperation.CREDIT);

        CompteEntity compteEntity = mapper.toCompteEntity(compteDto);
        OperationCompteEntity operationCompteEntity = mapper.toOperationCompteEntity(operationCompteDto);

        compteEntity.getOperations().add(operationCompteEntity);

        CompteDto reponse = mapper.toCompteDto(compteRepository.save(compteEntity));

        reponse.setOperations(null); // don t show operations in the result
        return reponse;
    }

    @Transactional
    public CompteDto tranferer(String numeroCompteExpediteur, String numeroCompteDestinataire, Double montantTransfert) {
        OperationCompteDto operationCredit = new OperationCompteDto();
        operationCredit.setMontantOperation(montantTransfert);
        operationCredit.setNumeroCompte(numeroCompteDestinataire);
        operationCredit.setTypeOperation(TypeOperation.CREDIT);

        crediterOuDebiter(operationCredit);

        OperationCompteDto operationDebit = new OperationCompteDto();
        operationDebit.setMontantOperation(montantTransfert);
        operationDebit.setNumeroCompte(numeroCompteExpediteur);
        operationDebit.setTypeOperation(TypeOperation.DEBIT);

        return crediterOuDebiter(operationDebit);
    }

    @Override
    public CompteDto crediterOuDebiter(OperationCompteDto operationCompteDto) {

        if (operationCompteDto.getMontantOperation() == 0) {
            throw new IncorrectOperationException(MONTANT_TRANSFERT_INCORRECT);
        }

        OperationCompteEntity operation = mapper.toOperationCompteEntity(operationCompteDto);

        // liaison des entites
        CompteEntity compteEntityExistant = findCompteByNumero(operationCompteDto.getNumeroCompte());
        compteEntityExistant.getOperations().add(operation);

        if (operationCompteDto.getTypeOperation().equals(TypeOperation.CREDIT)) {
            compteEntityExistant.setSolde(compteEntityExistant.getSolde() + operation.getMontantOperation());
        }

        if (operationCompteDto.getTypeOperation().equals(TypeOperation.DEBIT)) {
            if (compteEntityExistant.getSolde() < operationCompteDto.getMontantOperation()) {
                throw new RetraitImpossibleException(SOLDE_INSUFFISANT);
            }
            compteEntityExistant.setSolde(compteEntityExistant.getSolde() - operation.getMontantOperation());
        }
        CompteDto reponse = mapper.toCompteDto(compteRepository.save(compteEntityExistant));
        reponse.setOperations(null); //dont  send informations about operations
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


    public List<CompteDto> obtenirTousLesComptes() {
        List<CompteDto> list = new ArrayList<>();
        compteRepository.findAll()
                .iterator()
                .forEachRemaining(compteEntity -> list.add(mapper.toCompteDto(compteEntity)));
        return list;
    }
}
