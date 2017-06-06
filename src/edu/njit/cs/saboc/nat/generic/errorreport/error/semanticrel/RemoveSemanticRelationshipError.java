package edu.njit.cs.saboc.nat.generic.errorreport.error.semanticrel;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import org.json.simple.JSONObject;

/**
 * An error where the auditor specified that the given semantic relationship is 
 * erroneous and should be removed without replacement
 * 
 * @author Chris O
 * 
 * @param <T>
 * @param <V>
 */
public class RemoveSemanticRelationshipError<T extends Concept, V extends InheritableProperty> 
        extends IncorrectSemanticRelationshipError<T, V> {
    
    public RemoveSemanticRelationshipError(
            Ontology<T> ontology, 
            V relType, 
            T target) {
        
        super(ontology, relType, target);
    }
    
    public RemoveSemanticRelationshipError(
            Ontology<T> ontology, 
            V relType, 
            T target,
            String comment, 
            OntologyError.Severity severity) {
        
       super(ontology, relType, target, comment, severity);
    }

    @Override
    public JSONObject toJSON() {
        return super.getBaseJSON("RemoveSemanticRelationship");
    }

    @Override
    public String getSummaryText() {
         return String.format("Remove erroneous relationship == %s ==> %s", 
                this.getRelType().getName(),
                this.getTarget().getName());
    }

    @Override
    public String getTooltipText() {
        String text = "<html><font color = 'RED'><b>Remove erroneous relationship</b></font>";

        text += "<br>";

        if (this.getComment().isEmpty()) {
            text += this.getStyledEmptyCommentText();
        } else {
            text += this.getStyledCommentText();
        }

        return text;
    }
    
    @Override
    public String getStyledText() {
        String relName = SemanticRelationshipError.generateStyledRelationshipText(this.getRelType().getName(), this.getTarget().getName());
        
        String text =  String.format("<html><font color = 'RED'>"
                + "<b>Remove erroneous relationship: </b></font> %s", 
                relName);
        
        text += "<br>";
        
        if(!this.getComment().isEmpty()) {
            text += this.getStyledCommentText();
        }

        return text;
    }
    
    @Override
    public boolean equals(Object obj) {
        
        if (obj == null) {
            return false;
        }
        
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final RemoveSemanticRelationshipError<?, ?> other = (RemoveSemanticRelationshipError<?, ?>) obj;
        
        if(!this.getRelType().equals(other.getRelType())) {
            return false;
        }
        
        if(!this.getTarget().equals(other.getTarget())) {
            return false;
        }
        
        return true;
    }
}
