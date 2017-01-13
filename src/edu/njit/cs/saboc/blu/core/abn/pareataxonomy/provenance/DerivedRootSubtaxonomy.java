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
public class DerivedRootSubtaxonomy extends DerivedPAreaTaxonomy 
        implements DerivedRootedSubAbN<DerivedPAreaTaxonomy> {
    
    private final DerivedPAreaTaxonomy base;
    private final Concept pareaRootConcept;
    
    public DerivedRootSubtaxonomy(DerivedPAreaTaxonomy base, Concept pareaRootConcept) {
        super(base);
        
        this.base = base;
        this.pareaRootConcept = pareaRootConcept;
    }
    
    public DerivedRootSubtaxonomy(DerivedRootSubtaxonomy base) {
        this(base.getSuperAbNDerivation(), base.getSelectedRoot());
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
        return String.format("Derived root subtaxonomy (root: %s)", pareaRootConcept.getName());
    }

    @Override
    public PAreaTaxonomy getAbstractionNetwork() {
        PAreaTaxonomy taxonomy = super.getAbstractionNetwork();
        
        Set<PArea> pareas = taxonomy.getNodesWith(pareaRootConcept);
        
        return taxonomy.createRootSubtaxonomy(pareas.iterator().next());
    }
}
