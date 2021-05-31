package com.example.caa.repository;

import com.example.caa.model.City;
import com.example.caa.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    List<City> findByCityName(String name);

    List<City> findByCityNameIsStartingWith(String prefixName);

    List<City> findAllByState(State state);

    List<City> findAllByStateAndCityNameStartingWith(State state, String name);
}
