package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomyFactory;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivation;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;

/**
 *
 * @author Chris O
 */
public abstract class PAreaTaxonomyDerivation extends AbNDerivation<PAreaTaxonomy> {
    
    private final PAreaTaxonomyFactory factory;
    
    public PAreaTaxonomyDerivation(
            Ontology sourceOntology, 
            PAreaTaxonomyFactory factory) {
        
        super(sourceOntology);

        this.factory = factory;
    }
    
    public PAreaTaxonomyDerivation(PAreaTaxonomyDerivation base) {
        this(base.getSourceOntology(), base.getFactory());
    }

    public PAreaTaxonomyFactory getFactory() {
        return factory;
    }
}
