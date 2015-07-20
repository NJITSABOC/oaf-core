
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import SnomedShared.generic.GenericConceptGroup;
import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Chris O
 */
public abstract class AbstractGroupPanel<GROUP_T extends GenericConceptGroup, CONCEPT_T> extends GroupInformationPanel<GROUP_T> {
    
    private final JLabel groupNameLabel;
    
    private final ArrayList<GroupInformationPanel<GROUP_T>> groupDetailsPanels = new ArrayList<>();
    
    private final JTabbedPane tabbedPane;
    
    private AbstractGroupDetailsPanel<GROUP_T, CONCEPT_T> groupDetailsPanel;
        
    protected AbstractGroupPanel() {
        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        
        groupNameLabel = new JLabel("  ");
        groupNameLabel.setFont(groupNameLabel.getFont().deriveFont(Font.BOLD, 18));
        
        tabbedPane = new JTabbedPane();

        this.add(groupNameLabel, BorderLayout.NORTH);
        this.add(tabbedPane, BorderLayout.CENTER);
    }
    
    public void setContents(GROUP_T group) {
        groupNameLabel.setText("<html>" + group.getRoot().getName());
        
        groupDetailsPanels.forEach((GroupInformationPanel<GROUP_T> gdp) -> {
            gdp.setContents(group);
        });
    }
    
    public void initUI() {
        this.groupDetailsPanel = createGroupDetailsPanel();

        addGroupDetailsTab(groupDetailsPanel, "Details");

        groupDetailsPanels.forEach((GroupInformationPanel<GROUP_T> gdp) -> {
            gdp.initUI();
        });
    }
    
    public void addGroupDetailsTab(GroupInformationPanel<GROUP_T> panel, String tabName) {
        tabbedPane.addTab(tabName, panel);
        groupDetailsPanels.add(panel);
    }
    
    protected abstract AbstractGroupDetailsPanel<GROUP_T, CONCEPT_T> createGroupDetailsPanel();
}
