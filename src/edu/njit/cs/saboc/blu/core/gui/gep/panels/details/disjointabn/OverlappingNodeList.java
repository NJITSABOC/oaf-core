
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUDisjointConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeList;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public class OverlappingNodeList extends NodeList {

    private final BLUDisjointConfiguration config;
    
    public OverlappingNodeList(BLUDisjointConfiguration config) {
        super(new OverlappingNodeTableModel(config), config);
        
        this.config = config;
    }
    
    @Override
    protected String getBorderText(Optional<ArrayList<Node>> entities) {
        if(entities.isPresent()) {
            String base = String.format("Overlapping %s", config.getTextConfiguration().getOverlappingNodeTypeName(false));
            
            return String.format("%s (%d)", base, entities.get().size());
        } else {
            return "Overlapping";
        }
    }
}
