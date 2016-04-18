package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models;

import java.util.Optional;

/**
 *
 * @author Chris O
 */
public abstract class BLUAbstractAbNNodeTableModel<NODE_T, ENTITY_T> extends BLUAbstractTableModel<ENTITY_T> {
    
    private Optional<NODE_T> currentNode = Optional.empty();
    
    public BLUAbstractAbNNodeTableModel(String [] columnNames) {
        super(columnNames);
    }
    
    public Optional<NODE_T> getCurrentNode() {
        return currentNode;
    }
    
    public void setCurrentNode(NODE_T node) {
        currentNode = Optional.of(node);
    }
    
    public void clearCurrentNode() {
        currentNode = Optional.empty();
    }
}
