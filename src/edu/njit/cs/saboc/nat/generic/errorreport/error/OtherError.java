package edu.njit.cs.saboc.nat.generic.errorreport.error;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
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
        String comment = getComment();
        
        if(comment.length() > 23) {
            comment = String.format("%s...", comment.substring(0, 20));
        }
        
        return String.format("Other error: %s", comment);
    }

    @Override
    public String getTooltipText() {
        return getSummaryText();
    }

    @Override
    public String getStyledText() {
        return String.format("<html><font color = 'RED'><b>Other error: </b></font> %s", getComment());
    }
}
