package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.factory.NodeTypeNameFactory;

/**
 *
 * @author Chris O
 */
public class ChildNodeTableModel extends OAFAbstractTableModel<Node> {

    public ChildNodeTableModel(BLUConfiguration config, NodeTypeNameFactory nodeTypeName) {
        super(new String [] {
            String.format("Child %s", nodeTypeName.getNodeTypeName(false),
            String.format("# %s", config.getTextConfiguration().getConceptTypeName(true)))
        });
    }
    
    public Node getChildNode(int row) {
        return this.getItemAtRow(row);
    }

    @Override
    protected Object[] createRow(Node item) {
        return new Object [] {
            item.getName(),
            item.getConceptCount()
        };
    }
}
