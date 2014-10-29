/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.njit.cs.saboc.blu.core.gui.dialogs.panels;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JPanel;

/**
 *
 * @author Chris
 */
public abstract class GroupDetailsPanel extends JPanel {

    public static enum GroupType {
        PartialArea,
        Cluster,
        DisjointPArea
    }

    private final JPanel groupSummaryPanel = new JPanel(new BorderLayout());
    private final JPanel groupParentsPanel = new JPanel(new BorderLayout());
    private final JPanel groupConceptsPanel = new JPanel(new BorderLayout());
    private final JPanel groupChildrenPanel = new JPanel(new BorderLayout());
        
    protected AbstractionNetwork abstractionNetwork;
    
    private GroupType groupType;

    public GroupDetailsPanel(AbstractionNetwork hierarchyData, GroupType groupType) {
        super(new GridLayout(4, 0));
        
        this.abstractionNetwork = hierarchyData;
        
        this.groupType = groupType;
        
        this.add(groupSummaryPanel);
        this.add(groupParentsPanel);
        this.add(groupConceptsPanel);
        this.add(groupChildrenPanel);
        
        this.initialize();
    }
    
    public GroupType getGroupType() {
        return groupType;
    }

    protected JEditorPane makeTextDetailsPane() {
        JEditorPane pane = new JEditorPane();
        pane.setContentType("text/html");
        pane.setEditable(false);

        return pane;
    }
    
    protected abstract void initialize();

    protected void resetTextPaneScrollPosition(JEditorPane pane) {
        pane.setSelectionStart(0);
        pane.setSelectionEnd(0);
    }
    
    public void setGroupSummaryComponent(JComponent component) {
        setPanelComponent(groupSummaryPanel, component);
    }
    
    public void setParentComponent(JComponent component) {
        setPanelComponent(groupParentsPanel, component);
    }
    
    public void setConceptsComponent(JComponent component) {
        setPanelComponent(groupConceptsPanel, component);
    }
    
    public void setChildrenComponent(JComponent component) {
        setPanelComponent(groupChildrenPanel, component);
    }
    
    private void setPanelComponent(JPanel panel, JComponent component) {
        panel.removeAll();
        panel.add(component, BorderLayout.CENTER);
    }
}
