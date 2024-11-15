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

    private final CompteRepository compteRepository;
    private final EntityDtoMapper mapper;


    @Override
    public CompteDto creerCompte(CompteDto compteDto) {
        Optional<CompteEntity> compteEntityOptional = compteRepository.findById(compteDto.getNumeroCompte());

        if (compteEntityOptional.isPresent()) {
            throw new RessourceAlreadyExistException(NUMERO_COMPTE_EXISTE_DEJA);
        }

        OperationCompteDto operationCompteDto = mapper.toOperationCompteDto(compteDto);
        operationCompteDto.setTypeOperation(TypeOperation.CREDIT.getValeur());
        OperationCompteEntity operationCompteEntity = mapper.toOperationCompteEntity(operationCompteDto, "depot initial");

        List<OperationCompteEntity> list = new ArrayList<>();
        list.add(operationCompteEntity);

        CompteEntity compteEntity = mapper.toCompteEntity(compteDto);

        compteEntity.setOperations(list);
        operationCompteEntity.setCompte(compteEntity);

        return mapper.toCompteDto(compteRepository.save(compteEntity));
    }

    @Override
    public CompteDto crediter(OperationCompteDto operationCompteDto) {
        CompteEntity compteEntityExistant = findCompteByNumero(operationCompteDto.getNumeroCompte());
        operationCompteDto.setTypeOperation(TypeOperation.CREDIT.getValeur());
        OperationCompteEntity operationCredit = mapper.toOperationCompteEntity(operationCompteDto, TypeOperation.CREDIT.getValeur());
        List<OperationCompteEntity> list = new ArrayList<>();
        list.add(operationCredit);
        // liaison des entites
        compteEntityExistant.setOperations(list);
        operationCredit.setCompte(compteEntityExistant);
        //Mise a jour du solde
        compteEntityExistant.setSolde(compteEntityExistant.getSolde() + operationCredit.getMontantOperation());
        CompteEntity resulatEntity = compteRepository.save(compteEntityExistant);
        return mapper.toCompteDto(resulatEntity);
    }

    @Override
    public CompteDto debiter(OperationCompteDto operationCompteDto) {
        CompteEntity compteEntityExistant = findCompteByNumero(operationCompteDto.getNumeroCompte());
        if (operationCompteDto.getMontantOperation() > compteEntityExistant.getSolde()) {
            throw new RetraitImpossibleException(RETRAIT_IMPOSSIBLE);
        }
        operationCompteDto.setTypeOperation(TypeOperation.DEBIT.getValeur());
        OperationCompteEntity operationRetrait = mapper.toOperationCompteEntity(operationCompteDto, TypeOperation.DEBIT.getValeur());

        List<OperationCompteEntity> list = new ArrayList<>();
        list.add(operationRetrait);

        compteEntityExistant.setOperations(list);

        operationRetrait.setCompte(compteEntityExistant);
        compteEntityExistant.setSolde(compteEntityExistant.getSolde() - operationRetrait.getMontantOperation());
        return mapper.toCompteDto(compteRepository.save(compteEntityExistant));
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
