package edu.njit.cs.saboc.nat.generic.gui.layout;

import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.layout.basic.SearchAndHistoryPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.ChildrenPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.FocusConceptPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.ParentPanel;
import javax.swing.JSplitPane;

/**
 *
 * @author Chris O
 */
public class BasicNATAdjustableLayout extends BaseNATAdjustableLayout {
    
    private SearchAndHistoryPanel searchAndHistoryPanel;

    private FocusConceptPanel focusConceptPanel;

    private ParentPanel parentPanel;
    private ChildrenPanel childrenPanel;
    
    public BasicNATAdjustableLayout(ConceptBrowserDataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void createLayout(NATBrowserPanel mainPanel) {
        
        super.createLayout(mainPanel);
        
        searchAndHistoryPanel = new SearchAndHistoryPanel(mainPanel, getDataSource());
        focusConceptPanel = new FocusConceptPanel(mainPanel, getDataSource());
        parentPanel = new ParentPanel(mainPanel, getDataSource(), true);
        childrenPanel = new ChildrenPanel(mainPanel, getDataSource(), true);
        
        super.setLeftPanelContents(searchAndHistoryPanel);
        
        JSplitPane topPane = createStyledSplitPane(JSplitPane.VERTICAL_SPLIT);
        topPane.setDividerLocation(250);
        
        topPane.setTopComponent(parentPanel);
        
        
        JSplitPane bottomPane = createStyledSplitPane(JSplitPane.VERTICAL_SPLIT);
        bottomPane.setDividerLocation(150);
        
        bottomPane.setTopComponent(focusConceptPanel);
        bottomPane.setBottomComponent(childrenPanel);
        
        topPane.setBottomComponent(bottomPane);
        
        super.setMiddlePanelContents(topPane);
    }
}
