package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public interface PAreaTaxonomyFactory {
    public Set<InheritableProperty> getRelationships(Concept c);
}
