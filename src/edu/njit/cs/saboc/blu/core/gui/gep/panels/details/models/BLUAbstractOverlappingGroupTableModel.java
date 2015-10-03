package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.OverlappingGroupEntry;
import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUConfiguration;

/**
 *
 * @author Chris O
 */
public class BLUAbstractOverlappingGroupTableModel<GROUP_T extends GenericConceptGroup, CONCEPT_T> 
            extends BLUAbstractTableModel<OverlappingGroupEntry<GROUP_T, CONCEPT_T>> {
    
    protected final BLUConfiguration configuration;
    
    public BLUAbstractOverlappingGroupTableModel(BLUConfiguration configuration) {
        super(new String [] {
            configuration.getTextConfiguration().getGroupTypeName(false),
            String.format("# %s", configuration.getTextConfiguration().getConceptTypeName(true)),
            String.format("# Overlapping %s", configuration.getTextConfiguration().getConceptTypeName(true)),
        });
        
        this.configuration = configuration;
    }

    @Override
    protected Object[] createRow(OverlappingGroupEntry<GROUP_T, CONCEPT_T> item) {
        return new Object [] {
            configuration.getTextConfiguration().getGroupName(item.getOverlappingGroup()),
            item.getOverlappingGroup().getConceptCount(),
            item.getOverlappingConcepts().size()
        };
    }    
}
