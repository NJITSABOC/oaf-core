package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.blu.core.gui.iconmanager.ImageManager;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.FocusConceptManager;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.JButton;



public abstract class BaseNATPanel<T extends Concept> extends NATLayoutPanel<T> {
    
    private final ConceptBrowserDataSource<T> dataSource;
    
    private final FocusConceptManager<T> focusConceptManager;

    public BaseNATPanel(
            NATBrowserPanel<T> mainPanel, 
            ConceptBrowserDataSource<T> dataSource) {
        
        super(mainPanel);
        
        this.focusConceptManager = mainPanel.getFocusConceptManager();
        this.dataSource = dataSource;
    }
    
    public FocusConceptManager<T> getFocusConceptManager() {
        return focusConceptManager;
    }
    
    public ConceptBrowserDataSource<T> getDataSource() {
        return dataSource;
    }

    public static JButton createFilterButton(ActionListener action) {
        JButton filterButton = new JButton();
        filterButton.setPreferredSize(new Dimension(24, 24));
        filterButton.setIcon(ImageManager.getImageManager().getIcon("filter.png"));
        filterButton.setToolTipText("Filter these entries");
        filterButton.addActionListener(action);
        
        return filterButton;
    }
}
