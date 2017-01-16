package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.provenance.DerivedSubAbN;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class DerivedRelationshipSubtaxonomy extends DerivedPAreaTaxonomy 
        implements DerivedSubAbN<DerivedPAreaTaxonomy> {
    
    private final Set<InheritableProperty> selectedProperties;
    private final DerivedPAreaTaxonomy base;
    
    public DerivedRelationshipSubtaxonomy(
            DerivedPAreaTaxonomy base, 
            Set<InheritableProperty> selectedProperties) {
        
        super(base);
        
        this.base = base;
        this.selectedProperties = selectedProperties;
    }
    
    public DerivedRelationshipSubtaxonomy(DerivedRelationshipSubtaxonomy deriveTaxonomy) {
        this(deriveTaxonomy.getSuperAbNDerivation(), deriveTaxonomy.getSelectedProperties());
    }

    @Override
    public DerivedPAreaTaxonomy getSuperAbNDerivation() {
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
}
