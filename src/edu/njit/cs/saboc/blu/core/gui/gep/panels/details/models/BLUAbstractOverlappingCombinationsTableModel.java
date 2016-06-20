package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUDisjointableConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.OverlappingGroupCombinationsEntry;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Chris O
 */
public class BLUAbstractOverlappingCombinationsTableModel<
        GROUP_T extends GenericConceptGroup, 
        DISJOINTGROUP_T extends DisjointNode,
        CONCEPT_T> extends BLUAbstractTableModel<OverlappingGroupCombinationsEntry<GROUP_T, DISJOINTGROUP_T, CONCEPT_T>> {
    
    protected final BLUDisjointableConfiguration configuration;
    
    public BLUAbstractOverlappingCombinationsTableModel(BLUDisjointableConfiguration configuration) {
        super(new String[] {
            String.format("Degree of Overlap"),
            String.format("Other %s", configuration.getTextConfiguration().getGroupTypeName(true)),
            String.format("# %s", configuration.getTextConfiguration().getDisjointGroupTypeName(true)),
            String.format("# Overlapping %s", configuration.getTextConfiguration().getConceptTypeName(true))
        });

        this.configuration = configuration;
    }
    
    @Override
    protected Object[] createRow(OverlappingGroupCombinationsEntry<GROUP_T, DISJOINTGROUP_T, CONCEPT_T> item) {
        
        ArrayList<String> overlappingGroupNames = new ArrayList<>();

        item.getOtherOverlappingGroups().forEach( (GROUP_T group) -> {
            overlappingGroupNames.add(configuration.getTextConfiguration().getGroupName(group));
        });
        
        Collections.sort(overlappingGroupNames);
        
        String overlapName = overlappingGroupNames.get(0);
        
        for(int c = 1; c < overlappingGroupNames.size(); c++) {
            overlapName += (", " + overlappingGroupNames.get(c));
        }
        
        return new Object[] {
            item.getDisjointGroups().iterator().next().getOverlaps().size(),
            overlapName,
            item.getDisjointGroups().size(),
            item.getOverlappingConcepts().size()
        };
    }
    
}