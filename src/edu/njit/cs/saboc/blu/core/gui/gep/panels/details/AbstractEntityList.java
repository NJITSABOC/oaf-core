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
public abstract class AbstractEntityList<T> extends JPanel {
    private final JTable entityTable;
    
    private BLUAbstractTableModel<T> tableModel = null;
    
    protected AbstractEntityList() {
        super(new BorderLayout());

        this.entityTable = new JTable();
        this.entityTable.setFont(entityTable.getFont().deriveFont(Font.PLAIN, 14));
        
        this.add(new JScrollPane(entityTable), BorderLayout.CENTER);
        
        setBorderText(getBorderText(Optional.empty()));
    }
    
    public void setContents(ArrayList<T> entities) {
        tableModel.setContents(entities);
        
        setBorderText(getBorderText(Optional.of(entities)));
    }
    
    public void clearContents() {
        tableModel.setContents(new ArrayList<>());
    }
    
    public void initUI() {
        entityTable.setModel(tableModel = createTableModel());
    }

    protected abstract BLUAbstractTableModel<T> createTableModel();
    
    protected abstract String getBorderText(Optional<ArrayList<T>> entities);
    
    private final void setBorderText(String text) {
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), text));
    }
}
