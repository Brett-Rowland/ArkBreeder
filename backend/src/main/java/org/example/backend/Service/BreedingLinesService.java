package org.example.backend.Service;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.backend.DTOs.BreedingLinePageDTO;
import org.example.backend.DTOs.BreedingLineDTO;
import org.example.backend.DTOs.StatsDTO;
import org.example.backend.Domains.*;
import org.example.backend.Repo.*;
import org.example.backend.ValueObjects.BreedingSettings;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * BreedingLinesService.
 *
 * Service layer responsible for managing breeding line workflows.
 *
 * Responsibilities:
 * - create, rename, delete breeding lines
 * - build the Breeding Lines page initialization payload
 * - update the server/settings context applied to a breeding line
 *
 * This service coordinates persistence via repositories and uses
 * {@link ComputationService} to derive display-ready computed values.
 *
 * Notes:
 * - This service returns DTOs for UI consumption rather than exposing
 *   domain entities directly.
 * - Some computed values are derived dynamically each request (not persisted).
 */

@AllArgsConstructor
@Service
public class BreedingLinesService {

    private final BreedingLinesRepo breedingLinesRepo;
    private final UsersRepo usersRepo;
    private final CreatureRepo creatureRepo;
    private final ServersRepo serversRepo;
    private final ComputationService computationService;
    private final BaseStatsRepo baseStatsRepo;

    /**
     * Creates a new breeding line for a user.
     *
     * Persisted effects:
     * - creates and saves a {@link BreedingLine} linked to:
     *   - user (identified by token)
     *   - creature species (creatureId)
     *   - server configuration (serverId)
     *
     * Returns:
     * - {@link BreedingLineDTO} initialized for UI display
     *   (includes empty/zeroed stat placeholders based on the creature's base stats).
     *
     * Notes:
     * - This method is transactional to ensure the breeding line is saved
     *   atomically along with its relationships.
     *
     * @param lineNickname display name for the breeding line
     * @param token authentication token identifying the user
     * @param creatureId creature/species ID tied to the line
     * @param serverId server configuration applied to computations for this line
     * @return initialized {@link BreedingLineDTO} for UI consumption
     */

    @Transactional
    public BreedingLineDTO createLine(String lineNickname, Long token, Long creatureId, Long serverId) {
//      Saving of everything
        BreedingLine breedingLine = new BreedingLine();
        breedingLine.setLineName(lineNickname);
        breedingLine.setUser(usersRepo.getUsersByToken(token));
        breedingLine.setCreature(creatureRepo.getCreatureByCreatureId(creatureId));
        breedingLine.setServer(serversRepo.getServersByServerId(serverId));
        breedingLinesRepo.save(breedingLine);

//        Making the return object
        BreedingLineDTO breedingLineDTO = new BreedingLineDTO();
        breedingLineDTO.setBreedingLineId(breedingLine.getBreedingLineId());
        breedingLineDTO.setBreedingLineNickname(lineNickname);
        breedingLineDTO.setCreatureName(breedingLine.getCreature().getCreatureName());

//        Specific Stats Creature Contains
        List<StatsDTO> statsList = new ArrayList<>();

        for (BaseStats bs: breedingLine.getCreature().getBaseStats()){
            StatsDTO statsDTO = new StatsDTO();
            statsDTO.setStatType(bs.getStats().getStatType());
            statsDTO.setCalcTotal(0);
            statsDTO.setTotalPoints(0);
            statsList.add(statsDTO);
        }
        breedingLineDTO.setMaxStats(statsList);
        return breedingLineDTO;
    }

    /**
     * Renames an existing breeding line.
     *
     * Persisted effects:
     * - updates {@link BreedingLine#lineName} and saves the entity.
     *
     * @param lineId breeding line identifier
     * @param newName new display name for the breeding line
     */
    public void renameLine(Long lineId, String newName) {
        BreedingLine breedingLine = breedingLinesRepo.getReferenceById(lineId);
        breedingLine.setLineName(newName);
        breedingLinesRepo.save(breedingLine);
    }

    /**
     * Builds the Breeding Lines page payload for a given user.
     *
     * This method aggregates:
     * - a paginated list of breeding lines owned by the user
     * - computed max stat values for each breeding line
     * - representative display color regions for each breeding line
     * - the list of validated creatures for selection
     * - the list of available servers/settings for selection
     *
     * Computation rules:
     * - max points per stat type are retrieved via {@link BreedingLinesRepo#getBreedingLineMaxStats(Long)}
     * - final computed stat totals are derived using {@link ComputationService#calculation}
     * - if a breeding line has no server assigned, default {@link BreedingSettings} values are used
     *
     * @param token authentication token identifying the user
     * @param limit number of breeding lines to return (pagination size)
     * @return {@link BreedingLinePageDTO} containing all data required for the page
     */
    public BreedingLinePageDTO breedingPageSetup(Long token, Integer limit){
        BreedingLinePageDTO pageSetup = new BreedingLinePageDTO();
        List<BreedingLineDTO> transferList = new ArrayList<>();

//        Grab the Breeding Lines
        List<BreedingLine> breedingLines = breedingLinesRepo.getBreedingLinesLimit(token, PageRequest.of(0,limit));
        for (BreedingLine bl : breedingLines){
            BreedingLineDTO newBreedingLine = new BreedingLineDTO();
            List<StatsDTO> st = breedingLinesRepo.getBreedingLineMaxStats(bl.getBreedingLineId());

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
                StatsDTO updateStat = st.get(i);
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
        pageSetup.setCreatureList(creatureRepo.getValidatedCreatures());

//        Grab the settings listed
        pageSetup.setSettingsList(serversRepo.getServersByToken(token));

        pageSetup.setBreedingLines(transferList);


        return pageSetup;
    }
    /**
     * Deletes a breeding line.
     *
     * Persisted effects:
     * - removes the breeding line record (hard delete).
     *
     * Notes:
     * - If you later switch to soft delete (deleted flag),
     *   this method should be updated accordingly.
     *
     * @param lineId breeding line identifier
     */
    public void deleteLine(Long lineId) {
        breedingLinesRepo.deleteById(lineId);
    }

    /**
     * Updates the server/settings context for a breeding line.
     *
     * Persisted effects:
     * - updates {@link BreedingLine#server} and saves the entity.
     *
     * @param lineId breeding line identifier
     * @param serverID server configuration identifier
     */
    public void updateSettings(Long lineId, Long serverID){
        BreedingLine breedingLine = breedingLinesRepo.getBreedingLineByBreedingLineId(lineId);
        Servers server = serversRepo.getServersByServerId(serverID);
        breedingLine.setServer(server);
        breedingLinesRepo.save(breedingLine);
    }
}
