package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.explain;

import edu.njit.cs.saboc.blu.core.abn.diff.explain.HierarchicalChanges;
import edu.njit.cs.saboc.blu.core.abn.diff.explain.OntologyChanges;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class InheritablePropertyChanges extends OntologyChanges {
    
    private final Map<Concept, Set<InheritablePropertyChange>> propertyChangeEffects = new HashMap<>();
    
    private final HierarchicalChanges hierarchyChanges;
    
    public InheritablePropertyChanges(
            HierarchicalChanges hierarchyChanges,
            PropertyChangeDetailsFactory changeDetailsFactory) {
        
        super(hierarchyChanges.getFromOntology(), 
                hierarchyChanges.getToOntology());
        
        this.hierarchyChanges = hierarchyChanges;

        /*
        hierarchyChanges.getTransferredHierarchyConcepts().forEach((concept) -> {
            Set<Concept> fromParents = hierarchyChanges.getFromSubhierarchy().getParents(concept);
            Set<Concept> toParents = hierarchyChanges.getToSubhierarchy().getParents(concept);
            
            Set<InheritableProperty> fromProperties = changeDetailsFactory.getFromProperties(concept);
            Set<InheritableProperty> toProperties = changeDetailsFactory.getToProperties(concept);
            
            
        });
        */
        
        
        
    }
}
