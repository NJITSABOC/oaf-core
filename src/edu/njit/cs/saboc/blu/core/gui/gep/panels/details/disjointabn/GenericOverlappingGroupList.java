
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUDisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractGroupList;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public class GenericOverlappingGroupList<GROUP_T extends GenericConceptGroup> extends AbstractGroupList<GROUP_T> {

    private final BLUDisjointAbNConfiguration config;
    
    public GenericOverlappingGroupList(GenericOverlappingGroupTableModel model, BLUDisjointAbNConfiguration config) {
        super(model);
        
        this.config = config;
    }
    
    @Override
    protected String getBorderText(Optional<ArrayList<GROUP_T>> entities) {
 
        if(entities.isPresent()) {
            String base = String.format("Overlapping %s", config.getGroupTypeName(true));
            
            return String.format("%s (%d)", base, entities.get().size());
        } else {
            return "Overlapping";
        }
    }
}
