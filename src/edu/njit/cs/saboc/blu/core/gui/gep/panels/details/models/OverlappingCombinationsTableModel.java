package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.OverlappingNodeCombinationsEntry;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Chris O
 */
public class OverlappingCombinationsTableModel extends OAFAbstractTableModel<OverlappingNodeCombinationsEntry> {
    
    private final BLUConfiguration configuration;
    
    public OverlappingCombinationsTableModel(BLUConfiguration configuration) {
        
        super(new String[] {
            String.format("Degree of Overlap"),
            String.format("Other %s", configuration.getTextConfiguration().getGroupTypeName(true)),
            String.format("# %s", configuration.getTextConfiguration().getDisjointGroupTypeName(true)),
            String.format("# Overlapping %s", configuration.getTextConfiguration().getConceptTypeName(true))
        });

        this.configuration = configuration;
    }
    
    @Override
    protected Object[] createRow(OverlappingNodeCombinationsEntry entry) {
        
        ArrayList<String> overlappingGroupNames = new ArrayList<>();

        entry.getOtherOverlappingNodes().forEach( (node) -> {
            overlappingGroupNames.add(node.getName());
        });
        
        Collections.sort(overlappingGroupNames);
        
        String overlapName = overlappingGroupNames.get(0);
        
        for(int c = 1; c < overlappingGroupNames.size(); c++) {
            overlapName += (", " + overlappingGroupNames.get(c));
        }
        
        return new Object[] {
            entry.getDisjointNodes().iterator().next().getOverlaps().size(),
            overlapName,
            entry.getDisjointNodes().size(),
            entry.getOverlappingConcepts().size()
        };
    }
    
}