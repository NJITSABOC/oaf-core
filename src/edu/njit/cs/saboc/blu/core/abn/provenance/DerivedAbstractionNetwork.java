
package edu.njit.cs.saboc.blu.core.abn.provenance;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;

/**
 *
 * @author Chris O
 */
public abstract class DerivedAbstractionNetwork<ABN_T extends AbstractionNetwork> implements AbNDerivationHistoryEntry<ABN_T> {

    private final Ontology sourceOntology;
    
    public DerivedAbstractionNetwork(Ontology sourceOntology) {
        this.sourceOntology = sourceOntology;
    }
    
    public Ontology getSourceOntology() {
        return sourceOntology;
    }
}
