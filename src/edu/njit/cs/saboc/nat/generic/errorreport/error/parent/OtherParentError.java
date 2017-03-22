package edu.njit.cs.saboc.nat.generic.errorreport.error.parent;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import org.json.simple.JSONObject;

/**
 * A parent error report that consists of only a comment
 * 
 * @author Chris O
 * 
 * @param <T>
 */
public class OtherParentError<T extends Concept> extends IncorrectParentError<T> {

    public OtherParentError(Ontology<T> ontology, T erroneousParent) {
        super(ontology, erroneousParent);
    }

    public OtherParentError(Ontology<T> ontology, T erroneousParent, String comment, Severity severity) {
        super(ontology, erroneousParent, comment, severity);
    }

    @Override
    public JSONObject toJSON() {
        return super.getBaseJSON("OtherParentError");
    }

    @Override
    public String getSummaryText() {
        return String.format("Other error with parent: %s", this.getIncorrectParent().getName());
    }

    @Override
    public String getTooltipText() {
        return "Other error with parent";
    }
    
    @Override
    public String getStyledText() {
        String text = String.format("<html><font color = 'RED'><b>Other error with parent: </b></font> %s", 
                super.getIncorrectParent().getName());
        
        String desc;
        
        if(this.getComment().isEmpty()) {
            text += ("<br>" + this.getStyledEmptyCommentText());
        } else {
            text += ("<br>" + this.getStyledCommentText());
        }
        
        return text;
    }
}
