package edu.njit.cs.saboc.nat.generic.gui.layout;

import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.layout.basic.SearchAndHistoryPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.AncestorPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.ChildrenPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.DescendantsPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.FocusConceptPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.GrandchildrenPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.SiblingPanel;
import javax.swing.JSplitPane;

/**
 *
 * @author Chris O
 */
public class BasicNATAdjustableLayout extends BaseNATAdjustableLayout {
    
    private SearchAndHistoryPanel searchAndHistoryPanel;
    
    private SiblingPanel siblingPanel;

    private FocusConceptPanel focusConceptPanel;

    private AncestorPanel ancestorPanel;
    private DescendantsPanel descendantPanel;

    public BasicNATAdjustableLayout(ConceptBrowserDataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void createLayout(NATBrowserPanel mainPanel) {
        
        super.createLayout(mainPanel);
        
        searchAndHistoryPanel = new SearchAndHistoryPanel(mainPanel, getDataSource());
        siblingPanel = new SiblingPanel(mainPanel, getDataSource());
        
        focusConceptPanel = new FocusConceptPanel(mainPanel, getDataSource());
        
        ancestorPanel = new AncestorPanel(mainPanel, getDataSource());
        descendantPanel = new DescendantsPanel(mainPanel, getDataSource());
        
        
        JSplitPane leftPane = createStyledSplitPane(JSplitPane.VERTICAL_SPLIT);
        leftPane.setDividerLocation(800);
        
        leftPane.setTopComponent(searchAndHistoryPanel);
        leftPane.setBottomComponent(siblingPanel);
        
        super.setLeftPanelContents(leftPane);
        
        JSplitPane topPane = createStyledSplitPane(JSplitPane.VERTICAL_SPLIT);
        topPane.setDividerLocation(250);
        
        topPane.setTopComponent(ancestorPanel);
        
        
        JSplitPane bottomPane = createStyledSplitPane(JSplitPane.VERTICAL_SPLIT);
        bottomPane.setDividerLocation(150);
        
        bottomPane.setTopComponent(focusConceptPanel);
        bottomPane.setBottomComponent(descendantPanel);
        
        topPane.setBottomComponent(bottomPane);
        
        super.setMiddlePanelContents(topPane);
    }
}
