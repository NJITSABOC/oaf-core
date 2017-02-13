package edu.njit.cs.saboc.nat.generic.gui.layout;

import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.layout.basic.SearchAndHistoryPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.ChildrenPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.FocusConceptPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.ParentsPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public class NATBasicLayout extends NATLayout {
    
    private SearchAndHistoryPanel searchAndHistoryPanel;

    private FocusConceptPanel focusConceptPanel;
    
    private ParentsPanel parentPanel;
    private ChildrenPanel childrenPanel;
    
    public NATBasicLayout(ConceptBrowserDataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void createLayout(NATBrowserPanel mainPanel) {
        
        searchAndHistoryPanel = new SearchAndHistoryPanel(mainPanel, getDataSource());
        
        focusConceptPanel = new FocusConceptPanel(mainPanel, getDataSource());
       
        parentPanel = new ParentsPanel(mainPanel, getDataSource(), true);

        childrenPanel = new ChildrenPanel(mainPanel, getDataSource(), true);

        JPanel centerPanel = new JPanel(new GridLayout(3, 1));
        
        centerPanel.add(parentPanel);
        centerPanel.add(focusConceptPanel);
        centerPanel.add(childrenPanel);
        
        this.setLayout(new BorderLayout());
        
        JPanel leftPanel = new JPanel(new BorderLayout());
        
        leftPanel.add(searchAndHistoryPanel, BorderLayout.CENTER);
        
        this.add(leftPanel, BorderLayout.WEST);
        
        this.add(centerPanel, BorderLayout.CENTER);
    }
}
