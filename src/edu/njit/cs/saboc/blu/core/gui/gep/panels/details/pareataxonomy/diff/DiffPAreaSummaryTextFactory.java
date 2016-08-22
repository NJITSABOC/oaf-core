package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.PAreaSummaryTextFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration.DiffPAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class DiffPAreaSummaryTextFactory extends PAreaSummaryTextFactory {
    
    public DiffPAreaSummaryTextFactory(DiffPAreaTaxonomyConfiguration config) {
        super(config);
    }
    
    public DiffPAreaTaxonomyConfiguration getConfiguration() {
        return (DiffPAreaTaxonomyConfiguration)super.getConfiguration();
    }

    @Override
    public String createNodeSummaryText(PArea parea) {
        DiffPAreaTaxonomyConfiguration config = this.getConfiguration();
        
        DiffPAreaTaxonomy taxonomy = config.getPAreaTaxonomy();
        
        DiffPArea diffPArea = (DiffPArea)parea;
        
        String rootName = parea.getName();
        int conceptCount = parea.getConceptCount();

        int parentCount = taxonomy.getPAreaHierarchy().getParents(diffPArea).size();
        int childCount = taxonomy.getPAreaHierarchy().getChildren(diffPArea).size();

        Set<DiffPArea> descendentPAreas = taxonomy.getPAreaHierarchy().getDescendants(diffPArea);

        Set<Concept> descendantConcepts = new HashSet<>();

        descendentPAreas.forEach((descendentPArea) -> {
            descendantConcepts.addAll(descendentPArea.getConcepts());
        });
        
        StringBuilder result = new StringBuilder();
        
        result.append(String.format("<html><b>%s</b> is a partial-area that summarizes %d %s. ", 
                rootName, 
                conceptCount,  
                config.getTextConfiguration().getConceptTypeName(conceptCount > 1 || conceptCount == 0).toLowerCase()));
        
        
        result.append("<p>");
        result.append("<b>Help / Description</b><p>");
        result.append(config.getTextConfiguration().getNodeHelpDescription(parea));

        return result.toString();
    }
}
