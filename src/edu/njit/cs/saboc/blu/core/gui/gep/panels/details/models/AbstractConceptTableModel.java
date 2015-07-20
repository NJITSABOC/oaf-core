package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Chris O
 */
public abstract class AbstractConceptTableModel<T> extends AbstractTableModel {
    
    protected final ArrayList<T> concepts = new ArrayList<>();
    
    private final String[] columnNames;
    
    private Object[][] data = new Object[0][0];
            
    public AbstractConceptTableModel() {
        this.columnNames = getColumnNames();
    }
    
    protected abstract String[] getColumnNames();
    
    protected abstract Object[] createRow(T concept);
    
    public void setContents(ArrayList<T> concepts) {
        this.concepts.clear();
        this.concepts.addAll(concepts);
        
        data = new Object[concepts.size()][this.getColumnCount()];
        
        int r = 0;
        
        for(T concept : concepts) {
            data[r] = createRow(concept);
            r++;
        }
        
        this.fireTableDataChanged();
    }
    
    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
    
    public T getConceptAtRow(int row) {
        return concepts.get(row);
    }
}
