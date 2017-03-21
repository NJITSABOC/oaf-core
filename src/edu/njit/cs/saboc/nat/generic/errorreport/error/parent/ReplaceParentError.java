package edu.njit.cs.saboc.nat.generic.errorreport.error.parent;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Optional;
import org.json.simple.JSONObject;

/**
 * A report of a parent that should be replaced by another parent
 * 
 * @author Chris O
 * 
 * @param <T>
 */
public class ReplaceParentError<T extends Concept> extends IncorrectParentError<T> {
 
    private Optional<T> replacementParent = Optional.empty();
    
    public ReplaceParentError(Ontology<T> ontology, T erroneousParent) {
        super(ontology, erroneousParent);
    }
    
    public ReplaceParentError(Ontology<T> ontology, T erroneousParent, String comment, Severity severity) {
        super(ontology, erroneousParent, comment, severity);
    }
    
    public void setReplacementParent(T replacementParent) {
        this.replacementParent = Optional.of(replacementParent);
    }
    
    public void clearReplacementParent() {
        this.replacementParent = Optional.empty();
    }
    
    public Optional<T> getReplacementParent() {
        return replacementParent;
    }
    
    @Override
    public JSONObject toJSON() {
        JSONObject errorObject = super.getBaseJSON("ReplaceParent");
        
        if(replacementParent.isPresent()) {
            errorObject.put("replacementparentid", this.getReplacementParent().get().getIDAsString());
        }
        
        return errorObject;
    }

    @Override
    public String getSummaryText() {
        String base = String.format("Replace erroneous parent: %s.", this.getErroneousParent().getName());
        
        String replaceWithDesc;
        
        if (this.replacementParent.isPresent()) {
            replaceWithDesc = this.replacementParent.get().getName();
        } else {
            if (this.getComment().isEmpty()) {
                replaceWithDesc = "[not specified]";
            } else {
                replaceWithDesc = this.getAbbridgedComment();
            }
        }
        
        return String.format("%s Replace with: %s", base, replaceWithDesc);
    }

    @Override
    public String getTooltipText() {
        if(replacementParent.isPresent()) {
            return String.format("Replace with: %s", replacementParent.get().getName());
        } else {
            return "Replace parent";
        }
    }
    
    @Override
    public String getStyledText() {
        
        String description;
        
        if (this.replacementParent.isPresent()) {
            description = this.replacementParent.get().getName();
        } else {
            if (this.getComment().isEmpty()) {
                description = "[not specified]";
            } else {
                description = this.getComment();
            }
        }
        
        
        return String.format("<html><font color = 'RED'>"
                + "<b>Erroneous parent (remove): </b></font> %s. "
                + "<font color = 'RED'><b>Replace with: </b></font>%s", 
                super.getErroneousParent().getName(), 
                description);
    }
}
