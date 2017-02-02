
package edu.njit.cs.saboc.blu.core.abn.provenance;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import org.json.simple.JSONArray;

/**
 *
 * @author Chris O
 */
public abstract class AbNDerivation<T extends AbstractionNetwork> {

    private final Ontology sourceOntology;
    
    public AbNDerivation(Ontology sourceOntology) {
        this.sourceOntology = sourceOntology;
    }
    
    public Ontology getSourceOntology() {
        return sourceOntology;
    }
    
    public abstract String getDescription();
    public abstract T getAbstractionNetwork();
    public abstract JSONArray serializeToJSON();
    
}
