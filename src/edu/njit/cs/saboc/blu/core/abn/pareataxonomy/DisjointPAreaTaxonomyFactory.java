package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbNFactory;
import edu.njit.cs.saboc.blu.core.ontology.ConceptHierarchy;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class DisjointPAreaTaxonomyFactory implements DisjointAbNFactory<PArea, DisjointPArea> {

    @Override
    public DisjointPArea createDisjointNode(ConceptHierarchy hierarchy, Set<PArea> overlaps) {
        return new DisjointPArea(hierarchy, overlaps);
    }
}
