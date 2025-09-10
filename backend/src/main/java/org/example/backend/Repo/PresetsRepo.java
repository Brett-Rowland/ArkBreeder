package org.example.backend.Repo;

import org.example.backend.Domains.Presets;
import org.example.backend.Domains.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PresetsRepo extends JpaRepository<Presets, Long> {


    List<Presets> getPresetsByUser(Users user);

    Presets getPresetsByPresetID(Long presetID);
}
