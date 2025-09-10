package org.example.backend.Service;


import lombok.AllArgsConstructor;
import org.example.backend.Domains.BreedingLine;
import org.example.backend.Domains.Dinosaur;
import org.example.backend.Domains.Users;
import org.example.backend.Repo.BreedingLinesRepo;
import org.example.backend.Repo.DinosaurRepo;
import org.example.backend.Repo.UsersRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class BreedingLinesService {

    private final BreedingLinesRepo breedingLinesRepo;
    private final DinosaurRepo dinosaurRepo;
    private final UsersRepo usersRepo;

    public List<Dinosaur> grabDinosaurs(Long lineId) {
//        return dinosaurRepo.findByBreedingLine_BreedingLineId(lineId);
        return null;
    }


    public void createLine(BreedingLine breedingLine, Long token) {
        breedingLine.setUser(usersRepo.getUsersByToken(token));
        breedingLinesRepo.save(breedingLine);
    }

    public void renameLine(Long lineId, String newName) {
        BreedingLine breedingLine = breedingLinesRepo.getReferenceById(lineId);
        breedingLine.setLineName(newName);
        breedingLinesRepo.save(breedingLine);
    }

    public List<BreedingLine> grabLines(Long token) {
        Users user = usersRepo.getUsersByToken(token);
        return breedingLinesRepo.getBreedingLinesByUsersId(user.getUserId());
    }

    public BreedingLine grabLine(Long lineId) {
        return breedingLinesRepo.getBreedingLineByBreedingLineId(lineId);
    }

    public void deleteLine(Long lineId) {
        breedingLinesRepo.deleteById(lineId);
    }
}
