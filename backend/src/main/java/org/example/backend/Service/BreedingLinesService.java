package org.example.backend.Service;


import lombok.AllArgsConstructor;
import org.example.backend.DTOs.BreedingLineTransfer;
import org.example.backend.DTOs.StatsTransfer;
import org.example.backend.Domains.*;
import org.example.backend.Repo.*;
import org.example.backend.ValueObjects.BreedingSettings;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class BreedingLinesService {

    private final BreedingLinesRepo breedingLinesRepo;
    private final DinosaurRepo dinosaurRepo;
    private final UsersRepo usersRepo;
    private final CreatureRepo creatureRepo;
    private final PresetsRepo presetsRepo;
    private final ComputationService computationService;
    private final BaseStatsRepo baseStatsRepo;

    public List<Dinosaur> grabDinosaurs(Long lineId) {
//        return dinosaurRepo.findByBreedingLine_BreedingLineId(lineId);
        return null;
    }


    public void createLine(BreedingLine breedingLine, Long token, Long creatureId) {
        breedingLine.setUser(usersRepo.getUsersByToken(token));
        breedingLine.setCreature(creatureRepo.getCreatureByCreatureId(creatureId));
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

    public List<BreedingLineTransfer> breedingPageSetup(Long token, Integer limit){
        List<BreedingLineTransfer> transferList = new ArrayList<>();

//        Grab the Breeding Lines
        List<BreedingLine> breedingLines = breedingLinesRepo.getBreedingLinesLimit(token, PageRequest.of(0,limit));
        for (BreedingLine bl : breedingLines){
            BreedingLineTransfer newBreedingLine = new BreedingLineTransfer();
            List<StatsTransfer> st = breedingLinesRepo.getBreedingLineMaxStats(bl.getBreedingLineId());

//            Settings, Base Stats
            List<BaseStats> baseStats = baseStatsRepo.getBaseStatsASC(bl.getCreature());
            BreedingSettings breedingSettings;
            if (bl.getPresets() == null){
                breedingSettings = new BreedingSettings(1f,1f,1f,1f,1f,1f,.14f,.4f,1f,.14f,.44f);
            }
            else{
                breedingSettings = bl.getPresets().getSettings().getBreedingSettings();
            }

//            Calculation on the stats
            for (int i = 0; i<st.size(); i++){
                StatsTransfer updateStat = st.get(i);
                updateStat.setCalcTotal(computationService.calculation(breedingSettings, baseStats.get(i), updateStat.getTotalPoints(), 1f, updateStat.getStatType()));
            }

            newBreedingLine.setColorRegions(breedingLinesRepo.getDisplayColorRegions(bl.getBreedingLineId()));
            newBreedingLine.setBreedingLineId(bl.getBreedingLineId());
            newBreedingLine.setBreedingLineNickname(bl.getLineName() != null ? bl.getLineName() : "");
            newBreedingLine.setMaxStats(st);
            newBreedingLine.setCreatureName(bl.getCreature().getCreatureName());
            transferList.add(newBreedingLine);
        }

        return transferList;
    }
    public BreedingLine grabLine(Long lineId) {
        return breedingLinesRepo.getBreedingLineByBreedingLineId(lineId);
    }

    public void deleteLine(Long lineId) {
        breedingLinesRepo.deleteById(lineId);
    }

    public String updateSettings(Long lineId, Long presetId){
        BreedingLine breedingLine = breedingLinesRepo.getBreedingLineByBreedingLineId(lineId);
        Presets presets = presetsRepo.getPresetsByPresetID(presetId);
        breedingLine.setPresets(presets);
        breedingLinesRepo.save(breedingLine);
        return "Settings have been updated";
    }


    public BreedingLineTransfer lineComputation(Long lineId){
        BreedingLine breedingLine = breedingLinesRepo.getBreedingLineByBreedingLineId(lineId);
        return computationService.lineComputation(breedingLine);
    }
}
