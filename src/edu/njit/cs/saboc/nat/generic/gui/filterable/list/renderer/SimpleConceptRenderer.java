package edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer;

import edu.njit.cs.saboc.blu.core.utils.filterable.renderer.BaseFilterableRenderer;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
public class SimpleConceptRenderer<T extends Concept> extends BaseFilterableRenderer<T> {
    
    private final JLabel conceptNameLabel;
    private final JLabel conceptIdLabel;
    
    private final NATBrowserPanel<T> mainPanel;
    private final ConceptBrowserDataSource<T> dataSource;
    
    public SimpleConceptRenderer(
            NATBrowserPanel<T> mainPanel,
            ConceptBrowserDataSource<T> dataSource) {
        
        this.mainPanel = mainPanel;
        this.dataSource = dataSource;
        
        this.setLayout(new BorderLayout());
        
        this.conceptNameLabel = new JLabel();
        this.conceptIdLabel = new JLabel();
        
        this.conceptNameLabel.setFont(this.conceptNameLabel.getFont().deriveFont(Font.PLAIN, 16));
        this.conceptIdLabel.setFont(this.conceptIdLabel.getFont().deriveFont(Font.PLAIN, 10));
        
        this.conceptIdLabel.setForeground(Color.BLUE);
        
        this.conceptNameLabel.setOpaque(false);
        this.conceptIdLabel.setOpaque(false);
        
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(conceptNameLabel);
        leftPanel.add(Box.createHorizontalStrut(10));
        leftPanel.add(conceptIdLabel);
        
        leftPanel.setOpaque(false);
        
        JPanel spacerPanel = new JPanel();
        spacerPanel.setOpaque(false);
        
        spacerPanel.add(Box.createHorizontalStrut(4));
        spacerPanel.add(leftPanel);
        
        this.add(spacerPanel, BorderLayout.WEST);
        
        this.setOpaque(true);
        
        this.setPreferredSize(new Dimension(-1, 38));
    }
    
    public NATBrowserPanel<T> getMainPanel() {
        return mainPanel;
    }
    
    public ConceptBrowserDataSource<T> getDataSource() {
        return dataSource;
    }
    
    public JLabel getConceptNameLabel() {
        return conceptNameLabel;
    }
    
    public JLabel getConceptIdLabel() {
        return conceptIdLabel;
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
    
    
    @Override
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
    }
}
