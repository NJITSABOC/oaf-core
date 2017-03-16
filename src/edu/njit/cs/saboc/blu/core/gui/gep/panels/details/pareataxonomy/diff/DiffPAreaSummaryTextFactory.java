package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.NodeConceptChange;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.NodeConceptSetChangeType;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.parea.PAreaSummaryTextFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration.DiffPAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class DiffPAreaSummaryTextFactory extends PAreaSummaryTextFactory {
    
    public DiffPAreaSummaryTextFactory(DiffPAreaTaxonomyConfiguration config) {
        super(config);
    }
    
    @Override
    public DiffPAreaTaxonomyConfiguration getConfiguration() {
        return (DiffPAreaTaxonomyConfiguration)super.getConfiguration();
    }

    @Override
    public String createNodeSummaryText(PArea parea) {
        DiffPAreaTaxonomyConfiguration config = this.getConfiguration();
        
        DiffPAreaTaxonomy taxonomy = config.getPAreaTaxonomy();
        
        DiffPArea diffPArea = (DiffPArea)parea;
        
        String rootName = parea.getName();

        Set<DiffPArea> descendentPAreas = taxonomy.getPAreaHierarchy().getDescendants(diffPArea);

        Set<Concept> descendantConcepts = new HashSet<>();

        descendentPAreas.forEach((descendentPArea) -> {
            descendantConcepts.addAll(descendentPArea.getConcepts());
        });
        
        StringBuilder result = new StringBuilder();
        
        result.append(String.format("<html><b>%s</b> is a diff partial-area.<p>", rootName));
        
        Set<NodeConceptChange> conceptChanges = diffPArea.getDiffNode().getChangeDetails().getConceptChanges();
        
        Map<NodeConceptSetChangeType, Set<Concept>> conceptsByChangeType = new HashMap<>();
        
        conceptChanges.forEach( (change) -> {
            if(!conceptsByChangeType.containsKey(change.getChangeType())) {
                conceptsByChangeType.put(change.getChangeType(), new HashSet<>());
            }
            
            conceptsByChangeType.get(change.getChangeType()).add(change.getConcept());
        });
        
        
        conceptsByChangeType.forEach( (type, concepts) -> {
            result.append(String.format("%s: %d<br>", type, concepts.size()));
        });
        
        result.append(String.format("TOTAL: %d", diffPArea.getDiffNode().getChangeDetails().getConceptChanges().size()));

        return result.toString();
    }
}
