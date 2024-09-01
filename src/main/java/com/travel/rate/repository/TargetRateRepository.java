package com.travel.rate.repository;

import com.travel.rate.domain.TargetRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TargetRateRepository extends JpaRepository<TargetRate, Long> {

    @Query("select t from TargetRate t where t.tagId =: tagId")
    TargetRate findByTagId(Long tagId);

}
