package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
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
public class NodeDetailsPanel extends BaseNodeInformationPanel {
    
    private final NodeSummaryPanel nodeSummaryPanel;

    private final AbstractNodeOptionsPanel nodeOptionsMenuPanel;

    private final ConceptList nodeConceptList;

    private final JSplitPane splitPane;
    
    private Optional<Node> currentNode = Optional.empty();

    public NodeDetailsPanel(NodeSummaryPanel nodeSummaryPanel, 
            AbstractNodeOptionsPanel nodeOptionsMenuPanel, 
            ConceptList nodeConceptList) {
        
        this.nodeSummaryPanel = nodeSummaryPanel;
        this.nodeOptionsMenuPanel = nodeOptionsMenuPanel;
        this.nodeConceptList = nodeConceptList;

        this.setLayout(new BorderLayout());

        this.splitPane = NodeDetailsPanel.createStyledSplitPane(JSplitPane.VERTICAL_SPLIT);
        
        JPanel upperPanel = new JPanel(new BorderLayout());
        upperPanel.add(nodeSummaryPanel, BorderLayout.CENTER);
        upperPanel.add(nodeOptionsMenuPanel, BorderLayout.SOUTH);
        
        splitPane.setTopComponent(upperPanel);
        splitPane.setBottomComponent(nodeConceptList);
        splitPane.setDividerLocation(400);

        this.add(splitPane, BorderLayout.CENTER);
    }
    
    protected AbstractEntityList<Concept> getConceptList() {
        return nodeConceptList;
    }
    
    public void setContents(Node node) {
        this.currentNode = Optional.of(node);

        nodeSummaryPanel.setContents(node);
        nodeOptionsMenuPanel.setContents(node);
        
        ArrayList<Concept> sortedConcepts = new ArrayList<>(node.getConcepts());
        
        sortedConcepts.sort( (a, b) -> {
            return a.getName().compareToIgnoreCase(b.getName());
        });
        
        nodeConceptList.setContents(sortedConcepts);
    }
    
    public void clearContents() {
        this.currentNode = Optional.empty();

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
    
    public Optional<Node> getCurrentNode() {
        return currentNode;
    }
}
