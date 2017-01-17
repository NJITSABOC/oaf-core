package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.SubAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.RelationshipSubtaxonomyDerivation;
import java.util.Set;

/**
 *
 * @author cro3
 */
public class RelationshipSubtaxonomy<T extends PArea> extends PAreaTaxonomy<T> implements SubAbstractionNetwork<PAreaTaxonomy> {
    
    private final PAreaTaxonomy superTaxonomy;
    private final Set<InheritableProperty> allowedProperties;
    
    public RelationshipSubtaxonomy(
            PAreaTaxonomy superTaxonomy,
            Set<InheritableProperty> allowedProperties,
            PAreaTaxonomy subTaxonomy) {

        super(subTaxonomy, new RelationshipSubtaxonomyDerivation(superTaxonomy.getDerivation(), allowedProperties));
        
        this.superTaxonomy = superTaxonomy;
        
        this.allowedProperties = allowedProperties;
    }
    
    @Override
    public RelationshipSubtaxonomyDerivation getDerivation() {
        return (RelationshipSubtaxonomyDerivation)super.getDerivation();
    }
    
    @Override
    public PAreaTaxonomy getRelationshipSubtaxonomy(Set<InheritableProperty> allowedRelTypes) {
        return superTaxonomy.getRelationshipSubtaxonomy(allowedRelTypes);
    }
    
    public Set<InheritableProperty> getAllowedProperties() {
        return allowedProperties;
    }

    @Override
    public PAreaTaxonomy getSuperAbN() {
        return superTaxonomy;
    }
}
