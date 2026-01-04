package org.example.backend.Service;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.backend.DTOs.BreedingLinePageDTO;
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
    private final ServersRepo serversRepo;
    private final ComputationService computationService;
    private final BaseStatsRepo baseStatsRepo;
    private final SettingsRepo settingsRepo;

    public List<Dinosaur> grabDinosaurs(Long lineId) {
//        return dinosaurRepo.findByBreedingLine_BreedingLineId(lineId);
        return null;
    }

    @Transactional
    public BreedingLineTransfer createLine(String lineNickname, Long token, Long creatureId, Long serverId) {
//      Saving of everything
        BreedingLine breedingLine = new BreedingLine();
        breedingLine.setLineName(lineNickname);
        breedingLine.setUser(usersRepo.getUsersByToken(token));
        breedingLine.setCreature(creatureRepo.getCreatureByCreatureId(creatureId));
        breedingLine.setServer(serversRepo.getServersByServerId(serverId));
        breedingLinesRepo.save(breedingLine);

//        Making the return object
        BreedingLineTransfer breedingLineTransfer = new BreedingLineTransfer();
        breedingLineTransfer.setBreedingLineId(breedingLine.getBreedingLineId());
        breedingLineTransfer.setBreedingLineNickname(lineNickname);
        breedingLineTransfer.setCreatureName(breedingLine.getCreature().getCreatureName());

        List<StatsTransfer> statsList = new ArrayList<>();

        for (BaseStats bs: breedingLine.getCreature().getBaseStats()){
            StatsTransfer statsTransfer = new StatsTransfer();
            statsTransfer.setStatType(bs.getStats().getStatType());
            statsTransfer.setCalcTotal(0);
            statsTransfer.setTotalPoints(0);
            statsList.add(statsTransfer);
        }
        breedingLineTransfer.setMaxStats(statsList);
        return breedingLineTransfer;
    }

    public void renameLine(Long lineId, String newName) {
        BreedingLine breedingLine = breedingLinesRepo.getReferenceById(lineId);
        breedingLine.setLineName(newName);
        breedingLinesRepo.save(breedingLine);
    }

    public BreedingLinePageDTO breedingPageSetup(Long token, Integer limit){
        BreedingLinePageDTO pageSetup = new BreedingLinePageDTO();
        List<BreedingLineTransfer> transferList = new ArrayList<>();

//        Grab the Breeding Lines
        List<BreedingLine> breedingLines = breedingLinesRepo.getBreedingLinesLimit(token, PageRequest.of(0,limit));
        for (BreedingLine bl : breedingLines){
            BreedingLineTransfer newBreedingLine = new BreedingLineTransfer();
            List<StatsTransfer> st = breedingLinesRepo.getBreedingLineMaxStats(bl.getBreedingLineId());

//            Settings, Base Stats
            List<BaseStats> baseStats = baseStatsRepo.getBaseStatsASC(bl.getCreature());
            BreedingSettings breedingSettings;
            if (bl.getServer() == null){
                breedingSettings = new BreedingSettings(1f,1f,1f,1f,1f,1f,.14f,.4f,1f,.14f,.44f);
            }
            else{
                breedingSettings = bl.getServer().getSettings().getBreedingSettings();
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


//        Grab the creatures
        pageSetup.setCreatureList(creatureRepo.getCreatures());

//        Grab the settings listed
        pageSetup.setSettingsList(serversRepo.getServersByToken(token));

        pageSetup.setBreedingLines(transferList);


        return pageSetup;
    }
    public BreedingLine grabLine(Long lineId) {
        return breedingLinesRepo.getBreedingLineByBreedingLineId(lineId);
    }

    public void deleteLine(Long lineId) {
        breedingLinesRepo.deleteById(lineId);
    }

    public String updateSettings(Long lineId, Long serverID){
        BreedingLine breedingLine = breedingLinesRepo.getBreedingLineByBreedingLineId(lineId);
        Servers server = serversRepo.getServersByServerId(serverID);
        breedingLine.setServer(server);
        breedingLinesRepo.save(breedingLine);
        return "Settings have been updated";
    }


    public BreedingLineTransfer lineComputation(Long lineId){
        BreedingLine breedingLine = breedingLinesRepo.getBreedingLineByBreedingLineId(lineId);
        return computationService.lineComputation(breedingLine);
    }
}
