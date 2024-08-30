package com.travel.rate.repository;

import com.travel.rate.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

//      ----------------------------------- 기준선

    boolean existsByEmail(String email);
    Member findByEmail(String email);

}
