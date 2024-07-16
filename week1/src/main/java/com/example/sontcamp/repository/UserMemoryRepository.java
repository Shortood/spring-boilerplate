package com.example.sontcamp.repository;

import com.example.sontcamp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMemoryRepository extends JpaRepository<User, Long> {

}
