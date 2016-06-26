package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.factory.NodeSummaryTextFactory;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class DisjointNodeSummaryTextFactory implements NodeSummaryTextFactory {
    
    private final DisjointAbNConfiguration config;
    
    public DisjointNodeSummaryTextFactory(DisjointAbNConfiguration config) {
        this.config = config;
    }

    @Override
    public String createNodeSummaryText(Node node) {
        DisjointNode disjointNode = (DisjointNode)node;
               
        String rootName = disjointNode.getName();
        int classCount = disjointNode.getConceptCount();
        
        DisjointAbstractionNetwork disjointAbN = config.getAbstractionNetwork();

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
                    config.getTextConfiguration().getNodeTypeName(false).toLowerCase(), 
                    classCount, 
                    config.getTextConfiguration().getConceptTypeName(classCount > 1 || classCount == 0).toLowerCase(),
                    descendantGroupCount, 
                    config.getTextConfiguration().getNodeTypeName(descendantGroupCount > 1 || descendantGroupCount == 0).toLowerCase(),
                    descendantConceptCount,
                    config.getTextConfiguration().getConceptTypeName(descendantConceptCount > 1 || descendantConceptCount == 0).toLowerCase());
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
                    config.getTextConfiguration().getNodeTypeName(false).toLowerCase(), 
                    classCount, 
                    config.getTextConfiguration().getConceptTypeName(classCount > 1 || classCount == 0).toLowerCase(),
                    config.getTextConfiguration().getOverlappingNodeTypeName(true).toLowerCase(),
                    overlapsStr,
                    descendantGroupCount, 
                    config.getTextConfiguration().getNodeTypeName(descendantGroupCount > 1 || descendantGroupCount == 0).toLowerCase(),
                    descendantConceptCount,
                    config.getTextConfiguration().getConceptTypeName(descendantConceptCount > 1 || descendantConceptCount == 0).toLowerCase());
        }
        
        result += "<p><b>Help / Description:</b><br>";
        
        result += config.getTextConfiguration().getNodeHelpDescription(disjointNode);
        
        return result;
    }
    
}
