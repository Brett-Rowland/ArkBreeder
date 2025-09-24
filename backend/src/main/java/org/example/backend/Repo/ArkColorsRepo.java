package org.example.backend.Repo;

import org.example.backend.Domains.ArkColors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArkColorsRepo extends JpaRepository<ArkColors, Integer> {
    ArkColors getArkColorsById(long id);

    void deleteArkColorsById(long id);
}
