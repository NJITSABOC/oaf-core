package edu.njit.cs.saboc.nat.generic.errorreport.error.parent;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import org.json.simple.JSONObject;

/**
 * An error report involving a parent that is erroneous and should be removed
 * 
 * @author Chris O
 * 
 * @param <T>
 */
public class ErroneousParentError<T extends Concept> extends RemoveParentError<T> {
    
    public ErroneousParentError(Ontology<T> ontology, T erroneousParent) {
        super(ontology, erroneousParent);
    }
    
    public ErroneousParentError(
            Ontology<T> ontology, 
            T erroneousParent, 
            String comment, 
            OntologyError.Severity severity) {
        
        super(ontology, erroneousParent, comment, severity);
    }
    
    @Override
    public JSONObject toJSON() {
        return super.getBaseJSON("ErroneousParent");
    }

    @Override
    public String getSummaryText() {
        return String.format("Erroneous parent: %s", this.getIncorrectParent().getName());
    }

    @Override
    public String getTooltipText() {
         String text = String.format("<html><font color = 'RED'>"
                + "<b>Remove erroneous parent</b></font>");
        
        if(this.getComment().isEmpty()) {
            text += ("<br>" + this.getStyledEmptyCommentText());
        } else {
            text += ("<br>" + this.getStyledCommentText());
        }
        
        return text;
    }

    @Override
    public String getStyledText() {
        String text = String.format("<html><font color = 'RED'>"
                + "<b>Erroneous parent (remove): </b></font> %s", 
                super.getIncorrectParent().getName());
        
        if(!getComment().isEmpty()) {
            text += ("<br>" + this.getStyledCommentText());
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
        
        final ErroneousParentError<?> other = (ErroneousParentError<?>) obj;
        
        return this.getIncorrectParent().equals(other.getIncorrectParent());
    }
}