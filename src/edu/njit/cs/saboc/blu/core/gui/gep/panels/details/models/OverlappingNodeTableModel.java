package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.OverlappingNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUConfiguration;

/**
 *
 * @author Chris O
 */
public class OverlappingNodeTableModel extends OAFAbstractTableModel<OverlappingNodeEntry> {
    
    private final BLUConfiguration configuration;
    
    public OverlappingNodeTableModel(BLUConfiguration configuration) {
        super(new String [] {
            configuration.getTextConfiguration().getNodeTypeName(false),
            String.format("# %s", configuration.getTextConfiguration().getConceptTypeName(true)),
            String.format("# Overlapping %s", configuration.getTextConfiguration().getConceptTypeName(true)),
        });
        
        this.configuration = configuration;
    }

    @Override
    protected Object[] createRow(OverlappingNodeEntry item) {
        return new Object [] {
            item.getOverlappingGroup().getName(),
            item.getOverlappingGroup().getConceptCount(),
            item.getOverlappingConcepts().size()
        };
    }    
}
