package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.cluster;

import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.factory.NodeSummaryTextFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.TANConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class ClusterSummaryTextFactory implements NodeSummaryTextFactory<Cluster> {
    
    private final TANConfiguration config;
    
    public ClusterSummaryTextFactory(TANConfiguration config) {
        this.config = config;
    }

    @Override
    public String createNodeSummaryText(Cluster cluster) {
        ClusterTribalAbstractionNetwork tan = config.getAbstractionNetwork();
        
        String conceptType = config.getTextConfiguration().getConceptTypeName(true).toLowerCase();
        
        String rootName = cluster.getName();
        int classCount = cluster.getConceptCount();

        int parentCount = tan.getClusterHierarchy().getParents(cluster).size();
        int childCount = tan.getClusterHierarchy().getChildren(cluster).size();

        Set<Cluster> descendantClusters = tan.getClusterHierarchy().getDescendants(cluster);

        HashSet<Concept> descendantClasses = new HashSet<>();

        descendantClusters.forEach((c) -> {
            descendantClasses.addAll(c.getConcepts());
        });
        
        String result = String.format("<html><b>%s</b> is a cluster that summarizes %d %s. It has %d parent clusters and %d child clusters. "
                + "There are a total of %d descendant clusters that summarize %d %s",
                rootName, classCount, conceptType, parentCount, childCount, descendantClusters.size(), descendantClasses.size(), conceptType);
        
        result += "<p><b>Help / Description:</b><br>";
        result += config.getTextConfiguration().getNodeHelpDescription(cluster);

        return result;
    }
    
}
