package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.explain;

import edu.njit.cs.saboc.blu.core.abn.diff.explain.HierarchicalChanges;
import edu.njit.cs.saboc.blu.core.abn.diff.explain.OntologyChanges;
import edu.njit.cs.saboc.blu.core.abn.diff.utils.SetUtilities;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class InheritablePropertyChanges extends OntologyChanges {
    
    private final Map<Concept, Set<InheritablePropertyChange>> propertyChanges;
    
    private final HierarchicalChanges hierarchyChanges;
    
    private final Set<InheritableProperty> addedProperties;
    private final Set<InheritableProperty> removedProperties;
    private final Set<InheritableProperty> transferredProperties;
    
    public InheritablePropertyChanges(
            HierarchicalChanges hierarchyChanges,
            PropertyChangeDetailsFactory changeDetailsFactory) {
        
        super(hierarchyChanges.getFromOntology(), 
                hierarchyChanges.getToOntology());
        
        this.hierarchyChanges = hierarchyChanges;
        
        Set<InheritableProperty> fromOntProperties = changeDetailsFactory.getFromOntProperties();
        Set<InheritableProperty> toOntProperties = changeDetailsFactory.getToOntProperties();
        
        this.addedProperties = SetUtilities.getSetDifference(toOntProperties, fromOntProperties);
        this.removedProperties = SetUtilities.getSetDifference(fromOntProperties, toOntProperties);
        this.transferredProperties = SetUtilities.getSetIntersection(fromOntProperties, toOntProperties);
        
        this.propertyChanges = changeDetailsFactory.getPropertyChanges();

        /*
        hierarchyChanges.getTransferredHierarchyConcepts().forEach((concept) -> {
            Set<Concept> fromParents = hierarchyChanges.getFromSubhierarchy().getParents(concept);
            Set<Concept> toParents = hierarchyChanges.getToSubhierarchy().getParents(concept);
            
            Set<InheritableProperty> fromProperties = changeDetailsFactory.getFromPropertiesFor(concept);
            Set<InheritableProperty> toProperties = changeDetailsFactory.getToPropertiesFor(concept);
            
            
            if(!fromParents.equals(toParents) && !fromProperties.equals(toProperties)) {
                Set<InheritableProperty> addedProps = SetUtilities.getSetDifference(toProperties, fromProperties);
                Set<InheritableProperty> removedProps = SetUtilities.getSetDifference(fromProperties, toProperties);
                
                
            }
            
        });
        */
        
        
        
    }
}
