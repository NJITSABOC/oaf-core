package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.parea;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.factory.NodeSummaryTextFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class PAreaSummaryTextFactory implements NodeSummaryTextFactory<PArea> {
    
    private final PAreaTaxonomyConfiguration config;
    
    public PAreaSummaryTextFactory(PAreaTaxonomyConfiguration config) {
        this.config = config;
    }
    
    public PAreaTaxonomyConfiguration getConfiguration() {
        return config;
    }

    @Override
    public String createNodeSummaryText(PArea parea) {
        PAreaTaxonomy taxonomy = config.getPAreaTaxonomy();
        
        String rootName = parea.getName();
        int conceptCount = parea.getConceptCount();

        int parentCount = taxonomy.getPAreaHierarchy().getParents(parea).size();
        int childCount = taxonomy.getPAreaHierarchy().getChildren(parea).size();

        Set<PArea> descendentPAreas = taxonomy.getPAreaHierarchy().getDescendants(parea);

        Set<Concept> descendantConcepts = new HashSet<>();

        descendentPAreas.forEach((descendentPArea) -> {
            descendantConcepts.addAll(descendentPArea.getConcepts());
        });
        
        StringBuilder result = new StringBuilder();
        
        result.append(String.format("<html><b>%s</b> is a partial-area that summarizes %d %s. ", 
                rootName, 
                conceptCount,  
                config.getTextConfiguration().getConceptTypeName(conceptCount > 1 || conceptCount == 0).toLowerCase()));
        
        if(parentCount > 0) {
            result.append(String.format("It has %d parent %s and ", 
                    parentCount, 
                    config.getTextConfiguration().getNodeTypeName(parentCount > 1 || parentCount == 0).toLowerCase()));
        } else {
            result.append("It has no parent partial-areas and ");
        }
        
        if(childCount > 0) {
            result.append(String.format("%d child %s.", childCount,
                    config.getTextConfiguration().getNodeTypeName(childCount > 1 || childCount == 0).toLowerCase()));
            
            if(descendentPAreas.size() > childCount) {
                int descPAreaCount = descendentPAreas.size();
                int descClassCount = descendantConcepts.size();
                
                result.append(String.format("It has a total of %d descendant %s which summarize a total of %d %s.", 
                        descPAreaCount, 
                        config.getTextConfiguration().getNodeTypeName(descPAreaCount > 1).toLowerCase(),
                        descClassCount,
                        config.getTextConfiguration().getConceptTypeName(descClassCount > 1).toLowerCase())
                );
            }
            
        } else {
            result.append("no child partial-areas.");
        }
        
        result.append("<p>");
        result.append("<b>Help / Description</b><p>");
        result.append(config.getTextConfiguration().getNodeHelpDescription(parea));

        return result.toString();
    }
    
}
