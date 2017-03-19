package edu.njit.cs.saboc.nat.generic.errorreport.error.semanticrel;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 * @param <V>
 */
public class OtherSemanticRelationshipError<T extends Concept, V extends InheritableProperty> extends ErroneousSemanticRelationship<T, V> {
    
    public OtherSemanticRelationshipError(Ontology<T> ontology, V relType, T target) {
        super(ontology, relType, target);
    }
    
    public OtherSemanticRelationshipError(
            Ontology<T> ontology, 
            V relType, 
            T target,
            String comment, 
            OntologyError.Severity severity) {
        
       super(ontology, relType, target, comment, severity);
    }

    @Override
    public JSONObject toJSON() {
        return super.getBaseJSON("OtherSemanticRelationshipError");
    }

    @Override
    public String getSummaryText() {
        String abbridgedComment = this.getAbbridgedComment();
        
         return String.format("Other error with == %s ==> %s: %s", 
                this.getRelType().getName(),
                this.getTarget().getName(),
                abbridgedComment);
    }

    @Override
    public String getTooltipText() {
        return "Other error";
    }
}
