package com.example.caa.repository;

import com.example.caa.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Short> {
    
   Country findFirstByCountryName(String name);

   Country findByCountryCodeIso2(String codeIso2);

}
