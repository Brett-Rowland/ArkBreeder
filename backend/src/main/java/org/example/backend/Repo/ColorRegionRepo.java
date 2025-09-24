package org.example.backend.Repo;


import org.example.backend.Domains.ColorRegions;
import org.example.backend.Domains.Creature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRegionRepo extends JpaRepository<ColorRegions, Integer> {
    ColorRegions getColorRegionsByDCRId(Long DCRId);

    void deleteColorRegionsByDCRId(Long DCRId);

    List<ColorRegions> getColorRegionsByCreature(Creature creature);
}
