package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUDisjointConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;

/**
 *
 * @author Chris O
 */
public class OverlappingNodeTableModel extends OAFAbstractTableModel<Node>  {
    
    private final BLUDisjointConfiguration config;
    
    public OverlappingNodeTableModel(BLUDisjointConfiguration config) {
        super(new String[] { 
            String.format("Overlapping %s", config.getTextConfiguration().getOverlappingNodeTypeName(true)) 
        });
        
        this.config = config;
    }
    
    @Override
    protected Object[] createRow(Node node) {
        return new Object[] {
            node.getName()
        };
    }
}
