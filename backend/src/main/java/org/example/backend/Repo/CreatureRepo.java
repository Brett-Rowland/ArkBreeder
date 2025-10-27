package org.example.backend.Repo;

import org.example.backend.Domains.BaseStats;
import org.example.backend.Domains.Creature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreatureRepo extends JpaRepository<Creature,Long> {
    Creature getCreatureByCreatureName(String creatureName);

    @Query("SELECT cs from Creature cs Where UPPER(cs.creatureName) = UPPER(?1)")
    Creature getCreatureByName(String creatureName);

    Creature getCreatureByCreatureId(long creatureId);



}
