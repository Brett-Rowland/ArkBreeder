package org.example.backend.Repo;


import org.example.backend.Domains.BaseStats;
import org.example.backend.Domains.Creature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseStatsRepo extends JpaRepository<BaseStats, Integer> {

    @Query("SELECT bs from BaseStats bs where bs.creature = ?1 order by bs.statType ASC")
    List<BaseStats> getBaseStatsASC(Creature creature);



}
