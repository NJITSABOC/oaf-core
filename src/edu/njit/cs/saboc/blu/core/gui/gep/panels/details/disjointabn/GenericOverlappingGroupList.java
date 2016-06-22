
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUDisjointConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeList;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public class GenericOverlappingGroupList<GROUP_T extends GenericConceptGroup> extends NodeList<GROUP_T> {

    private final BLUDisjointConfiguration config;
    
    public GenericOverlappingGroupList(GenericOverlappingGroupTableModel model, BLUDisjointConfiguration config) {
        super(model);
        
        this.config = config;
    }
    
    @Override
    protected String getBorderText(Optional<ArrayList<GROUP_T>> entities) {
 
        if(entities.isPresent()) {
            String base = String.format("Overlapping %s", config.getTextConfiguration().getGroupTypeName(true));
            
            return String.format("%s (%d)", base, entities.get().size());
        } else {
            return "Overlapping";
        }
    }
}
