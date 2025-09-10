package org.example.backend.Service;

import lombok.AllArgsConstructor;

import org.example.backend.Domains.Creature;
import org.example.backend.Repo.CreatureRepo;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreatureService {

    private final CreatureRepo creatureRepo;

    public String createCreature(Creature creature) {

        creatureRepo.save(creature);

        return "Created Creature Successfully";
    }


    public Creature getCreature(String creatureName) {
        return creatureRepo.getCreatureByName(creatureName);
    }



}
