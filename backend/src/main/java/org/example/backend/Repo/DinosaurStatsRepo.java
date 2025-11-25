package org.example.backend.Repo;

import org.example.backend.Domains.Dinosaur;
import org.example.backend.Domains.DinosaurStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DinosaurStatsRepo extends JpaRepository<DinosaurStats, Integer> {

    @Query("SELECT ds from DinosaurStats ds where ds.dinosaur = ?1 order by ds.stats.statType ASC")
    List<DinosaurStats> getDinosaurStatsASC(Dinosaur dino);


}
