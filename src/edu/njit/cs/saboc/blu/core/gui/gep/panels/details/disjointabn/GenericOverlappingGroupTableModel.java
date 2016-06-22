package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUDisjointConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;

/**
 *
 * @author Chris O
 */
public class GenericOverlappingGroupTableModel<GROUP_T extends GenericConceptGroup> extends OAFAbstractTableModel<GROUP_T>  {
    
    private final BLUDisjointConfiguration config;
    
    public GenericOverlappingGroupTableModel(BLUDisjointConfiguration config) {
        super(new String[] { 
            String.format("Overlapping %s", config.getTextConfiguration().getOverlappingGroupTypeName(true)) 
        });
        
        this.config = config;
    }
    
    @Override
    protected Object[] createRow(GROUP_T group) {
        return new Object[] {
            config.getTextConfiguration().getOverlappingGroupName(group)
        };
    }
}
