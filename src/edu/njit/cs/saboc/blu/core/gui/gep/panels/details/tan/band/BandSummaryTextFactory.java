package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.band;

import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.factory.NodeSummaryTextFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class BandSummaryTextFactory extends NodeSummaryTextFactory<Band> {
    
    public BandSummaryTextFactory(TANConfiguration config) {
        super(config);
    }
    
    @Override
    public TANConfiguration getConfiguration() {
        return (TANConfiguration)super.getConfiguration();
    }
    
    @Override
    public String createNodeSummaryText(Band band) {
        String bandName = band.getName();

        Set<Cluster> clusters = band.getClusters();

        Set<Concept> allConcepts = new HashSet<>();

        clusters.forEach((cluster) -> {
            allConcepts.addAll(cluster.getConcepts());
        });

        String result = String.format("<html><b>%s</b> is a tribal band that summarizes %d %s in %d clusters.",
                bandName,
                allConcepts.size(),
                getConfiguration().getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(allConcepts.size() != 1).toLowerCase(),
                clusters.size());

        return result;
    }

}
