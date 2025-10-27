package org.example.backend.Service;

import lombok.AllArgsConstructor;

import org.example.backend.DTOs.CreatureInput;
import org.example.backend.Domains.BaseStats;
import org.example.backend.Domains.ColorRegions;
import org.example.backend.Domains.Creature;
import org.example.backend.Domains.StatModifiers;
import org.example.backend.Repo.BaseStatsRepo;
import org.example.backend.Repo.ColorRegionRepo;
import org.example.backend.Repo.CreatureRepo;
import org.example.backend.Repo.StatModifierRepo;
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
    private final StatModifierRepo statModifierRepo;

    void inputCreature(CreatureInput creature){
        Creature newCreature = new Creature();
        List<ColorRegions> colorRegions = new ArrayList<>();
        List<BaseStats> stats = new ArrayList<>();
        List<StatModifiers> statModifiers = new ArrayList<>();
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

        float[][] statModTransfer = creature.getStatModifiers();
        for (float[] ints: statModTransfer){
            StatModifiers statModifier = new StatModifiers();
            statModifier.setCreature(newCreature);
            statModifier.setStatAdditive(ints[0]);
            statModifier.setStatMultiplicand(ints[1]);
            statModifier.setStats(BaseStats.STATS.values()[(int) ints[2]]);
            statModifiers.add(statModifier);
        }

        newCreature.setColorRegions(colorRegions);
        newCreature.setBaseStats(stats);
        newCreature.setStatModifiers(statModifiers);
        creatureRepo.save(newCreature);
        colorRegionRepo.saveAll(colorRegions);
        baseStatsRepo.saveAll(stats);
        statModifierRepo.saveAll(statModifiers);
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

        List<StatModifiers> statModifiers = creature.getStatModifiers();
        for (StatModifiers sm : statModifiers){
            sm.setCreature(creature);
        }


        creatureRepo.save(creature);
        colorRegionRepo.saveAll(colorRegions);
        baseStatsRepo.saveAll(baseStats);
        statModifierRepo.saveAll(statModifiers);
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
