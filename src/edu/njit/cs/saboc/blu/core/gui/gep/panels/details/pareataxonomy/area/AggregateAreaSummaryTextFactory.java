package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.area;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.aggregate.AggregatePArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class AggregateAreaSummaryTextFactory extends AreaSummaryTextFactory {
    
    public AggregateAreaSummaryTextFactory(PAreaTaxonomyConfiguration config) {
        super(config);
    }

    @Override
    public String createNodeSummaryText(Area area) {

        PAreaTaxonomyConfiguration config = this.getConfiguration();

        Set<PArea> aggregatePAreas = new HashSet<>();
        Set<PArea> regularPAreas = new HashSet<>();

        Set<PArea> areaPAreas = area.getPAreas();

        Set<Concept> totalConcepts = area.getConcepts();
        
        areaPAreas.forEach((parea) -> {
            AggregatePArea aggregatePArea = (AggregatePArea) parea;

            if (aggregatePArea.getAggregatedNodes().isEmpty()) {
                regularPAreas.add(parea);
            } else {
                aggregatePAreas.add(parea);
            }
        });

        String pareaStr;

        if (aggregatePAreas.isEmpty()) {
            pareaStr = String.format("%d %s in %d regular %s.",
                    totalConcepts.size(),
                    config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true).toLowerCase(),
                    regularPAreas.size(),
                    config.getTextConfiguration().getNodeTypeName(regularPAreas.size() != 1).toLowerCase());
        } else {

            if (regularPAreas.isEmpty()) {
                pareaStr = String.format("%d %s in %d aggregate %s.",
                        totalConcepts.size(),
                        config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true).toLowerCase(),
                        aggregatePAreas.size(),
                        config.getTextConfiguration().getNodeTypeName(aggregatePAreas.size() != 1).toLowerCase());
            } else {
                pareaStr = String.format("%d %s in %d regular %s and %d aggregate %s.",
                        totalConcepts.size(),
                        config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true).toLowerCase(),
                        regularPAreas.size(),
                        config.getTextConfiguration().getNodeTypeName(regularPAreas.size() != 1).toLowerCase(),
                        aggregatePAreas.size(),
                        config.getTextConfiguration().getNodeTypeName(aggregatePAreas.size() != 1).toLowerCase());
            }
        }

        String areaName = area.getName();

        return String.format("<html><b>%s</b> is an area that summarizes %s",
                areaName,
                pareaStr
        );

    }
    
}
