package edu.njit.cs.saboc.nat.generic.errorreport.error.semanticrel;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import java.util.Objects;
import java.util.Optional;
import org.json.simple.JSONObject;

/**
 * An error where the auditor specified that the given semantic relationship is 
 * erroneous and should be replaced by a semantic relationship of the specified
 * type and target.
 * 
 * @author Chris O
 * 
 * @param <T>
 * @param <V>
 */
public class ReplaceSemanticRelationshipError<T extends Concept, V extends InheritableProperty> 
            extends ReplaceTargetError<T, V> {
    
    private Optional<V> replacementRelType = Optional.empty();

    public ReplaceSemanticRelationshipError(Ontology<T> ontology, V relType, T target) {
        super(ontology, relType, target);
    }
    
    public ReplaceSemanticRelationshipError(
            Ontology<T> ontology, 
            V relType, 
            T target,
            String comment, 
            OntologyError.Severity severity) {
        
       super(ontology, relType, target, comment, severity);
    }
    
    public void setReplacementRelationshipType(V relType) {
        this.replacementRelType = Optional.of(relType);
    }
    
    public void clearReplacementRelationshipType() {
        this.replacementRelType = Optional.empty();
    }
    
    public Optional<V> getReplacementRelationshipType() {
        return replacementRelType;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject object = super.getBaseJSON("ReplaceRelationshipError");
        
        if(super.getReplacementTarget().isPresent()) {
            object.put("replacementtargetid", super.getReplacementTarget().get().getIDAsString());
        }
        
        if(this.getReplacementRelationshipType().isPresent()) {
            object.put("replacementtypeid", this.getReplacementRelationshipType().get().getIDAsString());
        }

        return object;
    }

    @Override
    public String getTooltipText() {

        String replacementRelName;
        
        if (this.replacementRelType.isPresent()) {
            if (super.getReplacementTarget().isPresent()) {
                replacementRelName = SemanticRelationshipError.generateStyledRelationshipText(
                        replacementRelType.get().getName(), 
                        super.getReplacementTarget().get().getName());
                
            } else {
               replacementRelName = SemanticRelationshipError.generateStyledRelationshipText(
                        replacementRelType.get().getName(), 
                        "[target not specified]");
            }
        } else {
            if (super.getReplacementTarget().isPresent()) {
                replacementRelName = SemanticRelationshipError.generateStyledRelationshipText(
                        "[type not specified]", 
                         super.getReplacementTarget().get().getName());
            } else {
                replacementRelName = "[not specified]";
            }
        }

        String text =  String.format("<html><font color = 'RED'>"
                + "<b>Replace relationship with: </b></font> %s",
                replacementRelName);
        
        text += "<br>";
        
        if(this.getComment().isEmpty()) {
            text += this.getStyledEmptyCommentText();
        } else {
            text += this.getStyledCommentText();
        }
        
        return text;
    }
    
    @Override
    public String getStyledText() {
        String incorrectRelName = SemanticRelationshipError.generateStyledRelationshipText(this.getRelType().getName(), this.getTarget().getName());
        
        String replacementRelName;
        
        if (this.replacementRelType.isPresent()) {
            if (super.getReplacementTarget().isPresent()) {
                replacementRelName = SemanticRelationshipError.generateStyledRelationshipText(
                        replacementRelType.get().getName(), 
                        super.getReplacementTarget().get().getName());
                
            } else {
               replacementRelName = SemanticRelationshipError.generateStyledRelationshipText(
                        replacementRelType.get().getName(), 
                        "[target not specified]");
            }
        } else {
            if (super.getReplacementTarget().isPresent()) {
                replacementRelName = SemanticRelationshipError.generateStyledRelationshipText(
                        "[type not specified]", 
                         super.getReplacementTarget().get().getName());
            } else {
                replacementRelName = "[not specified]";
            }
        }

        String text =  String.format("<html><font color = 'RED'>"
                + "<b>Replace relationship: </b></font> %s.<br>"
                + "<font color = 'RED'><b>Replace with: </b></font> %s",
                incorrectRelName,
                replacementRelName);
        
        text += "<br>";
        
        if(this.getComment().isEmpty()) {
            text += this.getStyledEmptyCommentText();
        } else {
            text += this.getStyledCommentText();
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
        
        final ReplaceSemanticRelationshipError<?, ?> other = (ReplaceSemanticRelationshipError<?, ?>) obj;
        
        if(!this.getRelType().equals(other.getRelType())) {
            return false;
        }
        
        if(!this.getTarget().equals(other.getTarget())) {
            return false;
        }
        
        if (!Objects.equals(this.getReplacementTarget(), other.getReplacementTarget())) {
            return false;
        }
        
        if(!Objects.equals(this.replacementRelType, other.replacementRelType)) {
            return false;
        }
        
        return true;
    }
    
}