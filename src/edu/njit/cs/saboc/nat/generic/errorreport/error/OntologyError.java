package edu.njit.cs.saboc.nat.generic.errorreport.error;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import org.json.simple.JSONObject;

/**
 * Represents an error reported in an ontology
 * 
 * @author Chris O
 * @param <T>
 */
public abstract class OntologyError<T extends Concept> {
    
    /**
     * The allowed error severity types
     */
    public enum Severity {
        NonCritical,
        Moderate,
        Severe
    }

    private String comment;
    private Severity severity;
    
    private final Ontology<T> ontology;
    
    public OntologyError(Ontology<T> ontology) {
        this(ontology, "", Severity.NonCritical);
    }
    
    public OntologyError(Ontology<T> ontology, String comment, Severity severity) {
        this.ontology = ontology;
        this.comment = comment;
        this.severity = severity;
    }
    
    public Ontology<T> getOntology() {
        return ontology;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public void setSeverity(Severity severity) {
        this.severity = severity;
    }
    
    public Severity getSeverity() {
        return severity;
    }
    
    public abstract JSONObject toJSON();
    
    protected JSONObject getBaseJSON(String type) {
        JSONObject object = new JSONObject();
        object.put("type", type);
        object.put("severity", severity.toString());
        
        if(!comment.isEmpty()) {
            object.put("comment", comment);
        }
        
        return object;
    }
}
