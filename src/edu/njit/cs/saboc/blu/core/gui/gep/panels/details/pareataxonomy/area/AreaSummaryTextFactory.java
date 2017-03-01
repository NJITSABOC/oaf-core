package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.area;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.factory.NodeSummaryTextFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;

/**
 *
 * @author Chris O
 */
public class AreaSummaryTextFactory implements NodeSummaryTextFactory<Area> {
    private final PAreaTaxonomyConfiguration config;
    
    public AreaSummaryTextFactory(PAreaTaxonomyConfiguration config) {
        this.config = config;
    }
    
    public PAreaTaxonomyConfiguration getConfiguration() {
        return config;
    }

    @Override
    public String createNodeSummaryText(Area area) {
        String areaName = area.getName();
        int classCount = area.getConcepts().size();

        String conceptType = config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(classCount > 1 || classCount == 0).toLowerCase();

        StringBuilder builder = new StringBuilder();
        builder.append(String.format("<html><b>%s</b> is an area that summarizes %d %s.",
                areaName, classCount, conceptType));

        builder.append("<p>");
        builder.append("<b>Help / Description</b><p>");
        builder.append(config.getTextConfiguration().getContainerHelpDescription(area));

        return builder.toString();
    }
}
