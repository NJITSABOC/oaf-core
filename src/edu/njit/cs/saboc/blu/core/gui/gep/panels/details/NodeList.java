package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.NodeTableModel;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public class NodeList extends AbstractEntityList<Node> {
    
    private final BLUConfiguration config;
    
    public NodeList(BLUConfiguration config) {
        super(new NodeTableModel(config));
        
        this.config = config;
    }

    @Override
    protected String getBorderText(Optional<ArrayList<Node>> entities) {
        String base = config.getTextConfiguration().getNodeTypeName(true);
        
        if(entities.isPresent()) {
            return String.format("%s (%d)", base, entities.get().size());
        } else {
            return String.format("%s (0)", base);
        }
    }
}
