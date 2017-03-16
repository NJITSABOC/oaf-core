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
public class ReplaceSemanticRelationshipError<T extends Concept, V extends InheritableProperty> 
            extends ReplaceTargetError<T, V> {
    
    private Optional<V> replacementRelType = Optional.empty();

    public ReplaceSemanticRelationshipError(Ontology<T> ontology, V relType, T target) {
        super(ontology, relType, target);
    }
    
    public ReplaceSemanticRelationshipError(
            Ontology<T> ontology, 
            V relType, 
            T target,
            String comment, 
            OntologyError.Severity severity) {
        
       super(ontology, relType, target, comment, severity);
    }
    
    public void setReplacementRelationshipType(V relType) {
        this.replacementRelType = Optional.of(relType);
    }
    
    public void clearReplacementRelationshipType() {
        this.replacementRelType = Optional.empty();
    }
    
    public Optional<V> getReplacementRelationshipType() {
        return replacementRelType;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject object = super.getBaseJSON("ReplaceRelationshipError");
        
        if(super.getReplacementTarget().isPresent()) {
            object.put("replacementtargetid", super.getReplacementTarget().get().getIDAsString());
        }
        
        if(this.getReplacementRelationshipType().isPresent()) {
            object.put("replacementtypeid", this.getReplacementRelationshipType().get().getIDAsString());
        }

        return object;
    }
}