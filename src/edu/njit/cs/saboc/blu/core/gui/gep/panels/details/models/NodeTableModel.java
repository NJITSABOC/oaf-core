package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUConfiguration;

/**
 *
 * @author Chris O
 */
public class NodeTableModel extends OAFAbstractTableModel<Node> {
    
    public NodeTableModel(BLUConfiguration configuration) {
        super(
                new String[] {
                    configuration.getTextConfiguration().getNodeTypeName(false),
                    String.format("# %s", configuration.getTextConfiguration().getConceptTypeName(true))
            });
    }

    @Override
    protected Object[] createRow(Node node) {
        return new Object [] {
            node.getName(),
            node.getConceptCount()
        };
    }
}
