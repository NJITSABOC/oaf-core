package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import java.util.Optional;

/**
 *
 * @author Chris O
 * 
 * @param <ENTITY_T> The type of entity being displayed in the table (e.g., concept or relationship)
 */
public abstract class BLUAbstractAbNNodeTableModel<ENTITY_T> extends BLUAbstractTableModel<ENTITY_T> {
    
    private Optional<Node> currentNode = Optional.empty();
    
    public BLUAbstractAbNNodeTableModel(String [] columnNames) {
        super(columnNames);
    }
    
    public Optional<Node> getCurrentNode() {
        return currentNode;
    }
    
    public void setCurrentNode(Node node) {
        currentNode = Optional.of(node);
    }
    
    public void clearCurrentNode() {
        currentNode = Optional.empty();
    }
}
