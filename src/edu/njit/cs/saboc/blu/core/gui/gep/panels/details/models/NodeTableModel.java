package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;

/**
 *
 * @author Chris O
 */
public class NodeTableModel extends OAFAbstractTableModel<Node> {
    
    public NodeTableModel(AbNConfiguration configuration) {
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
