package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUDisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.BLUAbstractTableModel;

/**
 *
 * @author Chris O
 */
public class GenericOverlappingGroupTableModel<GROUP_T extends GenericConceptGroup> extends BLUAbstractTableModel<GROUP_T>  {
    
    private final BLUDisjointAbNConfiguration config;
    
    public GenericOverlappingGroupTableModel(BLUDisjointAbNConfiguration config) {
        super(new String[] { 
            String.format("Overlapping %s", config.getOverlappingGroupTypeName(true)) 
        });
        
        this.config = config;
    }
    
    @Override
    protected Object[] createRow(GROUP_T group) {
        return new Object[] {
            config.getOverlappingGroupName(group)
        };
    }
}
