package org.example.backend.Repo;

import org.example.backend.Domains.DinoColors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DinoColorsRepo extends JpaRepository<DinoColors, Integer> {
}
