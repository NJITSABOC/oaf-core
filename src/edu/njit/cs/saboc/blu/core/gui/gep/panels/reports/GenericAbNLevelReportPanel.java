package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;
import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUPartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractEntityList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.AbNLevelReport;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public class GenericAbNLevelReportPanel <
        CONCEPT_T,
        ABN_T extends PartitionedAbstractionNetwork, 
        CONTAINER_T extends GenericGroupContainer, 
        GROUP_T extends GenericConceptGroup> extends AbNReportPanel<CONCEPT_T, GROUP_T, ABN_T> {

    private final AbstractEntityList<AbNLevelReport<CONCEPT_T, GROUP_T, CONTAINER_T>> levelReportList; 
    
    public GenericAbNLevelReportPanel(BLUPartitionedAbNConfiguration config) {
        super(config);
        
        this.setLayout(new BorderLayout());
        
        this.levelReportList = new AbstractEntityList<AbNLevelReport<CONCEPT_T, GROUP_T, CONTAINER_T>> (
                new GenericAbNLevelReportTableModel<>(config)) {
                    
            public String getBorderText(Optional<ArrayList<AbNLevelReport<CONCEPT_T, GROUP_T, CONTAINER_T>>> reports) {
                if(reports.isPresent()) {
                    return String.format("Abstraction Network Levels (%d total)", reports.get().size());
                } else {
                    return "Abstraction Network Levels";
                }
            }
        };
        
        this.add(levelReportList, BorderLayout.CENTER);
    }
    
    @Override
    public void displayAbNReport(ABN_T abn) {
        BLUPartitionedAbNConfiguration currentConfig = (BLUPartitionedAbNConfiguration)config;
        
        ArrayList<CONTAINER_T> containers = abn.getContainers();
        
        HashMap<Integer, HashSet<CONTAINER_T>> containerLevels = new HashMap<>();
        
        containers.forEach((CONTAINER_T container) -> {
            int containerLevel = currentConfig.getContainerLevel(container);
            
            if(!containerLevels.containsKey(containerLevel)) {
                containerLevels.put(containerLevel, new HashSet<>());
            }
            
            containerLevels.get(containerLevel).add(container);
        });
        
        ArrayList<AbNLevelReport<CONCEPT_T, GROUP_T, CONTAINER_T>> levelReports = new ArrayList<>();
        
        containerLevels.forEach((Integer level, HashSet<CONTAINER_T> levelContainers) -> {
            HashSet<GROUP_T> groupsAtLevel = new HashSet<>();
            
            levelContainers.forEach((CONTAINER_T container) -> {
                groupsAtLevel.addAll(currentConfig.getContainerGroupSet(container));
            });
            
            HashSet<CONCEPT_T> conceptsAtLevel = new HashSet<>();
            
            groupsAtLevel.forEach((GROUP_T group) -> {
                conceptsAtLevel.addAll(currentConfig.getGroupConceptSet(group));
            });
            
            HashSet<CONCEPT_T> overlappingConceptsAtLevel = new HashSet<>();
            
            levelContainers.forEach((CONTAINER_T container) -> {
                overlappingConceptsAtLevel.addAll(currentConfig.getContainerOverlappingConcepts(container));
            });
            
            levelReports.add(new AbNLevelReport(level, conceptsAtLevel, overlappingConceptsAtLevel, groupsAtLevel, levelContainers));
        });
        
        levelReportList.setContents(levelReports);
    }
}
