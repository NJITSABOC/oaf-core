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
public class OtherSemanticRelationshipError<T extends Concept, V extends InheritableProperty> extends IncorrectSemanticRelationshipError<T, V> {
    
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
        String text =  "<html><font color = 'RED'><b>Other error </b></font> %s";
        
        text += "<br>";
        
        if(this.getComment().isEmpty()) {
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
                + "<b>Other error with relationship: </b></font> %s", 
                relName);
        
        text += "<br>";
        
        if(this.getComment().isEmpty()) {
            text += this.getStyledEmptyCommentText();
        } else {
            text += this.getStyledCommentText();
        }

        return text;
    }
}
