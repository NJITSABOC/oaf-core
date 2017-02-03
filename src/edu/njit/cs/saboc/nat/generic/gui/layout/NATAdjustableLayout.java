package edu.njit.cs.saboc.nat.generic.gui.layout;

import edu.njit.cs.saboc.nat.generic.GenericNATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.ChildrenPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.NATLayoutPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.ParentPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.basic.BasicSplitPaneDivider;

/**
 *
 * @author Chris
 */
public abstract class NATAdjustableLayout extends NATLayout {
    
    public enum PanelSide {
        NORTH, SOUTH, EAST, WEST
    };
    
    private int myWidth;
    private int myHeight;
    
    protected Map<NATLayoutPanel, NATPanelNeighborhood> panelNeighborhoods;

    public NATAdjustableLayout(ConceptBrowserDataSource dataSource) {
        super(dataSource);
        
        panelNeighborhoods = new HashMap<>();
    }

    
    public int getLayoutWidth() {
        return myWidth;
    }
    
    public int getLayoutHeight() {
        return myHeight;
    }
    
    public void handleResize(int newWidth, int newHeight) {
        this.myWidth = newWidth;
        this.myHeight = newHeight;
        
        this.resetSplitPanePosition();
    }
    
    
    private ParentPanel parentPanel;
    private ChildrenPanel childrenPanel;
    
    
    @Override
    public void createLayout(GenericNATBrowserPanel mainPanel) {
        
        this.parentPanel = new ParentPanel(mainPanel, getDataSource(), true);
        this.childrenPanel = new ChildrenPanel(mainPanel, getDataSource(), true);
        
        NATPanelNeighborhood synonymNeighborhood = new NATPanelNeighborhood();

        //Combine Logo panel to Synonym
        JSplitPane topLeftPane = createStyledSplitPane(JSplitPane.VERTICAL_SPLIT);
        topLeftPane.setDividerLocation( (33 * myHeight) / 100);
        
        topLeftPane.setTopComponent(new JPanel());
        topLeftPane.setBottomComponent(new JPanel());

        synonymNeighborhood.registerSplitPane(topLeftPane, PanelSide.NORTH);

        JSplitPane bottomLeftPane = createStyledSplitPane(JSplitPane.VERTICAL_SPLIT);
        bottomLeftPane.setDividerLocation( (66 * myHeight) / 100);
               
        bottomLeftPane.setTopComponent(topLeftPane);
        bottomLeftPane.setBottomComponent(new JPanel());

        synonymNeighborhood.registerSplitPane(bottomLeftPane, PanelSide.SOUTH);

        //Combine Focus panel to parent
        JSplitPane topCenterPane = createStyledSplitPane(JSplitPane.VERTICAL_SPLIT);
        topCenterPane.setDividerLocation( (30 * myHeight) / 100);
        
        topCenterPane.setTopComponent(parentPanel);
        topCenterPane.setBottomComponent(new JPanel());
        
        NATPanelNeighborhood parentNeighborhood = new NATPanelNeighborhood();
        parentNeighborhood.registerSplitPane(topCenterPane, PanelSide.SOUTH);

        //Create a split pane with the above and children.
        JSplitPane bottomCenterPane = createStyledSplitPane(JSplitPane.VERTICAL_SPLIT);
        bottomCenterPane.setDividerLocation((55 * myHeight) / 100);
        
        bottomCenterPane.setTopComponent(topCenterPane);
        bottomCenterPane.setBottomComponent(childrenPanel);

        NATPanelNeighborhood childNeighborhood = new NATPanelNeighborhood();
        childNeighborhood.registerSplitPane(bottomCenterPane, PanelSide.NORTH);

        //This finishes the center part of the Relationship NAT.

        JSplitPane leftSplitPane = createStyledSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        leftSplitPane.setDividerLocation( (33 * myWidth) / 100);
        
        leftSplitPane.setLeftComponent(bottomLeftPane);
        leftSplitPane.setRightComponent(bottomCenterPane);

        synonymNeighborhood.registerSplitPane(leftSplitPane, PanelSide.EAST);
        parentNeighborhood.registerSplitPane(leftSplitPane, PanelSide.WEST);
        childNeighborhood.registerSplitPane(leftSplitPane, PanelSide.WEST);

        NATPanelNeighborhood relationshipNeighborhood = new NATPanelNeighborhood();

        //Combine Logo panel to Synonym
        JSplitPane topRightPane = createStyledSplitPane(JSplitPane.VERTICAL_SPLIT);
        topRightPane.setDividerLocation((33 * myHeight) / 100);
        
        topRightPane.setTopComponent(new JPanel());
        topRightPane.setBottomComponent(new JPanel());

        relationshipNeighborhood.registerSplitPane(topRightPane, PanelSide.NORTH);

        JSplitPane centerRightPane = createStyledSplitPane(JSplitPane.VERTICAL_SPLIT);
        centerRightPane.setDividerLocation( (55 * myHeight) / 100);
        centerRightPane.setTopComponent(topRightPane);
        centerRightPane.setBottomComponent(new JPanel());

        relationshipNeighborhood.registerSplitPane(centerRightPane, PanelSide.SOUTH);

        JSplitPane rightSplitPane = createStyledSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        rightSplitPane.setDividerLocation( (66 * myWidth) / 100);
        
        rightSplitPane.setLeftComponent(leftSplitPane);
        rightSplitPane.setRightComponent(centerRightPane);

        parentNeighborhood.registerSplitPane(rightSplitPane, PanelSide.EAST);
        childNeighborhood.registerSplitPane(rightSplitPane, PanelSide.EAST);
        relationshipNeighborhood.registerSplitPane(rightSplitPane, PanelSide.WEST);
        
                
        this.setLayout(new BorderLayout());

        this.add(rightSplitPane, BorderLayout.CENTER);
        
//
//        panelNeighborhoods.put(synonymPanel, synonymNeighborhood);
//        panelNeighborhoods.put(parentAncestorPanel, parentNeighborhood);
//        panelNeighborhoods.put(childDescendantPanel, childNeighborhood);
//        panelNeighborhoods.put(propertyPanel, relationshipNeighborhood);
        
        mainPanel.add(this);
    }
    
