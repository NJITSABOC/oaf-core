package edu.njit.cs.saboc.nat.generic.errorreport.error;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import org.json.simple.JSONObject;

/**
 * An error other than one currently defined in the NAT system 
 * 
 * @author Chris O
 * 
 * @param <T>
 */
public class OtherError<T extends Concept> extends OntologyError<T> {

    public OtherError(Ontology<T> ontology) {
        this(ontology, "", Severity.NonCritical);
    }
    
    public OtherError(Ontology<T> ontology, 
            String comment, 
            Severity severity) {
        
        super(ontology, comment, severity);
    }

    @Override
    public JSONObject toJSON() {
        return super.getBaseJSON("OtherError");
    }

    @Override
    public String getSummaryText() {
        return String.format("Other error: %s", getComment());
    }

    @Override
    public String getTooltipText() {
        return getStyledText();
    }

    @Override
    public String getStyledText() {
        
        String text = "<html><font color = 'RED'><b>Other error</b></font>";
        
        if(this.getComment().isEmpty()) {
            text += "<br>" + this.getStyledEmptyCommentText();
        } else {
            text += "<br>" + this.getStyledCommentText();
        }
        
        return text;
    }
}
