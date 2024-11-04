package com.example.firstproject.utils;

import com.example.firstproject.models.CompteDto;

public class ResourceTestUtils {

    public static CompteDto getCompte() {
        CompteDto compteDto = new CompteDto();
        compteDto.setFirstName("Alex");
        compteDto.setLastName("Zong");
        compteDto.setNumeroCompte("FR-19852402-1");
        compteDto.setSolde(5_000.00);
        return compteDto;
    }
}