    public void resetSplitPanePosition() {
        int myWidth = getLayoutWidth();
        int myHeight = getLayoutHeight();

//        NATPanelNeighborhood synonymNeighborhood = panelNeighborhoods.get(synonymPanel);
//        NATPanelNeighborhood relationshipNeighborhood = panelNeighborhoods.get(propertyPanel);
//        NATPanelNeighborhood parentNeighborhood = panelNeighborhoods.get(parentAncestorPanel);
//        NATPanelNeighborhood childNeighborhood = panelNeighborhoods.get(childDescendantPanel);
//
//        synonymNeighborhood.getSplitPaneOn(PanelSide.NORTH).setDividerLocation((myHeight * 33) / 100);
//        synonymNeighborhood.getSplitPaneOn(PanelSide.NORTH).validate();
//
//        synonymNeighborhood.getSplitPaneOn(PanelSide.SOUTH).setDividerLocation((myHeight * 66) / 100);
//        synonymNeighborhood.getSplitPaneOn(PanelSide.SOUTH).validate();
//
//        synonymNeighborhood.getSplitPaneOn(PanelSide.EAST).setDividerLocation((myWidth * 33) / 100);
//        synonymNeighborhood.getSplitPaneOn(PanelSide.EAST).validate();
//
//        relationshipNeighborhood.getSplitPaneOn(PanelSide.WEST).setDividerLocation((myWidth * 66) / 100);
//        relationshipNeighborhood.getSplitPaneOn(PanelSide.WEST).validate();
//
//        relationshipNeighborhood.getSplitPaneOn(PanelSide.NORTH).setDividerLocation((myHeight * 33) / 100);
//        relationshipNeighborhood.getSplitPaneOn(PanelSide.NORTH).validate();
//
//        relationshipNeighborhood.getSplitPaneOn(PanelSide.SOUTH).setDividerLocation((myHeight * 55) / 100);
//        relationshipNeighborhood.getSplitPaneOn(PanelSide.SOUTH).validate();
//
//        parentNeighborhood.getSplitPaneOn(PanelSide.SOUTH).setDividerLocation((myHeight * 25) / 100);
//        parentNeighborhood.getSplitPaneOn(PanelSide.SOUTH).validate();
//
//        childNeighborhood.getSplitPaneOn(PanelSide.NORTH).setDividerLocation((myHeight * 50) / 100);
//        childNeighborhood.getSplitPaneOn(PanelSide.NORTH).validate();
    }
    
    
    
    private JSplitPane createStyledSplitPane(int alignment) {
        JSplitPane splitPane = new JSplitPane(alignment);
        splitPane.setBorder(null);

        Optional<BasicSplitPaneDivider> divider = Optional.empty();

        for (Component c : splitPane.getComponents()) {
            if (c instanceof BasicSplitPaneDivider) {
                divider = Optional.of((BasicSplitPaneDivider) c);
                break;
            }
        }

        if (divider.isPresent()) {
            
            divider.get().setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
            divider.get().setDividerSize(4);

        }
        
        return splitPane;
    }
}
