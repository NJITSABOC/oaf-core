package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomyFactory;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomyGenerator;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivation;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;

/**
 *
 * @author Chris O
 */
public abstract class DerivedPAreaTaxonomy extends AbNDerivation<PAreaTaxonomy> {
    
    private final PAreaTaxonomyFactory factory;
    
    public DerivedPAreaTaxonomy(
            Ontology sourceOntology, 
            PAreaTaxonomyFactory factory) {
        
        super(sourceOntology);

        this.factory = factory;
    }
    
    public DerivedPAreaTaxonomy(DerivedPAreaTaxonomy base) {
        this(base.getSourceOntology(), base.getFactory());
    }

    public PAreaTaxonomyFactory getFactory() {
        return factory;
    }
}
