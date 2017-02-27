package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.band;

import edu.njit.cs.saboc.blu.core.abn.tan.aggregate.AggregateCluster;
import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.TANConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class AggregateBandSummaryTextFactory extends BandSummaryTextFactory {
    
    public AggregateBandSummaryTextFactory(TANConfiguration config) {
        super(config);
    }

    @Override
    public String createNodeSummaryText(Band band) {

        TANConfiguration config = this.getConfiguration();

        Set<Cluster> aggregateClusters = new HashSet<>();
        Set<Cluster> regularClusters = new HashSet<>();

        Set<Cluster> bandClusters = band.getClusters();

        Set<Concept> totalConcepts = band.getConcepts();
        
        bandClusters.forEach((cluster) -> {
            AggregateCluster aggregateCluster = (AggregateCluster) cluster;

            if (aggregateCluster.getAggregatedNodes().isEmpty()) {
                regularClusters.add(cluster);
            } else {
                aggregateClusters.add(cluster);
            }
        });

        String pareaStr;

        if (aggregateClusters.isEmpty()) {
            pareaStr = String.format("%d %s in %d regular %s.",
                    totalConcepts.size(),
                    config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true).toLowerCase(),
                    regularClusters.size(),
                    config.getTextConfiguration().getNodeTypeName(regularClusters.size() != 1).toLowerCase());
        } else {

            if (regularClusters.isEmpty()) {
                pareaStr = String.format("%d %s in %d aggregate %s.",
                        totalConcepts.size(),
                        config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true).toLowerCase(),
                        aggregateClusters.size(),
                        config.getTextConfiguration().getNodeTypeName(aggregateClusters.size() != 1).toLowerCase());
            } else {
                pareaStr = String.format("%d %s in %d regular %s and %d aggregate %s.",
                        totalConcepts.size(),
                        config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true).toLowerCase(),
                        regularClusters.size(),
                        config.getTextConfiguration().getNodeTypeName(regularClusters.size() != 1).toLowerCase(),
                        aggregateClusters.size(),
                        config.getTextConfiguration().getNodeTypeName(aggregateClusters.size() != 1).toLowerCase());
            }
        }

        String bandName = band.getName();

        return String.format("<html><b>%s</b> is a band that summarizes %s"
                + "<p><b>Help / Description:</b><br>%s",
                bandName,
                pareaStr,
                config.getTextConfiguration().getContainerHelpDescription(band));
    }
}