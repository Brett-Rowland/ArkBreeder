package org.example.backend.Repo;

import org.example.backend.Domains.BreedingLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BreedingLinesRepo extends JpaRepository<BreedingLine, Long> {
    BreedingLine getBreedingLineByBreedingLineId(Long lineId);

    @Query("Select bl from BreedingLine bl where bl.user.userId = ?1")
    List<BreedingLine> getBreedingLinesByUsersId(Long userId);
}
