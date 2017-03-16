package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.cluster;

import edu.njit.cs.saboc.blu.core.abn.tan.aggregate.AggregateCluster;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.TANConfiguration;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class AggregateClusterSummaryTextFactory extends ClusterSummaryTextFactory {

    public AggregateClusterSummaryTextFactory(TANConfiguration config) {
        super(config);
    }

    @Override
    public String createNodeSummaryText(Cluster cluster) {
        TANConfiguration config = this.getConfiguration();
        
        ClusterTribalAbstractionNetwork tan = config.getTribalAbstractionNetwork();
        
        AggregateCluster aggregateCluster = (AggregateCluster)cluster;
        
        String rootName = aggregateCluster.getName();

        String typeDesc;
        
        if(aggregateCluster.getAggregatedNodes().isEmpty()) {
            Cluster rootCluster = aggregateCluster.getAggregatedHierarchy().getRoot();
            
            int conceptCount = rootCluster.getConceptCount();
            
            typeDesc = String.format("<b>%s</b> is a regular cluster that summarizes %d %s.",
                    rootName, rootCluster.getConceptCount(), 
                    config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(conceptCount > 1 || conceptCount == 0)).toLowerCase();
            
        } else {
            int aggregatedGroupCount = aggregateCluster.getAggregatedNodes().size();
            int totalConceptCount = aggregateCluster.getConcepts().size();
            
            typeDesc = String.format("<b>%s</b> is an aggregate cluster that summarizes %d %s and %d %s.",
                    rootName, 
                    totalConceptCount, 
                    config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(totalConceptCount > 1 || totalConceptCount == 0).toLowerCase(),
                    aggregatedGroupCount,
                    config.getTextConfiguration().getNodeTypeName(aggregatedGroupCount > 1 || aggregatedGroupCount == 0).toLowerCase());
        }

        int parentCount = tan.getClusterHierarchy().getParents(aggregateCluster).size();
        int childCount = tan.getClusterHierarchy().getChildren(aggregateCluster).size();

        Set<Cluster> descendantClusters = tan.getClusterHierarchy().getDescendants(aggregateCluster);

        return String.format("<html>%s It has %d parent clusters(s) and %d child clusters(s). "
                + "There are a total of %d descendant clusters(s).",
                typeDesc,  
                parentCount, 
                childCount, 
                descendantClusters.size()
        );
    }
    
}