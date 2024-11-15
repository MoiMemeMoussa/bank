package com.example.firstproject.services;

import com.example.firstproject.entities.CompteEntity;
import com.example.firstproject.entities.OperationCompteEntity;
import com.example.firstproject.entities.TypeOperation;
import com.example.firstproject.exceptions.RessourceAlreadyExistException;
import com.example.firstproject.exceptions.RessourceNotFoundException;
import com.example.firstproject.exceptions.RetraitImpossibleException;
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

    private final CompteRepository compteRepository;

    private static final String NUMERO_COMPTE_EXISTE_DEJA = "Ce compte existe déja";
    private static final String NUMERO_COMPTE_EXISTE_PAS = "Ce compte n'existe pas";
    private static final String RETRAIT_IMPOSSIBLE = "Retrait impossible: votre solde est inférieure à ce montant";
    private static final String OPERATION_DEPOT = "Depot";
    private static final String OPERATION_RETRAIT = "Retrait";

    @Override
    public CompteDto creerCompte(CompteDto compteDto) {
        Optional<CompteEntity> compteEntityOptional = compteRepository.findById(compteDto.getNumeroCompte());
        if (compteEntityOptional.isPresent()) {
            throw new RessourceAlreadyExistException(NUMERO_COMPTE_EXISTE_DEJA);
        }
        CompteEntity compteEntity = creerObjetCompteEntity(compteDto);
        OperationCompteEntity operationDepotInitial = creerObjetOperationCompteEntity(TypeOperation.CREDIT.getValeur(), compteEntity.getSolde());
        List<OperationCompteEntity> list = creerListObjetOperationCompte(operationDepotInitial);
        //liaison compte - operations
        compteEntity.setOperations(list);
        operationDepotInitial.setCompte(compteEntity);
        operationDepotInitial.setDescription("depot initial");
        compteRepository.save(compteEntity);
        compteDto.setSolde(compteEntity.getSolde());
        return compteDto;
    }

    @Override
    public CompteDto crediter(OperationCompteDto operationCompteDto) {
        CompteEntity compteEntityExistant = trouverCompteParNumero(operationCompteDto.getNumeroCompte());
        OperationCompteEntity operationCompteEntity = creerObjetOperationCompteEntity(TypeOperation.CREDIT.getValeur()
                , operationCompteDto.getMontantOperation());
        List<OperationCompteEntity> list = creerListObjetOperationCompte(operationCompteEntity);
        // liaison des entites
        compteEntityExistant.setOperations(list);
        operationCompteEntity.setCompte(compteEntityExistant);
        //Mise a jour du solde
        compteEntityExistant.setSolde(compteEntityExistant.getSolde() + operationCompteEntity.getMontantOperation());
        return creerObjetCompteDto(compteRepository.save(compteEntityExistant));
    }

    @Override
    public CompteDto debiter(OperationCompteDto operationCompteDto) {
        CompteEntity compteEntityExistant = trouverCompteParNumero(operationCompteDto.getNumeroCompte());
        if (operationCompteDto.getMontantOperation() > compteEntityExistant.getSolde()) {
            throw new RetraitImpossibleException(RETRAIT_IMPOSSIBLE);
        }
        OperationCompteEntity retrait = creerObjetOperationCompteEntity(TypeOperation.DEBIT.getValeur(), operationCompteDto.getMontantOperation());
        List<OperationCompteEntity> list = creerListObjetOperationCompte(retrait);
        // liaison des entites
        compteEntityExistant.setOperations(list);
        retrait.setCompte(compteEntityExistant);
        //Mise a jour du solde
        compteEntityExistant.setSolde(compteEntityExistant.getSolde() - retrait.getMontantOperation());
        compteRepository.save(compteEntityExistant);
        return creerObjetCompteDto(compteEntityExistant);
    }

    @Transactional
    public CompteDto tranferer(String numeroCompteExpediteur, String numeroCompteDestinataire, Double montantTransfert) {
        OperationCompteDto operation = new OperationCompteDto();
        operation.setMontantOperation(montantTransfert);
        // debiter expediteur
        operation.setNumeroCompte(numeroCompteExpediteur);
        CompteDto compteExpediteur = debiter(operation);
        //crediter destinataire
        operation.setNumeroCompte(numeroCompteDestinataire);
        crediter(operation);
        return compteExpediteur;
    }

    public CompteEntity trouverCompteParNumero(String numeroCompte) {
        return compteRepository.findById(numeroCompte)
                .orElseThrow(() -> new RessourceNotFoundException(NUMERO_COMPTE_EXISTE_PAS));
    }

    public List<CompteDto> obtenirTousLesComptes() {
        List<CompteDto> list = new ArrayList<>();
        compteRepository.findAll()
                .iterator()
                .forEachRemaining(compteEntity -> {
                    CompteDto compteDto = creerObjetCompteDto(compteEntity);
                    list.add(compteDto);
                });
        return list;
    }


    private List<OperationCompteEntity> creerListObjetOperationCompte(OperationCompteEntity operationCompteEntity) {
        List<OperationCompteEntity> list = new ArrayList<>();
        list.add(operationCompteEntity);
        return list;
    }

    private OperationCompteEntity creerObjetOperationCompteEntity(String typeOperation, double montant) {
        OperationCompteEntity operationCompteEntity = new OperationCompteEntity();
        operationCompteEntity.setTypeOperation(TypeOperation.getValeur(typeOperation));
        operationCompteEntity.setMontantOperation(montant);
        if (TypeOperation.CREDIT.getValeur().equals(typeOperation)) {
            operationCompteEntity.setDescription(OPERATION_DEPOT);
        } else {
            operationCompteEntity.setDescription(OPERATION_RETRAIT);
        }
        return operationCompteEntity;
    }

    private CompteDto creerObjetCompteDto(CompteEntity compteEntity) {
        CompteDto compteDto = new CompteDto();
        compteDto.setSolde(compteEntity.getSolde());
        compteDto.setNumeroCompte(compteEntity.getNumeroCompte());
        compteDto.setFirstName(compteEntity.getFirstName());
        compteDto.setLastName(compteEntity.getLastName());
        return compteDto;
    }

    private CompteEntity creerObjetCompteEntity(CompteDto compteDto) {
        CompteEntity compteEntity = new CompteEntity();
        compteEntity.setNumeroCompte(compteDto.getNumeroCompte());
        compteEntity.setFirstName(compteDto.getFirstName());
        compteEntity.setLastName(compteDto.getLastName());
        compteEntity.setSolde(compteDto.getSolde());
        return compteEntity;
    }
}
