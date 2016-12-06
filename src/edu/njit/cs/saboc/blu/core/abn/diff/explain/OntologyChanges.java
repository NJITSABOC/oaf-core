package edu.njit.cs.saboc.blu.core.abn.diff.explain;

import edu.njit.cs.saboc.blu.core.ontology.Ontology;

/**
 *
 * @author Chris O
 */
public abstract class OntologyChanges {
    
    private final Ontology fromOntology;
    private final Ontology toOntology;
    
    protected OntologyChanges(Ontology fromOntology, Ontology toOntology) {
        this.fromOntology = fromOntology;
        this.toOntology = toOntology;
    }
    
    public Ontology getFromOntology() {
        return fromOntology;
    }

    public Ontology getToOntology() {
        return toOntology;
    }
    
}
