package com.travel.rate.repository;

import com.travel.rate.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    @Query("select c from Country c where c.num = :num")
    Country findByNum(Long num);
}
