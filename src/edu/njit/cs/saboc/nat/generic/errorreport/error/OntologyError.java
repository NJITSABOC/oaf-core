package edu.njit.cs.saboc.nat.generic.errorreport.error;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import org.json.simple.JSONObject;

/**
 * Represents an error reported for a single concept in an ontology
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
    
    public String getAbbridgedComment() {
        String shortComment = getComment();

        if (shortComment.length() > 23) {
            shortComment = String.format("%s...", shortComment.substring(0, 20));
        }

        return shortComment;
    }
    
    /**
     * Returns a short string describing the reported error
     * 
     * @return 
     */
    public abstract String getSummaryText();
    
    /**
     * Returns HTML-styled text for use in tooltips when 
     * a user mouses over the given error
     * 
     * @return 
     */
    public abstract String getTooltipText();
    
    /**
     * Returns HTML-styled text providing a full description of
     * the error
     * 
     * @return 
     */
    public abstract String getStyledText();
    
    /**
     * Serializes the error to a JSON object
     * 
     * @return 
     */
    public abstract JSONObject toJSON();
    
    
    protected String getStyledCommentText() {
        String base = "<html><table><tr><td width = '32'></td><td><font size = '-1'><b>Comments:</b> %s</font></td></tr></table>";
        
        return String.format(base, getComment());
    }
    
    protected String getStyledEmptyCommentText() {
        String base = "<html><table><tr><td width = '32'></td><td><font size = '-1'><b>Comments:</b> %s</font></td></tr></table>";
        
        return String.format(base, "[No comments specified]");
    }
    
    /**
     * Returns the JSON that all ontology errors will contain
     * 
     * @param type
     * @return 
     */
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
