package edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class DetailsConceptRenderer<T extends Concept> extends BaseFilterableRenderer<T> {
    
    private final SimpleConceptRenderer<T> conceptRenderer;
    
    private final JPanel detailsPanel;
    
    private final NATBrowserPanel<T> mainPanel;
    private final ConceptBrowserDataSource<T> dataSource;
    
    public DetailsConceptRenderer(
            NATBrowserPanel<T> mainPanel,
            ConceptBrowserDataSource<T> dataSource) {
        
        this.mainPanel = mainPanel;
        this.dataSource = dataSource;
        
        this.conceptRenderer = new SimpleConceptRenderer<>(mainPanel, dataSource);
        this.conceptRenderer.setOpaque(false);
        
        this.detailsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.detailsPanel.setOpaque(false);
        
        this.detailsPanel.add(Box.createHorizontalStrut(10));
        
        this.setLayout(new BorderLayout());
        
        this.add(conceptRenderer, BorderLayout.CENTER);
        this.add(detailsPanel, BorderLayout.SOUTH);
        
        this.setPreferredSize(new Dimension(-1, 60));
    }

    public NATBrowserPanel<T> getMainPanel() {
        return mainPanel;
    }
    
    public ConceptBrowserDataSource<T> getDataSource() {
        return dataSource;
    }
    
    public void addDetailsLabel(JLabel label) {
        detailsPanel.add(label);
        detailsPanel.add(Box.createHorizontalStrut(10));
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
        conceptRenderer.showDetailsFor(filterableEntry);
    }
}
