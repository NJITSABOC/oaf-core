package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.change.ChangeState;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPArea;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.AreaSummaryTextFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration.DiffPAreaTaxonomyConfiguration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class DiffAreaSummaryTextFactory extends AreaSummaryTextFactory {
    
    public DiffAreaSummaryTextFactory(DiffPAreaTaxonomyConfiguration config) {
        super(config);
    }
    
    public DiffPAreaTaxonomyConfiguration getConfiguration() {
        return (DiffPAreaTaxonomyConfiguration)super.getConfiguration();
    }
    
    @Override
    public String createNodeSummaryText(Area area) {
        DiffPAreaTaxonomyConfiguration config = this.getConfiguration();
        
        DiffArea diffArea = (DiffArea)area;
        
        String areaName = area.getName();
        int classCount = area.getConcepts().size();

        String conceptType = config.getTextConfiguration().getConceptTypeName(classCount > 1 || classCount == 0).toLowerCase();

        StringBuilder builder = new StringBuilder();
        builder.append(String.format("<html><b>%s</b> is a diff area.", areaName));
        
        
        Map<ChangeState, Set<DiffPArea>> diffPAreasByType = new HashMap<>();
        
        diffArea.getDiffPAreas().forEach((diffPArea) -> {
            if(!diffPAreasByType.containsKey(diffPArea.getPAreaState())) {
                diffPAreasByType.put(diffPArea.getPAreaState(), new HashSet<>());
            }
            
            diffPAreasByType.get(diffPArea.getPAreaState()).add(diffPArea);
        });
        
        
        builder.append("<p>Diff Partial-areas:<br>");
        
        diffPAreasByType.forEach( (type, diffPAreasOfType) -> {
            builder.append(String.format("%s: %d<br>", type, diffPAreasOfType.size()));
        });
        

        builder.append("<br>");
        builder.append("<b>Help / Description</b><p>");
        builder.append(config.getTextConfiguration().getContainerHelpDescription(area));

        return builder.toString();
    }
}
