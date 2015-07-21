
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models;

/**
 *
 * @author Chris O
 */
public abstract class BLUAbstractConceptTableModel<T> extends BLUAbstractTableModel<T> {
    public BLUAbstractConceptTableModel() {
        
    }
    
    public T getConceptAtRow(int row) {
        return super.getItemAtRow(row);
    }
}
