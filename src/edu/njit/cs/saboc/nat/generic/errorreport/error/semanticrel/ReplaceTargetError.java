package edu.njit.cs.saboc.nat.generic.errorreport.error.semanticrel;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import java.util.Optional;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
 * @param <T>
 * @param <V>
 */
public class ReplaceTargetError <T extends Concept, V extends InheritableProperty> 
            extends ErroneousSemanticRelationship<T, V> {
    
    private Optional<T> replacementTarget = Optional.empty();

    public ReplaceTargetError(Ontology<T> ontology, V relType, T target) {
        super(ontology, relType, target);
    }
    
    public ReplaceTargetError(
            Ontology<T> ontology, 
            V relType, 
            T target,
            String comment, 
            OntologyError.Severity severity) {
        
       super(ontology, relType, target, comment, severity);
    }
    
    public void setReplacementTarget(T target) {
        this.replacementTarget = Optional.of(target);
    }
    
    public void clearReplacementTarget() {
        this.replacementTarget = Optional.empty();
    }
    
    public Optional<T> getReplacementTarget() {
        return replacementTarget;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject object = super.getBaseJSON("ReplaceTargetError");
        
        if(replacementTarget.isPresent()) {
            object.put("replacementtargetid", replacementTarget.get().getIDAsString());
        }
        
        return object;
    }
}