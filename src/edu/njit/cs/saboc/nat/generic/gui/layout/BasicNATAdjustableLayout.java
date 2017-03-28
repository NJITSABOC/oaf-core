package edu.njit.cs.saboc.nat.generic.gui.layout;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.layout.basic.SearchAndHistoryPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.AncestorPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.DescendantsPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.FocusConceptPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.SiblingPanel;
import javax.swing.JSplitPane;

/**
 * A basic, adjustable layout for the NAT interface
 * 
 * @author Chris O
 * @param <T>
 */
public class BasicNATAdjustableLayout<T extends Concept> extends BaseNATAdjustableLayout<T> {
    
    private SearchAndHistoryPanel<T> searchAndHistoryPanel;
    
    private SiblingPanel<T> siblingPanel;

    private FocusConceptPanel<T> focusConceptPanel;

    private AncestorPanel<T> ancestorPanel;
    private DescendantsPanel<T> descendantPanel;

    public BasicNATAdjustableLayout(ConceptBrowserDataSource<T> dataSource) {
        super(dataSource);
    }

    @Override
    public void createLayout(NATBrowserPanel mainPanel) {
        
        super.createLayout(mainPanel);
        
        searchAndHistoryPanel = new SearchAndHistoryPanel<>(mainPanel, getDataSource());
        siblingPanel = new SiblingPanel<>(mainPanel, getDataSource());
        
        focusConceptPanel = new FocusConceptPanel<>(mainPanel, getDataSource());
        
        ancestorPanel = new AncestorPanel<>(mainPanel, getDataSource());
        descendantPanel = new DescendantsPanel<>(mainPanel, getDataSource());
        
        JSplitPane leftPane = BaseNATAdjustableLayout.createStyledSplitPane(JSplitPane.VERTICAL_SPLIT);
        leftPane.setDividerLocation(800);
        
        leftPane.setTopComponent(searchAndHistoryPanel);
        leftPane.setBottomComponent(siblingPanel);
        
        super.setLeftPanelContents(leftPane);
        
        JSplitPane topPane = BaseNATAdjustableLayout.createStyledSplitPane(JSplitPane.VERTICAL_SPLIT);
        topPane.setDividerLocation(250);
        
        topPane.setTopComponent(ancestorPanel);
        
        
        JSplitPane bottomPane = BaseNATAdjustableLayout.createStyledSplitPane(JSplitPane.VERTICAL_SPLIT);
        bottomPane.setDividerLocation(170);
        
        bottomPane.setTopComponent(focusConceptPanel);
        bottomPane.setBottomComponent(descendantPanel);
        
        topPane.setBottomComponent(bottomPane);
        
        super.setMiddlePanelContents(topPane);
    }
    
    public AncestorPanel<T> getAncestorPanel() {
        return ancestorPanel;
    }
    
    public DescendantsPanel<T> getDescendantsPanel() {
        return descendantPanel;
    }
    
    public SiblingPanel<T> getSiblingPanel() {
        return siblingPanel;
    }
    
    public SearchAndHistoryPanel<T> getSearchAndHistoryPanel() {
        return searchAndHistoryPanel;
    }
    
    public FocusConceptPanel<T> getFocusConceptPanel() {
        return focusConceptPanel;
    }
}
