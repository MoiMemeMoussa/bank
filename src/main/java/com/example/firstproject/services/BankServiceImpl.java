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

    private static final String OPERATION_DEPOT = "Depot";
    private static final String OPERATION_RETRAIT = "Retrait";
    private static final String DEPOT_INITIAL = "Depot Initial";

    private static final String SOLDE_INSUFFISANT = "Retrait impossible: Solde insuffisant";
    private static final String MONTANT_OPERATION_INCORRECT = "Le montant de l'operation doit etre superieur à 0";
    private static final String RETRAIT_IMPOSSIBLE = "Retrait impossible: votre solde est inférieure à ce montant";

    private final CompteRepository compteRepository;
    private final EntityDtoMapper mapper;

    @Override
    public CompteDto creerCompte(CompteDto compteDto) {

        validerMontantOperation(compteDto.getSolde().toString());

        Optional<CompteEntity> compteEntityOptional = compteRepository.findById(compteDto.getNumeroCompte());

        if (compteEntityOptional.isPresent()) {
            throw new RessourceAlreadyExistException(NUMERO_COMPTE_EXISTE_DEJA);
        }

        OperationCompteDto operationCompteDto = mapper.toOperationCompteDto(compteDto.getNumeroCompte()
                , TypeOperation.CREDIT.getValeur(), compteDto.getSolde());

        CompteEntity compteEntity = mapper.toCompteEntity(compteDto);
        OperationCompteEntity operationCompteEntity = mapper.toOperationCompteEntity(operationCompteDto);

        compteEntity.getOperations().add(operationCompteEntity);
        operationCompteEntity.setCompte(compteEntity);

        return mapper.toCompteDto(compteRepository.save(compteEntity));
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

        validerMontantOperation(operationCompteDto.getMontantOperation().toString());

        OperationCompteEntity operation = mapper.toOperationCompteEntity(operationCompteDto);

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

    public List<CompteDto> obtenirTousLesComptes() {
        List<CompteDto> list = new ArrayList<>();
        compteRepository.findAll()
                .iterator()
                .forEachRemaining(compteEntity -> list.add(mapper.toCompteDto(compteEntity)));
        return list;
    }

    public void validerMontantOperation(String montant) {
        if (validerMontant(montant) == 0 || validerMontant(montant) < 0) {
            throw new IncorrectOperationException(MONTANT_OPERATION_INCORRECT);
        }
    }

    private double validerMontant(String montant) {
        try {
            return Double.parseDouble(montant);
        } catch (NumberFormatException exception) {
            throw new IncorrectOperationException(MONTANT_OPERATION_INCORRECT);
        }
    }
}
