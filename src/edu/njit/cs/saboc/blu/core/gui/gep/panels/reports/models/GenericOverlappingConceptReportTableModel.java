package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.models;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.OverlappingConceptResult;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUPartitionedConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Chris O
 */
public class GenericOverlappingConceptReportTableModel <CONCEPT_T, GROUP_T extends GenericConceptGroup> 
    extends OAFAbstractTableModel<OverlappingConceptResult<CONCEPT_T, GROUP_T>>  {
    
    private final BLUPartitionedConfiguration config;
    
    public GenericOverlappingConceptReportTableModel(BLUPartitionedConfiguration config) {
        super(new String [] {
            String.format("Overlapping %s", config.getTextConfiguration().getConceptTypeName(false)),
            "Degree of Overlap",
            String.format("Overlapping %s", config.getTextConfiguration().getGroupTypeName(true)), 
            String.format("%s", config.getTextConfiguration().getContainerTypeName(false))
        });
        
        this.config = config;
    }
    
    @Override
    protected Object[] createRow(OverlappingConceptResult<CONCEPT_T, GROUP_T> item) {
        
        String overlappingConceptName = config.getTextConfiguration().getConceptName(item.getConcept());
        
        ArrayList<String> overlappingGroupNames = new ArrayList<>();
        
        item.getOverlappingGroups().forEach((GROUP_T group) -> {
            String groupName = config.getTextConfiguration().getGroupName(group);
            int groupConceptCount = group.getConceptCount();
            
            overlappingGroupNames.add(String.format("%s (%d)", groupName, groupConceptCount));
        });
        
        Collections.sort(overlappingGroupNames);
        
        String overlappingGroups = overlappingGroupNames.get(0);
        
        for(int c = 1; c < overlappingGroupNames.size(); c++) {
            overlappingGroups += "\n" + overlappingGroupNames.get(c);
        }
        
        String areaName = config.getTextConfiguration().getGroupsContainerName(item.getOverlappingGroups().iterator().next()).replaceAll(", ", "\n");
        
        return new Object[] {
            overlappingConceptName,
            item.getOverlappingGroups().size(),
            overlappingGroups,
            areaName
        };
    }
}
