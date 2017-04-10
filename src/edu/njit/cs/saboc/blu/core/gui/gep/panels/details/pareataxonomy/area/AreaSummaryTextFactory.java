package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.area;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.factory.NodeSummaryTextFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;

/**
 *
 * @author Chris O
 */
public class AreaSummaryTextFactory extends NodeSummaryTextFactory<Area> {

    public AreaSummaryTextFactory(PAreaTaxonomyConfiguration config) {
        super(config);
    }
    
    @Override
    public PAreaTaxonomyConfiguration getConfiguration() {
        return (PAreaTaxonomyConfiguration)super.getConfiguration();
    }

    @Override
    public String createNodeSummaryText(Area area) {
        String areaName = area.getName();
        int classCount = area.getConcepts().size();

        String conceptType = getConfiguration().getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(classCount > 1 || classCount == 0).toLowerCase();

        StringBuilder builder = new StringBuilder();
        builder.append(String.format("<html><b>%s</b> is an area that summarizes %d %s.",
                areaName, classCount, conceptType));

        return builder.toString();
    }
}
