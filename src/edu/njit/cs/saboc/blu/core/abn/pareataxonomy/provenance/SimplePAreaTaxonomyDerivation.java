package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomyFactory;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomyGenerator;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;

/**
 *
 * @author Chris O
 */
public class SimplePAreaTaxonomyDerivation extends PAreaTaxonomyDerivation {

    private final Concept root;

    public SimplePAreaTaxonomyDerivation(
            Ontology sourceOntology,
            Concept root,
            PAreaTaxonomyFactory factory) {

        super(sourceOntology, factory);

        this.root = root;
    }

    public Concept getRoot() {
        return root;
    }

    @Override
    public PAreaTaxonomy getAbstractionNetwork() {
        PAreaTaxonomyGenerator generator = new PAreaTaxonomyGenerator();

        PAreaTaxonomy taxonomy = generator.derivePAreaTaxonomy(
                super.getFactory(),
                super.getSourceOntology().getConceptHierarchy().getSubhierarchyRootedAt(root));

        return taxonomy;
    }

    @Override
    public String getDescription() {
        return String.format("Derived Partial-area Taxonomy (Root: %s)", root.getName());
    }
}
