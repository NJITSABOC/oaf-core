
package edu.njit.cs.saboc.blu.core.abn.provenance;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * A base class for specifying the arguments needed to create a specific 
 * kind of abstraction network, such that it can be recreated at a later time
 * for the same ontology, a different version of the same ontology, or a 
 * different ontology
 * 
 * @author Chris O
 * 
 * @param <T>
 */
public abstract class AbNDerivation<T extends AbstractionNetwork> {

    private final Ontology sourceOntology;
    
    public AbNDerivation(Ontology sourceOntology) {
        this.sourceOntology = sourceOntology;
    }
    
    public Ontology getSourceOntology() {
        return sourceOntology;
    }
    
    public abstract String getName();
    public abstract String getAbstractionNetworkTypeName();
    
    public abstract String getDescription();
    public abstract T getAbstractionNetwork();
    
    public abstract JSONObject serializeToJSON();
}
