package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.BLUAbstractAbNNodeTableModel;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public abstract class AbstractAbNNodeEntityList<NODE_T, ENTITY_T> extends AbstractEntityList<ENTITY_T>  {
    
    private Optional<NODE_T> currentNode = Optional.empty();
    
    public AbstractAbNNodeEntityList(BLUAbstractAbNNodeTableModel<NODE_T, ENTITY_T> tableModel) {
        super(tableModel);
    }
    
    public void setCurrentNode(NODE_T node) {
        this.currentNode = Optional.of(node);
        
        ((BLUAbstractAbNNodeTableModel<NODE_T, ENTITY_T>)super.getTableModel()).setCurrentNode(node);
    }
    
    public void clearCurrentNode() {
        this.currentNode = Optional.empty();
        
        ((BLUAbstractAbNNodeTableModel<NODE_T, ENTITY_T>)super.getTableModel()).clearCurrentNode();
    }
}
