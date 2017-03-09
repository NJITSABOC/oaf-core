package edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
    
    public enum HierarchyDisplayInfo {
        Ancestors,
        Descendants,
        None
    }
    
    private final JLabel conceptNameLabel;
    private final JLabel conceptIdLabel;
    private final JLabel descendantCountLabel;
    
    private final HierarchyDisplayInfo displayInfo;
    
    private final ConceptBrowserDataSource<T> dataSource;
    
    public SimpleConceptRenderer(ConceptBrowserDataSource<T> dataSource, HierarchyDisplayInfo displayInfo) {
        
        this.displayInfo = displayInfo;
        
        this.dataSource = dataSource;
        
        this.setLayout(new BorderLayout());
        
        this.conceptNameLabel = new JLabel();
        this.conceptIdLabel = new JLabel();
        this.descendantCountLabel = new JLabel(); 
        
        this.conceptNameLabel.setFont(this.conceptNameLabel.getFont().deriveFont(Font.PLAIN, 16));
        this.conceptIdLabel.setFont(this.conceptIdLabel.getFont().deriveFont(Font.PLAIN, 10));
        this.descendantCountLabel.setFont(this.descendantCountLabel.getFont().deriveFont(Font.PLAIN, 10));
        
        this.conceptIdLabel.setForeground(Color.BLUE);
        
        this.conceptNameLabel.setOpaque(false);
        this.conceptIdLabel.setOpaque(false);
        this.descendantCountLabel.setOpaque(false);
        
        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        leftPanel.add(conceptNameLabel);
        
        
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        labelPanel.add(conceptIdLabel);
        labelPanel.add(Box.createHorizontalStrut(10));
        labelPanel.add(descendantCountLabel);
        
        labelPanel.setOpaque(false);
        
        leftPanel.add(labelPanel);
        
        leftPanel.setOpaque(false);
        
        JPanel spacerPanel = new JPanel();
        spacerPanel.setOpaque(false);
        
        spacerPanel.add(Box.createHorizontalStrut(4));
        spacerPanel.add(leftPanel);
        
        this.add(spacerPanel, BorderLayout.WEST);
        
        this.setOpaque(true);
        
        this.setPreferredSize(new Dimension(-1, 50));
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

        showDetailsFor(value);
        
        return this;
    }
    
    public void showDetailsFor(Filterable<T> filterableEntry) {
        T concept = filterableEntry.getObject();
        
        String conceptNameStr = concept.getName();
        String conceptIdStr = concept.getIDAsString();

        if (filterableEntry.getCurrentFilter().isPresent()) {
            conceptNameStr = Filterable.filter(conceptNameStr, filterableEntry.getCurrentFilter().get());
            conceptIdStr = Filterable.filter(conceptIdStr, filterableEntry.getCurrentFilter().get());
        }

        this.conceptNameLabel.setText(conceptNameStr);
        this.conceptIdLabel.setText(conceptIdStr);

        if (displayInfo.equals(HierarchyDisplayInfo.Descendants)) {
            int descendantCount = dataSource.getDescendantCount(concept);

            if (descendantCount == 0) {
                this.descendantCountLabel.setForeground(new Color(64, 200, 64));
                this.descendantCountLabel.setText("Leaf");
            } else {
                this.descendantCountLabel.setForeground(Color.BLACK);
                this.descendantCountLabel.setText(String.format("Descendants: %d", descendantCount));
            }
        }
    }
}
