package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomyFactory;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomyGenerator;
import edu.njit.cs.saboc.blu.core.abn.provenance.DerivedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;

/**
 *
 * @author Chris O
 */
public class DerivedPAreaTaxonomy extends DerivedAbstractionNetwork<PAreaTaxonomy> {
    
    private final Concept root;
    private final PAreaTaxonomyFactory factory;
    
    public DerivedPAreaTaxonomy(
            Ontology sourceOntology, 
            Concept root, 
            PAreaTaxonomyFactory factory) {
        
        super(sourceOntology);
        
        this.root = root;
        this.factory = factory;
    }
    
    public DerivedPAreaTaxonomy(DerivedPAreaTaxonomy base) {
        this(base.getSourceOntology(), base.root, base.factory);
    }

    public Concept getRoot() {
        return root;
    }
    
    public PAreaTaxonomyFactory getFactory() {
        return factory;
    }
    
    @Override
    public PAreaTaxonomy getAbstractionNetwork() {
        PAreaTaxonomyGenerator generator = new PAreaTaxonomyGenerator();
        
        PAreaTaxonomy taxonomy = generator.derivePAreaTaxonomy(
                factory, 
                super.getSourceOntology().getConceptHierarchy().getSubhierarchyRootedAt(root));
        
        return taxonomy;
    }

    @Override
    public String getDescription() {
        return String.format("Derived partial-area taxonomy (root: %s)" + root.getName());
    }
}
