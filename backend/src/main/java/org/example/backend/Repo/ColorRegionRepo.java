package org.example.backend.Repo;


import org.example.backend.Domains.ColorRegions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRegionRepo extends JpaRepository<ColorRegions, Integer> {
}
