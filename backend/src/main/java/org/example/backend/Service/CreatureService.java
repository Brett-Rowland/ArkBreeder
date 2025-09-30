package org.example.backend.Service;

import lombok.AllArgsConstructor;

import org.example.backend.DTOs.CreatureTransfer;
import org.example.backend.Domains.BaseStats;
import org.example.backend.Domains.ColorRegions;
import org.example.backend.Domains.Creature;
import org.example.backend.Repo.BaseStatsRepo;
import org.example.backend.Repo.ColorRegionRepo;
import org.example.backend.Repo.CreatureRepo;
import org.example.backend.ValueObjects.StatsDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CreatureService {

    private final CreatureRepo creatureRepo;
    private final ColorRegionRepo colorRegionRepo;
    private final BaseStatsRepo baseStatsRepo;

    void inputCreature(CreatureTransfer creature){
        Creature newCreature = new Creature();
        List<ColorRegions> colorRegions = new ArrayList<>();
        List<BaseStats> stats = new ArrayList<>();
        newCreature.setCreatureName(creature.getCreatureName());
        int [][] colorRegionsTransfer = creature.getColorRegions();
//        Make new color regions objects as a list
        for (int i = 0; i<colorRegionsTransfer.length; i++){
            ColorRegions cr =  new ColorRegions();
            cr.setCreature(newCreature);

            cr.setColorRegion(colorRegionsTransfer[i][0]);
            cr.setRegionDescriptor(creature.getColorRegionDescriptor()[i]);
            cr.setVisibility(ColorRegions.Visibility.values()[colorRegionsTransfer[i][1]]);
            colorRegions.add(cr);
        }
//        Make new base stats as a list
        int [][] statsTransfer = creature.getStats();
        for (int[] ints : statsTransfer) {
            BaseStats bs = new BaseStats();
            bs.setCreature(newCreature);
            bs.setStats(BaseStats.STATS.values()[ints[2]]);
            bs.setValue(new StatsDefaults(ints[0], ints[1]));
        }

        newCreature.setColorRegions(colorRegions);
        newCreature.setBaseStats(stats);
        creatureRepo.save(newCreature);
        colorRegionRepo.saveAll(colorRegions);
        baseStatsRepo.saveAll(stats);


    }

    public String createCreature(CreatureTransfer creature) {
        inputCreature(creature);
        return "Created Creature Successfully";
    }


    public Creature getCreature(String creatureName) {
        return creatureRepo.getCreatureByName(creatureName);
    }

    public List<Creature> getCreatures() {
        return creatureRepo.findAll();
    }

    public String createCreatureList(List<CreatureTransfer> creatures) {
        for (CreatureTransfer creature : creatures) {
            inputCreature(creature);
        }
        return "Created Creatures Successfully";
    }

}
