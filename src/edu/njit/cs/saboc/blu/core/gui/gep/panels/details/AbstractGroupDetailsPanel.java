package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import SnomedShared.generic.GenericConceptGroup;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.basic.BasicSplitPaneDivider;

/**
 *
 * @author Chris O
 */
public abstract class AbstractGroupDetailsPanel<GROUP_T extends GenericConceptGroup, CONCEPT_T> extends GroupInformationPanel<GROUP_T> {
    
    private AbstractGroupSummaryPanel<GROUP_T> groupSummaryPanel;

    private AbstractGroupOptionsPanel<GROUP_T> optionsMenuPanel;

    private AbstractConceptList<CONCEPT_T> groupConceptList;

    private final JSplitPane splitPane;

    public AbstractGroupDetailsPanel() {
        this.setLayout(new BorderLayout());

        this.splitPane = AbstractGroupDetailsPanel.createStyledSplitPane(JSplitPane.VERTICAL_SPLIT);

        this.add(splitPane, BorderLayout.CENTER);
    }
    
    public void initUI() {
        
        this.groupSummaryPanel = createGroupSummaryPanel();
        this.optionsMenuPanel = createOptionsPanel();
        this.groupConceptList = createGroupConceptList();
        
        groupSummaryPanel.initUI();
        optionsMenuPanel.initUI();
        groupConceptList.initUI();
        
        JPanel upperPanel = new JPanel(new BorderLayout());
        upperPanel.add(groupSummaryPanel, BorderLayout.NORTH);
        upperPanel.add(optionsMenuPanel, BorderLayout.SOUTH);
        
        splitPane.setTopComponent(upperPanel);
        splitPane.setBottomComponent(groupConceptList);
    }
    
    public void setContents(GROUP_T conceptGroup) {
        
        optionsMenuPanel.enableOptionsForGroup(conceptGroup);
        groupConceptList.setContents(getSortedConceptList(conceptGroup));
    }
    
    public static JSplitPane createStyledSplitPane(int alignment) {
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
        
    protected abstract AbstractGroupSummaryPanel<GROUP_T> createGroupSummaryPanel();
    
    protected abstract AbstractGroupOptionsPanel<GROUP_T> createOptionsPanel();
    
    protected abstract ArrayList<CONCEPT_T> getSortedConceptList(GROUP_T conceptGroup);
    
    protected abstract AbstractConceptList<CONCEPT_T> createGroupConceptList();
}
