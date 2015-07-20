package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.AbstractConceptTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author Chris O
 */
public abstract class AbstractConceptList<T> extends JPanel {
    private final JTable conceptListTable;
    
    private AbstractConceptTableModel<T> tableModel = null;
    
    protected AbstractConceptList() {
        super(new BorderLayout());

        this.conceptListTable = new JTable();
        
        this.add(conceptListTable, BorderLayout.CENTER);
        
        setBorderText(getBorderText(Optional.empty()));
    }
    
    public void setContents(ArrayList<T> concepts) {
        tableModel.setContents(concepts);
        
        setBorderText(getBorderText(Optional.of(concepts)));
    }
    
    public void initUI() {
        conceptListTable.setModel(tableModel = createTableModel());
    }

    protected abstract AbstractConceptTableModel<T> createTableModel();
    
    protected abstract String getBorderText(Optional<ArrayList<T>> concepts);
    
    private final void setBorderText(String text) {
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), text));
    }
}
