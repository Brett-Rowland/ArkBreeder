package org.example.backend.Service;

import lombok.AllArgsConstructor;

import org.example.backend.DTOs.CreatureInput;
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

    void inputCreature(CreatureInput creature){
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
        float [][] statsTransfer = creature.getStats();
        for (float[] floats : statsTransfer) {
            BaseStats bs = new BaseStats();
            bs.setCreature(newCreature);
            bs.setStatType(BaseStats.STATS.values()[(int)floats[4]]);
            bs.setStats(new StatsDefaults(floats[0], floats[1], floats[2], floats[3]));
            stats.add(bs);
        }


        newCreature.setColorRegions(colorRegions);
        newCreature.setBaseStats(stats);
        creatureRepo.save(newCreature);
        colorRegionRepo.saveAll(colorRegions);
        baseStatsRepo.saveAll(stats);
    }

    public void inputCreatureOld(Creature creature){

//        Grab the stats and color regions
        List<ColorRegions> colorRegions = creature.getColorRegions();

        for (ColorRegions cr : colorRegions){
            cr.setCreature(creature);
        }

        List<BaseStats> baseStats = creature.getBaseStats();
        for (BaseStats bs : baseStats){
            bs.setCreature(creature);
        }


        creatureRepo.save(creature);
        colorRegionRepo.saveAll(colorRegions);
        baseStatsRepo.saveAll(baseStats);
    }

    public String createCreature(CreatureInput creature) {
        inputCreature(creature);
        return "Created Creature Successfully";
    }


    public Creature getCreature(String creatureName) {
        return creatureRepo.getCreatureByName(creatureName);
    }

    public List<Creature> getCreatures() {
        return creatureRepo.findAll();
    }

    public String createCreatureList(List<CreatureInput> creatures) {
        for (CreatureInput creature : creatures) {
            inputCreature(creature);
        }
        return "Created Creatures Successfully";
    }

    public String createCreatureListOld(List<Creature> creatures){
        for (Creature creature : creatures){
            inputCreatureOld(creature);
        }
        return "Created Creatures Succesfully";
    }
}
