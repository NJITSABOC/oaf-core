package edu.njit.cs.saboc.nat.generic.errorreport.error.semanticrel;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import java.util.Optional;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 * @param <V>
 */
public class ReplaceTargetError <T extends Concept, V extends InheritableProperty> 
            extends IncorrectSemanticRelationshipError<T, V> {
    
    private Optional<T> replacementTarget = Optional.empty();

    public ReplaceTargetError(Ontology<T> ontology, V relType, T target) {
        super(ontology, relType, target);
    }
    
    public ReplaceTargetError(
            Ontology<T> ontology, 
            V relType, 
            T target,
            String comment, 
            OntologyError.Severity severity) {
        
       super(ontology, relType, target, comment, severity);
    }
    
    public void setReplacementTarget(T target) {
        this.replacementTarget = Optional.of(target);
    }
    
    public void clearReplacementTarget() {
        this.replacementTarget = Optional.empty();
    }
    
    public Optional<T> getReplacementTarget() {
        return replacementTarget;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject object = super.getBaseJSON("ReplaceTargetError");
        
        if(replacementTarget.isPresent()) {
            object.put("replacementtargetid", replacementTarget.get().getIDAsString());
        }
        
        return object;
    }

    @Override
    public String getSummaryText() {
        
        String desc;
        
        if (this.replacementTarget.isPresent()) {
            desc = this.replacementTarget.get().getName();
        } else {
            if (this.getComment().isEmpty()) {
                desc = "[not specified]";
            } else {
                desc = this.getAbbridgedComment();
            }
        }
        
        return String.format("Replace target of == %s ==> %s: %s", 
                this.getRelType().getName(),
                this.getTarget().getName(),
                desc);
    }

    @Override
    public String getTooltipText() {
        if(this.replacementTarget.isPresent()) {
            return String.format("Replace target with: %s", this.replacementTarget.get().getName());
        } else {
            return "Replace target";
        }
    }
    
    @Override
    public String getStyledText() {
        String relName = this.generateStyledRelationshipText(this.getRelType().getName(), this.getTarget().getName());
        
        String replacementTargetName;
        
        if(replacementTarget.isPresent()) {
            replacementTargetName = this.getReplacementTarget().get().getName();
        } else {
            replacementTargetName = "[not specified]";
        }
        
        String text =  String.format("<html><font color = 'RED'>"
                + "<b>Replace target of relationship: </b></font> %s. "
                + "<font color = 'RED'><b>Replace with: </b></font> %s",
                relName,
                replacementTargetName);
        
        text += "<br>";
        
        if(this.getComment().isEmpty()) {
            text += this.getStyledEmptyCommentText();
        } else {
            text += this.getStyledCommentText();
        }

        return text;
    }
}