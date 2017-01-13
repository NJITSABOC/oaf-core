
package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.provenance.DerivedRootedSubAbN;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class DerivedAncestorSubtaxonomy extends DerivedPAreaTaxonomy 
    implements DerivedRootedSubAbN<DerivedPAreaTaxonomy> {
    
    private final DerivedPAreaTaxonomy base;
    private final Concept pareaRootConcept;
    
    public DerivedAncestorSubtaxonomy(DerivedPAreaTaxonomy base, Concept pareaRootConcept) {
        super(base);
        
        this.base = base;
        this.pareaRootConcept = pareaRootConcept;
    }
    
    public DerivedAncestorSubtaxonomy(DerivedAncestorSubtaxonomy deriveTaxonomy) {
        this(deriveTaxonomy.getSuperAbNDerivation(), deriveTaxonomy.getSelectedRoot());
    }

    @Override
    public Concept getSelectedRoot() {
        return pareaRootConcept;
    }

    @Override
    public DerivedPAreaTaxonomy getSuperAbNDerivation() {
        return base;
    }
    
    @Override
    public String getDescription() {
        return String.format("Derived ancestor subtaxonomy (PArea: %s)", pareaRootConcept.getName());
    }

    @Override
    public PAreaTaxonomy getAbstractionNetwork() {
        PAreaTaxonomy taxonomy = base.getAbstractionNetwork();
        
        Set<PArea> pareas = taxonomy.getNodesWith(pareaRootConcept);
        
        return taxonomy.createAncestorSubtaxonomy(pareas.iterator().next());
    }
}
