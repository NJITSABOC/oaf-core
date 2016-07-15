
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.AggregatePArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class AggregatePAreaSummaryTextFactory extends PAreaSummaryTextFactory {

    public AggregatePAreaSummaryTextFactory(PAreaTaxonomyConfiguration config) {
        super(config);
    }

    @Override
    public String createNodeSummaryText(PArea parea) {
        PAreaTaxonomyConfiguration config = this.getConfiguration();
        
        PAreaTaxonomy taxonomy = config.getPAreaTaxonomy();
        
        AggregatePArea aggregatePArea = (AggregatePArea)parea;
        
        String rootName = aggregatePArea.getName();

        String typeDesc;
        
        if(aggregatePArea.getAggregatedNodes().isEmpty()) {
            PArea rootPArea = aggregatePArea.getAggregatedHierarchy().getRoot();
            
            int conceptCount = rootPArea.getConceptCount();
            
            typeDesc = String.format("<b>%s</b> is a regular partial-area that summarizes %d %s.",
                    rootName, rootPArea.getConceptCount(), 
                    config.getTextConfiguration().getConceptTypeName(conceptCount > 1 || conceptCount == 0)).toLowerCase();
            
        } else {
            int aggregatedGroupCount = aggregatePArea.getAggregatedNodes().size();
            int totalConceptCount = aggregatePArea.getConcepts().size();
            
            typeDesc = String.format("<b>%s</b> is an aggregate partial-area that summarizes %d %s and %d %s.",
                    rootName, 
                    totalConceptCount, 
                    config.getTextConfiguration().getConceptTypeName(totalConceptCount > 1 || totalConceptCount == 0).toLowerCase(),
                    aggregatedGroupCount,
                    config.getTextConfiguration().getNodeTypeName(aggregatedGroupCount > 1 || aggregatedGroupCount == 0).toLowerCase());
        }

        int parentCount = taxonomy.getPAreaHierarchy().getParents(aggregatePArea).size();
        int childCount = taxonomy.getPAreaHierarchy().getChildren(aggregatePArea).size();

        Set<PArea> descendantPAreas = taxonomy.getPAreaHierarchy().getDescendants(aggregatePArea);

        return String.format("<html>%s It has %d parent partial-area(s) and %d child partial-area(s). "
                + "There are a total of %d descendant partial-area(s)."
                + "<p>"
                + "<b>Help / Description:</b><br>%s",
                typeDesc,  
                parentCount, 
                childCount, 
                descendantPAreas.size(), 
                config.getTextConfiguration().getNodeHelpDescription(aggregatePArea));
    }
    
}
