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
public class RegionsService {

    private final ColorRegionRepo colorRegionRepo;
    private final CreatureRepo creatureRepo;

    public void createColorRegion(ColorRegions colorRegions, long creatureId) throws Exception {
        Creature creature = creatureRepo.findById(creatureId).orElse(null);

        if (creature == null){
            throw new Exception("No Creature Found");
        }

        colorRegions.setCreature(creature);
        colorRegionRepo.save(colorRegions);
    }

    public ColorRegions grabColorRegion(long colorRegionID){
        return colorRegionRepo.getColorRegionsByDCRId(colorRegionID);
    }

    public void updateColorRegion(ColorRegions newColorRegions, long colorRegionID) {
        ColorRegions colorRegions = colorRegionRepo.getColorRegionsByDCRId(colorRegionID);
        colorRegions.setRegionDescriptor(newColorRegions.getRegionDescriptor());
        colorRegions.setColorRegion(newColorRegions.getColorRegion());
        colorRegionRepo.save(colorRegions);
    }

    public void deleteColorRegion(long colorRegionID) {
        colorRegionRepo.deleteColorRegionsByDCRId(colorRegionID);
    }

    public List<ColorRegions> getAllColorRegions(){
        return colorRegionRepo.findAll();
    }

    public List<ColorRegions> creaturesColorRegions(long creatureID){
        return colorRegionRepo.getColorRegionsByCreature(creatureRepo.getCreatureByCreatureId(creatureID));
    }
}
