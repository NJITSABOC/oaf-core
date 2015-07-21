package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.BLUAbstractTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author Chris O
 */
public abstract class AbstractConceptList<T> extends JPanel {
    private final JTable conceptListTable;
    
    private BLUAbstractTableModel<T> tableModel = null;
    
    protected AbstractConceptList() {
        super(new BorderLayout());

        this.conceptListTable = new JTable();
        this.conceptListTable.setFont(conceptListTable.getFont().deriveFont(Font.PLAIN, 14));
        
        this.add(new JScrollPane(conceptListTable), BorderLayout.CENTER);
        
        setBorderText(getBorderText(Optional.empty()));
    }
    
    public void setContents(ArrayList<T> concepts) {
        tableModel.setContents(concepts);
        
        setBorderText(getBorderText(Optional.of(concepts)));
    }
    
    public void clearContents() {
        tableModel.setContents(new ArrayList<>());
    }
    
    public void initUI() {
        conceptListTable.setModel(tableModel = createTableModel());
    }

    protected abstract BLUAbstractTableModel<T> createTableModel();
    
    protected abstract String getBorderText(Optional<ArrayList<T>> concepts);
    
    private final void setBorderText(String text) {
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), text));
    }
}
