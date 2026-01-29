package org.example.backend.Repo;


import org.example.backend.Domains.BreedingSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BreedingSettingsRepo extends JpaRepository<BreedingSettings, Long> {



}
