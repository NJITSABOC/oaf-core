package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.provenance.SubAbNDerivation;
import java.util.Set;

/**
 * Stores the arguments for creating a relationship subtaxonomy
 * 
 * @author Chris O
 */
public class RelationshipSubtaxonomyDerivation extends PAreaTaxonomyDerivation 
        implements SubAbNDerivation<PAreaTaxonomyDerivation> {
    
    private final Set<InheritableProperty> selectedProperties;
    private final PAreaTaxonomyDerivation base;
    
    public RelationshipSubtaxonomyDerivation(
            PAreaTaxonomyDerivation base, 
            Set<InheritableProperty> selectedProperties) {
        
        super(base);
        
        this.base = base;
        this.selectedProperties = selectedProperties;
    }
    
    public RelationshipSubtaxonomyDerivation(RelationshipSubtaxonomyDerivation deriveTaxonomy) {
        this(deriveTaxonomy.getSuperAbNDerivation(), deriveTaxonomy.getSelectedProperties());
    }

    @Override
    public PAreaTaxonomyDerivation getSuperAbNDerivation() {
         return base;
    }
    
    public Set<InheritableProperty> getSelectedProperties() {
        return selectedProperties;
    }

    @Override
    public PAreaTaxonomy getAbstractionNetwork() {
        return base.getAbstractionNetwork().getRelationshipSubtaxonomy(selectedProperties);
    }

    @Override
    public String getDescription() {
        return "Created relationship subtaxonomy";
    }

    @Override
    public String getName() {
        if(base instanceof SimplePAreaTaxonomyDerivation) {
            String rootName = ((SimplePAreaTaxonomyDerivation)base).getRoot().getName();
            
            return String.format("%s %s", rootName, getAbstractionNetworkTypeName());
        }
        
        return "[Unknown Relationship Subtaxonomy Type]";
    }

    @Override
    public String getAbstractionNetworkTypeName() {
        return "Relationship Subtaxonomy";
    }
    
    
}
