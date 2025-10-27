package org.example.backend.Repo;

import org.example.backend.Domains.StatModifiers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatModifierRepo extends JpaRepository<StatModifiers, Long> {
}
