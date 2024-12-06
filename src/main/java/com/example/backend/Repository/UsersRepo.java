package com.example.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.backend.Model.Users;

@Repository
public interface UsersRepo extends JpaRepository<Users, Integer> {
    Users findByUsername(String username);
    boolean existsByUsername(String username);

    @Query("SELECT u.password FROM Users u WHERE u.username = :username")
    String findPasswordByUsername(String username);
}
