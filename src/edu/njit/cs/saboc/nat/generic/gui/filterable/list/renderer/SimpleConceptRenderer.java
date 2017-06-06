package edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer;

import edu.njit.cs.saboc.blu.core.gui.utils.renderers.BasicFilterableConceptRenderer;
import edu.njit.cs.saboc.blu.core.utils.filterable.renderer.BaseFilterableRenderer;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class SimpleConceptRenderer<T extends Concept> extends BaseFilterableRenderer<T> {
    
    private final BasicFilterableConceptRenderer<T> renderer;
    
    private final NATBrowserPanel<T> mainPanel;
    
    public SimpleConceptRenderer(NATBrowserPanel<T> mainPanel) {
        
        this.mainPanel = mainPanel;
        
        this.setLayout(new BorderLayout());
        
        this.renderer = new BasicFilterableConceptRenderer<>();
        this.renderer.setPreferredSize(new Dimension(-1, 38));
        
        this.add(renderer, BorderLayout.CENTER);
        
        //this.setOpaque(false);
    }
    
    public NATBrowserPanel<T> getMainPanel() {
        return mainPanel;
    }
    
    public JLabel getConceptNameLabel() {
        return renderer.getConceptNameLabel();
    }
    
    public JLabel getConceptIdLabel() {
        return renderer.getConceptIdLabel();
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
        renderer.showDetailsFor(filterableEntry);
    }
}
