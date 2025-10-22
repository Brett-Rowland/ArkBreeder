package org.example.backend.Repo;


import org.example.backend.Domains.BaseStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseStatsRepo extends JpaRepository<BaseStats, Integer> {

}
