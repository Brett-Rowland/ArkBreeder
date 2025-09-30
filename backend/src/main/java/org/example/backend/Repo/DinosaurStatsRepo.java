package org.example.backend.Repo;

import org.example.backend.Domains.DinosaurStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DinosaurStatsRepo extends JpaRepository<DinosaurStats, Integer> {
}
