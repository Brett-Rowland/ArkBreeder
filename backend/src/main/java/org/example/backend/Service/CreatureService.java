package org.example.backend.Service;

import lombok.AllArgsConstructor;

import org.example.backend.Domains.ColorRegions;
import org.example.backend.Domains.Creature;
import org.example.backend.Repo.ColorRegionRepo;
import org.example.backend.Repo.CreatureRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CreatureService {

    private final CreatureRepo creatureRepo;
    private final ColorRegionRepo colorRegionRepo;

    public String createCreature(Creature creature) {
        List<ColorRegions> colorRegionsList = creature.getColorRegions();

        creatureRepo.save(creature);

        for  (ColorRegions colorRegions : colorRegionsList) {
            colorRegions.setCreature(creature);
            colorRegionRepo.save(colorRegions);
        }
        return "Created Creature Successfully";
    }


    public Creature getCreature(String creatureName) {
        return creatureRepo.getCreatureByName(creatureName);
    }

    public List<Creature> getCreatures() {
        return creatureRepo.findAll();
    }

}
