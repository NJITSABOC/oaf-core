package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.factory.NodeSummaryTextFactory;

/**
 *
 * @author Chris O
 */
public class AreaSummaryTextFactory implements NodeSummaryTextFactory {
    private final PAreaTaxonomyConfiguration config;
    
    public AreaSummaryTextFactory(PAreaTaxonomyConfiguration config) {
        this.config = config;
    }

    @Override
    public String createNodeSummaryText(Node node) {
        Area area = (Area) node;

        String areaName = area.getName();
        int classCount = area.getConcepts().size();

        String conceptType = config.getTextConfiguration().getConceptTypeName(classCount > 1 || classCount == 0).toLowerCase();

        StringBuilder builder = new StringBuilder();
        builder.append(String.format("<html><b>%s</b> is an area that summarizes %d %s.",
                areaName, classCount, conceptType));

        builder.append("<p>");
        builder.append("<b>Help / Description</b><p>");
        builder.append(config.getTextConfiguration().getContainerHelpDescription(area));

        return builder.toString();
    }
}
