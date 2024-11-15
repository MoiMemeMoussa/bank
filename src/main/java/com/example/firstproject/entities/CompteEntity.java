package com.example.firstproject.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Table(name = "compte")
@Entity
public class CompteEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private String numeroCompte;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private double solde;

    @CreationTimestamp
    private LocalDate dateCreation;

    @LastModifiedDate
    @JsonIgnore
    private LocalDate dateModification;

    @OneToMany(mappedBy = "compte", cascade = CascadeType.ALL)
    private List<OperationCompteEntity> operations = new ArrayList<>();

}
