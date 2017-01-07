package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author cro3
 */
public class PAreaRelationshipSubtaxonomyFactory extends PAreaTaxonomyFactory {
    
    private final PAreaTaxonomy baseTaxonomy;
    private final Set<InheritableProperty> allowedRels;
    
    public PAreaRelationshipSubtaxonomyFactory(
            PAreaTaxonomy baseTaxonomy, 
            Set<InheritableProperty> allowedTypes) {

        this.baseTaxonomy = baseTaxonomy;
        this.allowedRels = allowedTypes;
    }
    
    @Override
    public AreaTaxonomy createAreaTaxonomy(
            Hierarchy<Area> areaHierarchy, 
            Hierarchy<Concept> sourceHierarchy) {

        return baseTaxonomy.getPAreaTaxonomyFactory().createAreaTaxonomy(areaHierarchy, sourceHierarchy);
    }
    
    @Override
    public <T extends PArea> PAreaTaxonomy createPAreaTaxonomy(
            AreaTaxonomy areaTaxonomy,
            Hierarchy<T> pareaHierarchy, 
            Hierarchy<Concept> conceptHierarchy) {

        return new RelationshipSubtaxonomy(baseTaxonomy, allowedRels, areaTaxonomy, pareaHierarchy, conceptHierarchy);
    }
    
    @Override
    public Set<InheritableProperty> getRelationships(Concept c) {
        Set<InheritableProperty> allRels = new HashSet<>(baseTaxonomy.getPAreaTaxonomyFactory().getRelationships(c));
        
        allRels.retainAll(allowedRels);

        return allRels;
    }
}
