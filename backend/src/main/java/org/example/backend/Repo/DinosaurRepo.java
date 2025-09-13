package org.example.backend.Repo;

import org.example.backend.Domains.Dinosaur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DinosaurRepo extends JpaRepository<Dinosaur, Long> {
    Dinosaur getDinosaurByDinoId(Long DinosaurId);
//    List<Dinosaur> findByBreedingLine_BreedingLineId(Long lineId);

}
