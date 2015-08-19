package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.OverlappingGroupEntry;
import SnomedShared.generic.GenericConceptGroup;

/**
 *
 * @author Chris O
 */
public class BLUAbstractOverlappingGroupTableModel<GROUP_T extends GenericConceptGroup, CONCEPT_T> 
            extends BLUAbstractTableModel<OverlappingGroupEntry<GROUP_T, CONCEPT_T>> {
    
    protected final BLUAbNConfiguration configuration;
    
    public BLUAbstractOverlappingGroupTableModel(BLUAbNConfiguration configuration) {
        super(new String [] {
            configuration.getGroupTypeName(false),
            String.format("# %s", configuration.getConceptTypeName(true)),
            String.format("# Overlapping %s", configuration.getConceptTypeName(true)),
        });
        
        this.configuration = configuration;
    }

    @Override
    protected Object[] createRow(OverlappingGroupEntry<GROUP_T, CONCEPT_T> item) {
        return new Object [] {
            configuration.getGroupName(item.getOverlappingGroup()),
            item.getOverlappingGroup().getConceptCount(),
            item.getOverlappingConcepts().size()
        };
    }    
}
