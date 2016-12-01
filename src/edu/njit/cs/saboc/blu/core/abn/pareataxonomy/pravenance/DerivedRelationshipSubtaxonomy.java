package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.pravenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class DerivedRelationshipSubtaxonomy extends DerivedPAreaTaxonomy {
    
    private final Set<InheritableProperty> selectedProperties;
    
    public DerivedRelationshipSubtaxonomy(
            DerivedPAreaTaxonomy base, 
            Set<InheritableProperty> selectedProperties) {
        
        super(base);
        
        this.selectedProperties = selectedProperties;
    }

    @Override
    public PAreaTaxonomy getAbstractionNetwork() {
        return super.getAbstractionNetwork().getRelationshipSubtaxonomy(selectedProperties);
    }

    @Override
    public String getDescription() {
        return "Created relationship subtaxonomy";
    }
}
