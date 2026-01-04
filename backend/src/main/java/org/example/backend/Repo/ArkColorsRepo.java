package org.example.backend.Repo;

import org.example.backend.Domains.ArkColors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ArkColorsRepo extends JpaRepository<ArkColors, Integer> {
    ArkColors getArkColorsByColorId(long id);

    void deleteArkColorsByColorId(long id);


    @Query("Select ac from ArkColors ac where ac.colorId in (?1)")
    List<ArkColors> getArkColorsByColorIds(Set<Long> colorIds);
}
