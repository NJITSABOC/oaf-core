
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import edu.njit.cs.saboc.blu.core.abn.disjoint.aggregate.AggregateDisjointNode;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNConfiguration;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class AggregateDisjointNodeTextFactory<T extends SinglyRootedNode> extends DisjointNodeSummaryTextFactory<T> {

    public AggregateDisjointNodeTextFactory(DisjointAbNConfiguration config) {
        super(config);
    }

    @Override
    public String createNodeSummaryText(DisjointNode<T> parea) {
        DisjointAbNConfiguration config = this.getConfiguration();
        
        DisjointAbstractionNetwork disjointAbN = config.getAbstractionNetwork();
        
        AggregateDisjointNode<T> aggregatePArea = (AggregateDisjointNode<T>)parea;
        
        String rootName = aggregatePArea.getName();

        String typeDesc;
        
        if(aggregatePArea.getAggregatedNodes().isEmpty()) {
            DisjointNode<T> rootNode = aggregatePArea.getAggregatedHierarchy().getRoot();
            
            int conceptCount = rootNode.getConceptCount();
            
            typeDesc = String.format("<b>%s</b> is a regular %s that summarizes %d %s.",
                    rootName, 
                    config.getTextConfiguration().getNodeTypeName(false).toLowerCase(),
                    rootNode.getConceptCount(), 
                    config.getTextConfiguration().getConceptTypeName(conceptCount > 1 || conceptCount == 0)).toLowerCase();
            
        } else {
            int aggregatedGroupCount = aggregatePArea.getAggregatedNodes().size();
            int totalConceptCount = aggregatePArea.getConcepts().size();
            
            typeDesc = String.format("<b>%s</b> is an aggregate %s that summarizes %d %s and %d %s.",
                    rootName, 
                    config.getTextConfiguration().getNodeTypeName(false).toLowerCase(),
                    totalConceptCount, 
                    config.getTextConfiguration().getConceptTypeName(totalConceptCount > 1 || totalConceptCount == 0).toLowerCase(),
                    aggregatedGroupCount,
                    config.getTextConfiguration().getNodeTypeName(aggregatedGroupCount > 1 || aggregatedGroupCount == 0).toLowerCase());
        }

        int parentCount = disjointAbN.getNodeHierarchy().getParents(aggregatePArea).size();
        int childCount = disjointAbN.getNodeHierarchy().getChildren(aggregatePArea).size();

        Set<PArea> descendantPAreas = disjointAbN.getNodeHierarchy().getDescendants(aggregatePArea);

        return String.format("<html>%s It has %d parent partial-area(s) and %d child partial-area(s). "
                + "There are a total of %d descendant partial-area(s)."
                + "<p>"
                + "<b>Help / Description:</b><br>%s",
                typeDesc,  
                parentCount, 
                childCount, 
                descendantPAreas.size(), 
                config.getTextConfiguration().getNodeHelpDescription(aggregatePArea));
    }
    
}
