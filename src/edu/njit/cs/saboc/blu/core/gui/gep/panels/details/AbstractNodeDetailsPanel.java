package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

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
public abstract class AbstractNodeDetailsPanel<NODE_T, CONCEPT_T> extends AbNNodeInformationPanel<NODE_T> {
    
    private AbstractNodeSummaryPanel<NODE_T> nodeSummaryPanel;

    private AbstractNodeOptionsPanel<NODE_T> nodeOptionsMenuPanel;

    private AbstractEntityList<CONCEPT_T> nodeConceptList;

    private final JSplitPane splitPane;

    public AbstractNodeDetailsPanel() {
        this.setLayout(new BorderLayout());

        this.splitPane = AbstractNodeDetailsPanel.createStyledSplitPane(JSplitPane.VERTICAL_SPLIT);

        this.add(splitPane, BorderLayout.CENTER);
    }
    
    public void initUI() {
        
        this.nodeSummaryPanel = createGroupSummaryPanel();
        this.nodeOptionsMenuPanel = createOptionsPanel();
        this.nodeConceptList = createGroupConceptList();
        
        nodeSummaryPanel.initUI();
        nodeOptionsMenuPanel.initUI();
        nodeConceptList.initUI();
        
        JPanel upperPanel = new JPanel(new BorderLayout());
        upperPanel.add(nodeSummaryPanel, BorderLayout.NORTH);
        upperPanel.add(nodeOptionsMenuPanel, BorderLayout.SOUTH);
        
        splitPane.setTopComponent(upperPanel);
        splitPane.setBottomComponent(nodeConceptList);
    }
    
    public void setContents(NODE_T conceptGroup) {
        nodeSummaryPanel.setContents(conceptGroup);
        nodeOptionsMenuPanel.setContents(conceptGroup);
        nodeConceptList.setContents(getSortedConceptList(conceptGroup));
    }
    
    public void clearContents() {
        nodeSummaryPanel.clearContents();
        nodeOptionsMenuPanel.clearContents();
        nodeConceptList.clearContents();
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
        
    protected abstract AbstractNodeSummaryPanel<NODE_T> createGroupSummaryPanel();
    
    protected abstract AbstractNodeOptionsPanel<NODE_T> createOptionsPanel();
    
    protected abstract ArrayList<CONCEPT_T> getSortedConceptList(NODE_T conceptGroup);
    
    protected abstract AbstractEntityList<CONCEPT_T> createGroupConceptList();
}
