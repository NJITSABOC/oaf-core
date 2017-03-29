package edu.njit.cs.saboc.nat.generic.errorreport.error.child;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import org.json.simple.JSONObject;

/**
 * An error of unspecified type on a specific child concept for a given 
 * audit set concept
 * 
 * @author Chris O
 * 
 * @param <T>
 */
public class OtherChildError<T extends Concept> extends IncorrectChildError<T> {

    public OtherChildError(Ontology<T> ontology, T erroneousChild) {
        super(ontology, erroneousChild);
    }

    public OtherChildError(Ontology<T> ontology, T erroneousChild, String comment, Severity severity) {
        super(ontology, erroneousChild, comment, severity);
    }

    @Override
    public JSONObject toJSON() {
        return super.getBaseJSON("OtherChildError");
    }

    @Override
    public String getSummaryText() {
        return String.format("Other error with child: %s", this.getErroneousChild().getName());
    }

    @Override
    public String getTooltipText() {
        String text =  "<html><font color = 'RED'><b>Other error</b></font>";
        
        if(this.getComment().isEmpty()) {
            text += ("<br>" + this.getStyledEmptyCommentText());
        } else {
            text += ("<br>" + this.getStyledCommentText());
        }
        
        return text;
    }

    @Override
    public String getStyledText() {
        String text =  String.format("<html><font color = 'RED'><b>Other error with child: </b></font> %s", 
                super.getErroneousChild().getName());
        
        if(this.getComment().isEmpty()) {
            text += ("<br>" + this.getStyledEmptyCommentText());
        } else {
            text += ("<br>" + this.getStyledCommentText());
        }
        
        return text;
    }
}
