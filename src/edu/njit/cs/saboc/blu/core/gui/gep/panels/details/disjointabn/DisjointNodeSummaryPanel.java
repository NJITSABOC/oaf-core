package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUDisjointConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeSummaryPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.factory.NodeSummaryTextFactory;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class DisjointNodeSummaryPanel extends NodeSummaryPanel {
    
    private final DisjointAbstractionNetwork disjointAbN;
    
    private final OverlappingNodeList overlapsPanel;
    
    private final BLUDisjointConfiguration configuration;
    
    public DisjointNodeSummaryPanel(
            DisjointAbstractionNetwork disjointAbN, 
            BLUDisjointConfiguration configuration,
            NodeSummaryTextFactory summaryTextFactory) {
        
        super(summaryTextFactory);
        
        this.disjointAbN = disjointAbN;
        this.configuration = configuration;
        
        this.overlapsPanel = new OverlappingNodeList(configuration);
        this.overlapsPanel.setMinimumSize(new Dimension(-1, 100));
        this.overlapsPanel.setPreferredSize(new Dimension(-1, 100));
        
        this.add(this.overlapsPanel);
    }
    
    public void setContents(DisjointNode disjointNode) {
        super.setContents(disjointNode);
        
        ArrayList<Node> overlaps = new ArrayList<>(disjointNode.getOverlaps());
        
        Collections.sort(overlaps, (a, b) -> a.getName().compareTo(b.getName()));
        
        overlapsPanel.setContents(overlaps);
    }
    
    public void clearContents() {
        super.clearContents();
        
        overlapsPanel.clearContents();
    }

    protected String createDescriptionStr(DisjointNode disjointNode) {
        
        String rootName = disjointNode.getName();
        int classCount = disjointNode.getConceptCount();

        Set<DisjointNode> descendantDisjointNodes = disjointAbN.getNodeHierarchy().getDescendants(disjointNode);
        
        int descendantGroupCount = descendantDisjointNodes.size();

        Set<Concept> descendantClasses = new HashSet<>();

        descendantDisjointNodes.forEach((descendentDisjointNode) -> {
            descendantClasses.addAll(descendentDisjointNode.getConcepts());
        });
        
        int descendantConceptCount = descendantClasses.size();
        
        Set<Node> overlaps = disjointNode.getOverlaps();
        
        String result;
        
        if(overlaps.size() == 1) {
            result = String.format("<b>%s</b> is a basis (non-overlapping) %s that summarizes %d non-overlapping %s. It has %d descendant overlapping %s which summarizes %d overlapping %s.",
                    rootName, 
                    configuration.getTextConfiguration().getNodeTypeName(false).toLowerCase(), 
                    classCount, 
                    configuration.getTextConfiguration().getConceptTypeName(classCount > 1 || classCount == 0).toLowerCase(),
                    descendantGroupCount, 
                    configuration.getTextConfiguration().getNodeTypeName(descendantGroupCount > 1 || descendantGroupCount == 0).toLowerCase(),
                    descendantConceptCount,
                    configuration.getTextConfiguration().getConceptTypeName(descendantConceptCount > 1 || descendantConceptCount == 0).toLowerCase());
        } else {
            
            ArrayList<String> overlappingGroupNames = new ArrayList<>();
            
            overlaps.forEach((overlappingGroup) -> {
                overlappingGroupNames.add(overlappingGroup.getName());
            });
            
            Collections.sort(overlappingGroupNames);
            
            String overlapsStr = overlappingGroupNames.get(0);
            
            for(int c = 1; c < overlappingGroupNames.size(); c++) {
                overlapsStr += ", " + overlappingGroupNames.get(c);
            }
            
            result = String.format("<b>%s</b> is an overlapping %s that summarizes %d overlapping %s. It overlaps between the following %s: <b>%s</b>."
                    + " It has %d descendant overlapping %s which summarizes %d overlapping %s.",
                    rootName, 
                    configuration.getTextConfiguration().getNodeTypeName(false).toLowerCase(), 
                    classCount, 
                    configuration.getTextConfiguration().getConceptTypeName(classCount > 1 || classCount == 0).toLowerCase(),
                    configuration.getTextConfiguration().getOverlappingNodeTypeName(true).toLowerCase(),
                    overlapsStr,
                    descendantGroupCount, 
                    configuration.getTextConfiguration().getNodeTypeName(descendantGroupCount > 1 || descendantGroupCount == 0).toLowerCase(),
                    descendantConceptCount,
                    configuration.getTextConfiguration().getConceptTypeName(descendantConceptCount > 1 || descendantConceptCount == 0).toLowerCase());
        }
        
        result += "<p><b>Help / Description:</b><br>";
        
        result += configuration.getTextConfiguration().getNodeHelpDescription(disjointNode);
        
        return result;
    }
}
