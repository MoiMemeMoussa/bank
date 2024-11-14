package com.example.firstproject.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;

@Getter
@Setter
@Table(name = "operation")
@Entity
public class OperationCompteEntity {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idOperation;

    @Column
    private String description;

    @Column(nullable = false)
    private double montantOperation;

    @Column(nullable = false)
    private String typeOperation;

    @CreationTimestamp
    private LocalDate dateOperation;

    @LastModifiedDate
    @JsonIgnore
    private LocalDate dateModification;

    @ManyToOne
    @JsonIgnore
    private CompteEntity compte;
}
