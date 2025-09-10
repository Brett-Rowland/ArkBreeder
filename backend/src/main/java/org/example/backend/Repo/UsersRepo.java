package org.example.backend.Repo;


import org.example.backend.Domains.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepo extends JpaRepository<Users, Long> {
    Boolean existsByUsername(String username);
    Boolean existsByToken(Long token);

    Users findByToken(Long token);

    Optional<Users> findByUsernameAndPassword(String username, String password);

    Users getUsersByToken(Long token);
}
