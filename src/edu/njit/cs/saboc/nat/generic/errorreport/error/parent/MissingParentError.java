package edu.njit.cs.saboc.nat.generic.errorreport.error.parent;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Objects;
import java.util.Optional;
import org.json.simple.JSONObject;

/**
 * A report of a parent missing from a concept
 * 
 * @author Chris O
 * 
 * @param <T>
 */
public class MissingParentError<T extends Concept> extends ParentError<T> {
    
    private Optional<T> missingParent = Optional.empty();
    
    public MissingParentError(Ontology<T> ontology) {
        super(ontology, "", Severity.NonCritical);
    }
    
    public MissingParentError(Ontology<T> ontology, String comment, Severity severity) {
       super(ontology, comment, severity);
    }
    
    public void setMissingParent(T missingParent) {
        this.missingParent = Optional.of(missingParent);
    }
    
    public void clearMissingParent() {
        this.missingParent = Optional.empty();
    }
    
    public Optional<T> getMissingParent() {
        return missingParent;
    }
    
    @Override
    public JSONObject toJSON() {
        JSONObject errorObject = super.getBaseJSON("MissingParent");
        
        if(missingParent.isPresent()) {
            errorObject.put("parentid", missingParent.get().getIDAsString());
        }
        
        return errorObject;
    }

    @Override
    public String getSummaryText() {
        String description;

        if (this.missingParent.isPresent()) {
            description = this.missingParent.get().getName();
        } else {
            if (this.getComment().isEmpty()) {
                description = "[not specified]";
            } else {
                description = this.getAbbridgedComment();
            }
        }

        return String.format("Parent missing: %s", description);
    }

    @Override
    public String getTooltipText() {
        if(this.missingParent.isPresent()) {
            return String.format("Missing parent: %s", this.missingParent.get().getName());
        } else {
            return "Missing parent";
        }
    }
    
    @Override
    public String getStyledText() {
        String missingParentName;
        
        if(this.missingParent.isPresent()) {
            missingParentName = this.missingParent.get().getName();
        } else {
            missingParentName = "[not specified]'";
        }
        
        String text = String.format("<html><font color = 'RED'><b>Missing parent: </b></font> %s", missingParentName);

        if (!getComment().isEmpty()) {
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
        
        final MissingParentError<?> other = (MissingParentError<?>) obj;
        
        if (!Objects.equals(this.missingParent, other.missingParent)) {
            return false;
        }
        
        return true;
    }
}
