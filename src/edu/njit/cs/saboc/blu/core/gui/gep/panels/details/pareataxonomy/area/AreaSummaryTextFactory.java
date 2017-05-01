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
        
        String areaName = "<html><b><areaName></b> is an area that summarizes "
                + "<font color = 'RED'><conceptCount></font> <conceptTypeName count=<conceptCount>> "
                + "in <font color = 'RED'><pareaCount></font> <nodeTypeName count=<pareaCount>>. "
                + "It contains <font color = 'RED'><overlappingConceptCount></font> overlapping "
                + "<conceptTypeName count=<overlappingConceptCount>> that are summarized by "
                + "multiple <nodeTypeName count=2>.";
        
        areaName = areaName.replaceAll("<areaName>", area.getName());
        areaName = areaName.replaceAll("<conceptCount>", Integer.toString(area.getConcepts().size()));
        areaName = areaName.replaceAll("<pareaCount>", Integer.toString(area.getPAreas().size()));
        areaName = areaName.replaceAll("<overlappingConceptCount>", Integer.toString(area.getOverlappingConcepts().size()));
       
        return areaName;
    }
}
