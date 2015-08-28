package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.OverlappingConceptResult;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUPartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.BLUAbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Chris O
 */
public class GenericOverlappingConceptReportTableModel <CONCEPT_T, GROUP_T extends GenericConceptGroup> 
    extends BLUAbstractTableModel<OverlappingConceptResult<CONCEPT_T, GROUP_T>>  {
    
    private final BLUPartitionedAbNConfiguration config;
    
    public GenericOverlappingConceptReportTableModel(BLUPartitionedAbNConfiguration config) {
        super(new String [] {
            String.format("Overlapping %s", config.getConceptTypeName(false)),
            "Degree of Overlap",
            String.format("Overlapping %s", config.getGroupTypeName(true)), 
            String.format("%s", config.getContainerTypeName(false))
        });
        
        this.config = config;
    }
    
    @Override
    protected Object[] createRow(OverlappingConceptResult<CONCEPT_T, GROUP_T> item) {
        
        String overlappingConceptName = config.getConceptName(item.getConcept());
        
        ArrayList<String> overlappingGroupNames = new ArrayList<>();
        
        item.getOverlappingGroups().forEach((GROUP_T group) -> {
            String groupName = config.getGroupName(group);
            int groupConceptCount = group.getConceptCount();
            
            overlappingGroupNames.add(String.format("%s (%d)", groupName, groupConceptCount));
        });
        
        Collections.sort(overlappingGroupNames);
        
        String overlappingGroups = overlappingGroupNames.get(0);
        
        for(int c = 1; c < overlappingGroupNames.size(); c++) {
            overlappingGroups += "\n" + overlappingGroupNames.get(c);
        }
        
        String areaName = config.getGroupsContainerName(item.getOverlappingGroups().iterator().next()).replaceAll(", ", "\n");
        
        return new Object[] {
            overlappingConceptName,
            item.getOverlappingGroups().size(),
            overlappingGroups,
            areaName
        };
    }
}
