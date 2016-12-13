package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * @author cro3
 */
public class RelationshipSubtaxonomy<T extends PArea> extends PAreaSubtaxonomy<T> {
    
    private final Set<InheritableProperty> allowedProperties;
    
    public RelationshipSubtaxonomy(
            PAreaTaxonomy sourceTaxonomy,
            Set<InheritableProperty> allowedProperties,
            AreaTaxonomy areaTaxonomy,
            Hierarchy<T> pareaHierarchy, 
            Hierarchy<Concept> conceptHierarchy) {

        super(sourceTaxonomy, areaTaxonomy, pareaHierarchy, conceptHierarchy);
        
        this.allowedProperties = allowedProperties;
    }
    
    public PAreaTaxonomy getRelationshipSubtaxonomy(Set<InheritableProperty> allowedRelTypes) {
        return getSourceTaxonomy().getRelationshipSubtaxonomy(allowedRelTypes);
    }
    
    public Set<InheritableProperty> getAllowedProperties() {
        return allowedProperties;
    }
}
