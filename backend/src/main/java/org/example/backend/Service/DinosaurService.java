package org.example.backend.Service;


import org.example.backend.Domains.BreedingLine;
import org.example.backend.Domains.Dinosaur;
import org.example.backend.Repo.BreedingLinesRepo;
import org.example.backend.Repo.DinosaurRepo;
import org.springframework.stereotype.Service;

@Service
public class DinosaurService {

    BreedingLinesRepo breedingLinesRepo;
    DinosaurRepo dinosaurRepo;


    public void createDinosaur(Dinosaur dinosaur, Long lineId){
        BreedingLine breedingLine = breedingLinesRepo.getBreedingLineByBreedingLineId(lineId);
        dinosaur.setBreedingLineId(breedingLine);
        dinosaurRepo.save(dinosaur);
    }

    public void updateDinosaur(Dinosaur dinosaur, Long dinoId){
        Dinosaur dino = dinosaurRepo.getDinosaurByDinoId(dinoId);
        dino.setDinosaur_nickname(dinosaur.getDinosaur_nickname());
        dino.setHealth(dinosaur.getHealth());
        dino.setDinoColors(dinosaur.getDinoColors());
        dino.setFood(dinosaur.getFood());
        dino.setMelee(dinosaur.getMelee());
        dino.setWeight(dinosaur.getWeight());
        dino.setStamina(dinosaur.getStamina());
        dino.setOxygen(dinosaur.getOxygen());
        dinosaurRepo.save(dinosaur);

    }

    public void deleteDinosaur(Long dinoId){
        dinosaurRepo.deleteById(dinoId);
    }

    public void renameDinosaur(Long dinoId, String newName) {
        Dinosaur dino = dinosaurRepo.getDinosaurByDinoId(dinoId);
        dino.setDinosaur_nickname(newName);
        dinosaurRepo.save(dino);
    }

    public Dinosaur grabDino(Long dinoId) {
        return dinosaurRepo.getDinosaurByDinoId(dinoId);
    }
}
