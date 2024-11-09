package com.example.firstproject.repositories;

import com.example.firstproject.entities.CompteEntity;
import org.springframework.data.repository.CrudRepository;

public interface CompteRepository extends CrudRepository<CompteEntity, String> {
}
