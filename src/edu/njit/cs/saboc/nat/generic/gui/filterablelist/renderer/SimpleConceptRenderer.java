package edu.njit.cs.saboc.nat.generic.gui.filterablelist.renderer;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class SimpleConceptRenderer<T extends Concept> extends BaseFilterableRenderer<T> {
    
    private final JLabel conceptNameLabel;
    private final JLabel conceptIdLabel;
    
    public SimpleConceptRenderer() {
        
        this.setLayout(new BorderLayout());
        
        this.conceptNameLabel = new JLabel();
        this.conceptIdLabel = new JLabel();
        
        this.conceptNameLabel.setFont(this.conceptNameLabel.getFont().deriveFont(Font.PLAIN, 16));
        this.conceptIdLabel.setFont(this.conceptIdLabel.getFont().deriveFont(Font.PLAIN, 10));
        
        this.conceptIdLabel.setForeground(Color.BLUE);
        
        this.conceptNameLabel.setOpaque(false);
        this.conceptIdLabel.setOpaque(false);
        
        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        leftPanel.add(conceptNameLabel);
        leftPanel.add(conceptIdLabel);
        
        leftPanel.setOpaque(false);
        
        JPanel spacerPanel = new JPanel();
        spacerPanel.setOpaque(false);
        
        spacerPanel.add(Box.createHorizontalStrut(4));
        spacerPanel.add(leftPanel);
        
        this.add(spacerPanel, BorderLayout.WEST);
        
        this.setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(
            JList<? extends Filterable<T>> list, 
            Filterable<T> value, 
            int index, 
            boolean isSelected, 
            boolean cellHasFocus) {
        
        super.getListCellRendererComponent(
                list, 
                value, 
                index, 
                isSelected, 
                cellHasFocus);

        T concept = value.getObject();
        
        String conceptNameStr = concept.getName();
        String conceptIdStr = concept.getIDAsString();
        
        if(value.getCurrentFilter().isPresent()) {
            conceptNameStr = Filterable.filter(conceptNameStr, value.getCurrentFilter().get());
            conceptIdStr = Filterable.filter(conceptIdStr, value.getCurrentFilter().get());
        }
        
        this.conceptNameLabel.setText(conceptNameStr);
        this.conceptIdLabel.setText(conceptIdStr);
        
        return this;
    }
}
