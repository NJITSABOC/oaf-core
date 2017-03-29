package edu.njit.cs.saboc.nat.generic.errorreport.error.child;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import java.util.Objects;
import java.util.Optional;
import org.json.simple.JSONObject;

/**
 * Represents an error where a child concept should be added to a given
 * audit set concept
 * 
 * @author Chris O
 * 
 * @param <T>
 */
public class MissingChildError<T extends Concept> extends ChildError<T> {
    
    private Optional<T> missingChild = Optional.empty();
    
    public MissingChildError(Ontology<T> ontology) {
        super(ontology, "", OntologyError.Severity.NonCritical);
    }
    
    public MissingChildError(Ontology<T> ontology, String comment, OntologyError.Severity severity) {
        super(ontology, comment, severity);
    }
    
    public void setMissingChild(T missingParent) {
        this.missingChild = Optional.of(missingParent);
    }
    
    public void clearMissingChild() {
        this.missingChild = Optional.empty();
    }
    
    public Optional<T> getMissingChild() {
        return missingChild;
    }
    
    @Override
    public JSONObject toJSON() {
        JSONObject errorObject = super.getBaseJSON("MissingChild");
        
        if(missingChild.isPresent()) {
            errorObject.put("childid", missingChild.get().getIDAsString());
        }
        
        return errorObject;
    }

    @Override
    public String getSummaryText() {
        
        String description;
        
        if(this.missingChild.isPresent()) {
            description = this.missingChild.get().getName();
        } else {
            if(this.getComment().isEmpty()) {
                description = "[not specified]";
            } else {
                description = this.getAbbridgedComment();
            }
        }
        
        return String.format("Child missing: %s", description);
    }

    @Override
    public String getTooltipText() {
        if(this.missingChild.isPresent()) {
            return String.format("Child missing: %s", this.missingChild.get().getName());
        } else {
            return "Child missing";
        }
    }

    @Override
    public String getStyledText() {
        String missingChildName;
        
        if(this.missingChild.isPresent()) {
            missingChildName = this.missingChild.get().getName();
        } else {
            missingChildName = "[Not specified]";
        }
        
        String text = String.format("<html><font color = 'RED'><b>Missing child: </b></font> %s", missingChildName);
        
        if(this.getComment().isEmpty()) {
            text += ("<br>" + this.getStyledEmptyCommentText());
        } else {
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
        
        final MissingChildError<?> other = (MissingChildError<?>) obj;
        
        if (!Objects.equals(this.missingChild, other.missingChild)) {
            return false;
        }
        
        return true;
    }
    
    
}
