package com.example.firstproject.services;

import com.example.firstproject.entities.CompteEntity;
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

import static com.example.firstproject.entities.TypeOperation.CREDIT;
import static com.example.firstproject.entities.TypeOperation.DEBIT;

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
        OperationCompteDto operation = mapper.toOperationCompteDto(compte, CREDIT);
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

        CompteDto destinataire = getCompteDto(numeroCompteDestinataire, montantTransfert);
        OperationCompteDto operationCredit = mapper.toOperationCompteDto(destinataire, CREDIT);

        // créditer le compte
        crediter(operationCredit);

        CompteDto expediteur = getCompteDto(numeroCompteExpediteur, montantTransfert);
        OperationCompteDto operationDebit = mapper.toOperationCompteDto(expediteur, DEBIT);

        // débiter le compte
        return debiter(operationDebit);
    }

    public CompteDto obtenirReleveCompte(String numeroCompte) {
        CompteEntity entity = operationCompteService.obtenirDetailsCompte(numeroCompte);
        return mapper.toCompteDto(entity);
    }

    private CompteDto getCompteDto(String numeroCompte, Double montant) {
        CompteDto compteDto = new CompteDto();
        compteDto.setNumeroCompte(numeroCompte);
        compteDto.setSolde(montant);
        return compteDto;
    }
}