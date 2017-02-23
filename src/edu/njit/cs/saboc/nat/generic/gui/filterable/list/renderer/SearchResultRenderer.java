package edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.data.NATConceptSearchResult;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class SearchResultRenderer<T extends Concept> extends BaseFilterableRenderer<NATConceptSearchResult<T>> {
    
    private final JLabel conceptNameLabel;
    private final JLabel conceptIdLabel;
    
    public SearchResultRenderer() {
        
        this.setLayout(new BorderLayout());
        
        this.conceptNameLabel = new JLabel();
        this.conceptIdLabel = new JLabel();
        
        this.conceptNameLabel.setFont(this.conceptNameLabel.getFont().deriveFont(Font.PLAIN, 16));
        this.conceptIdLabel.setFont(this.conceptIdLabel.getFont().deriveFont(Font.PLAIN, 10));
        
        this.conceptIdLabel.setForeground(Color.BLUE);
        
        this.conceptNameLabel.setOpaque(false);
        this.conceptIdLabel.setOpaque(false);
        
        JPanel conceptPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        conceptPanel.setOpaque(false);
        
        conceptPanel.add(conceptNameLabel);
        conceptPanel.add(Box.createHorizontalStrut(8));
        conceptPanel.add(conceptIdLabel);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setOpaque(false);
        
        leftPanel.add(conceptPanel, BorderLayout.CENTER);
        
        JPanel spacerPanel = new JPanel();
        spacerPanel.setOpaque(false);
        
        spacerPanel.add(Box.createHorizontalStrut(4));
        spacerPanel.add(leftPanel);
        
        this.add(spacerPanel, BorderLayout.WEST);
        
        this.setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(
            JList<? extends Filterable<NATConceptSearchResult<T>>> list, 
            Filterable<NATConceptSearchResult<T>> value, 
            int index, 
            boolean isSelected, 
            boolean cellHasFocus) {
        
        super.getListCellRendererComponent(
                list, 
                value, 
                index, 
                isSelected, 
                cellHasFocus);

        NATConceptSearchResult<T> searchResult = value.getObject();
        
        String conceptNameStr = searchResult.getConcept().getName();
        String conceptIdStr = searchResult.getConcept().getIDAsString();
        
        if(value.getCurrentFilter().isPresent()) {
            conceptNameStr = Filterable.filter(conceptNameStr, value.getCurrentFilter().get());
        }

        this.conceptNameLabel.setText(conceptNameStr);
        this.conceptIdLabel.setText(conceptIdStr);
        
        return this;
    }
}