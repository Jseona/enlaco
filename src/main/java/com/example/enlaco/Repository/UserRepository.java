package com.example.enlaco.Repository;

import com.example.enlaco.Entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<MemberEntity, Integer> {
    Optional<MemberEntity> findByMemail(String memail);
}
