package org.example.backend.Repo;


import org.example.backend.DTOs.StatsTransfer;
import org.example.backend.Domains.BaseStats;
import org.example.backend.Domains.Creature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseStatsRepo extends JpaRepository<BaseStats, Integer> {

    @Query("SELECT bs from BaseStats bs where bs.creature = ?1 order by bs.stats.statType ASC")
    List<BaseStats> getBaseStatsASC(Creature creature);


    @Query("SELECT bs from BaseStats bs where bs.creature.creatureId = ?1 order by bs.stats.statType ASC")
    List<BaseStats> getBaseStatsASCById(long creatureId);

    @Query("SELECT new org.example.backend.DTOs.StatsTransfer(bs.stats.statType) from BaseStats bs where bs.creature.creatureId = ?1 order by bs.stats.statType ASC")
    List<StatsTransfer> getStatTypeASCByID(long creatureId);
}
