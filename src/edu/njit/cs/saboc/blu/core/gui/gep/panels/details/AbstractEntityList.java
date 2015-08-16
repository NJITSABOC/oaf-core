package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.BLUAbstractTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author Chris O
 */
public abstract class AbstractEntityList<T> extends JPanel {
    protected final JTable entityTable;
    
    protected BLUAbstractTableModel<T> tableModel = null;
    
    protected ArrayList<EntitySelectionListener<T>> selectionListeners = new ArrayList<>();
    
    private JPanel optionsPanel;
    
    protected AbstractEntityList(BLUAbstractTableModel<T> tableModel) {
        super(new BorderLayout());

        this.entityTable = new JTable(this.tableModel = tableModel);
        this.entityTable.setFont(entityTable.getFont().deriveFont(Font.PLAIN, 14));
        
        this.entityTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(entityTable.getSelectedRow() >= 0) {
                    T entity = tableModel.getItemAtRow(entityTable.getSelectedRow());
                    
                    selectionListeners.forEach((EntitySelectionListener<T> listener) -> {
                        listener.entitySelected(entity);
                    });
                } else {
                    selectionListeners.forEach((EntitySelectionListener<T> listener) -> {
                        listener.noEntitySelected();
                    });
                }
            }
        });
        
        this.add(new JScrollPane(entityTable), BorderLayout.CENTER);
        
        optionsPanel = new JPanel();
        this.add(optionsPanel, BorderLayout.SOUTH);
        
        setBorderText(getBorderText(Optional.empty()));
    }
    
    public void addEntitySelectionListener(EntitySelectionListener<T> listener) {
        selectionListeners.add(listener);
    }
    
    public void removeEntitySelectionListener(EntitySelectionListener<T> listener) {
        selectionListeners.remove(listener);
    }
    
    public void setContents(ArrayList<T> entities) {
        tableModel.setContents(entities);
        
        setBorderText(getBorderText(Optional.of(entities)));
    }
    
    public void clearContents() {
        tableModel.setContents(new ArrayList<>());
    }
    
    protected void addOptionButton(JButton btn) {
        optionsPanel.add(btn);
    }

    protected abstract String getBorderText(Optional<ArrayList<T>> entities);
    
    private final void setBorderText(String text) {
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), text));
    }
}
