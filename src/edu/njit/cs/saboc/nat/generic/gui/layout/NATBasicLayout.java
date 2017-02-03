package edu.njit.cs.saboc.nat.generic.gui.layout;

import edu.njit.cs.saboc.nat.generic.GenericNATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.ChildrenPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.FocusConceptPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.ParentPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.search.SearchPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public class NATBasicLayout extends NATLayout {
    
    private SearchPanel searchPanel;
    
    private FocusConceptPanel focusConceptPanel;
    
    private ParentPanel parentPanel;
    private ChildrenPanel childrenPanel;
    
    public NATBasicLayout(ConceptBrowserDataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void createLayout(GenericNATBrowserPanel mainPanel) {
        
        searchPanel = new SearchPanel(mainPanel, getDataSource());
        searchPanel.setPreferredSize(new Dimension(450, -1));
        
        
        focusConceptPanel = new FocusConceptPanel(mainPanel, getDataSource());
       
        parentPanel = new ParentPanel(mainPanel, getDataSource(), true);

        childrenPanel = new ChildrenPanel(mainPanel, getDataSource(), true);
        

        JPanel centerPanel = new JPanel(new GridLayout(3, 1));
        
        centerPanel.add(parentPanel);
        centerPanel.add(focusConceptPanel);
        centerPanel.add(childrenPanel);
        
        this.setLayout(new BorderLayout());
        
        
        this.add(searchPanel, BorderLayout.WEST);
        
        
        this.add(centerPanel, BorderLayout.CENTER);
    }

}
