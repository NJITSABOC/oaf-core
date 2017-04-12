package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbNFactory;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivation;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;

/**
 * Stores the arguments needed to create a disjoint abstraction network.
 * 
 * @author Chris O
 */
public abstract class DisjointAbNDerivation extends AbNDerivation<DisjointAbstractionNetwork> {
    
    private final DisjointAbNFactory factory;
    
    public DisjointAbNDerivation(DisjointAbNFactory factory, Ontology<Concept> sourceOntology) {
        
        super(sourceOntology);
        
        this.factory = factory;
    }
    
    public DisjointAbNDerivation(DisjointAbNDerivation derivedAbN) {
        this(derivedAbN.getFactory(), derivedAbN.getSourceOntology());
    }
    
    public DisjointAbNFactory getFactory() {
        return factory;
    }
}
