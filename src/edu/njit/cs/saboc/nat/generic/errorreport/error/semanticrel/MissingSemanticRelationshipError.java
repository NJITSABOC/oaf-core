
package edu.njit.cs.saboc.nat.generic.errorreport.error.semanticrel;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import java.util.Objects;
import java.util.Optional;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 * @param <V>
 */
public class MissingSemanticRelationshipError<T extends Concept, V extends InheritableProperty> extends SemanticRelationshipError<T> {

    private Optional<V> missingRelType = Optional.empty();
    private Optional<T> missingTarget = Optional.empty();

    public MissingSemanticRelationshipError(Ontology<T> ontology) {
        super(ontology, "", OntologyError.Severity.NonCritical);
    }

    public MissingSemanticRelationshipError(Ontology<T> ontology, String comment, OntologyError.Severity severity) {
        super(ontology, comment, severity);
    }
    
    public void setMissingRelType(V relType) {
        this.missingRelType = Optional.of(relType);
    }
    
    public void clearMissingRelType() {
        this.missingRelType = Optional.empty();
    }
    
    public Optional<V> getMissingRelType() {
        return missingRelType;
    }
    
    public void setMissingRelTarget(T target) {
        this.missingTarget = Optional.of(target);
    }
    
    public void clearMissingRelTarget() {
        this.missingTarget = Optional.empty();
    }
    
    public Optional<T> getMissingTarget() {
        return missingTarget;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject object = super.getBaseJSON("MissingSemanticRelationship");
        
        if(missingRelType.isPresent()) {
            object.put("missingreltypeid", missingRelType.get().getIDAsString());
        }
        
        if(missingTarget.isPresent()) {
            object.put("missingtargetid", missingTarget.get().getIDAsString());
        }
        
        return object;
    }

    @Override
    public String getSummaryText() {
        
        if(missingRelType.isPresent()) {
            if(missingTarget.isPresent()) {
                return String.format("Missing relationship: == %s ==> %s", 
                        missingRelType.get().getName(),
                        missingTarget.get().getName());
            } else {
                return String.format("Missing relationship: == %s ==> [target not specified]", 
                        missingRelType.get().getName());
            }
        } else {
            if(missingTarget.isPresent()) {
                return String.format("Missing relationship: == [type not specified] ==> %s", 
                        missingTarget.get().getName());
            } else {
                return String.format("Missing relationship: %s", this.getAbbridgedComment());
            }
        }
    }

    @Override
    public String getTooltipText() {
        if(missingRelType.isPresent()) {
            if(missingTarget.isPresent()) {
                return String.format("Missing relationship: == %s ==> %s", 
                        missingRelType.get().getName(),
                        missingTarget.get().getName());
            } else {
                return String.format("Missing relationship: == %s ==> [target not specified]", 
                        missingRelType.get().getName());
            }
        } else {
            if(missingTarget.isPresent()) {
                return String.format("Missing relationship: == [type not specified] ==> %s", 
                        missingTarget.get().getName());
            } else {
                return "Missing relationship";
            }
        }
    }

    @Override
    public String getStyledText() {
        
        String missingRelName;
        
        if (missingRelType.isPresent()) {
            if (missingTarget.isPresent()) {
                missingRelName = SemanticRelationshipError.generateStyledRelationshipText(
                        missingRelType.get().getName(), 
                        missingTarget.get().getName());
                
            } else {
               missingRelName = SemanticRelationshipError.generateStyledRelationshipText(
                        missingRelType.get().getName(), 
                        "[target not specified]");
            }
        } else {
            if (missingTarget.isPresent()) {
                missingRelName = SemanticRelationshipError.generateStyledRelationshipText(
                        "[type not specified]", 
                        missingTarget.get().getName());
            } else {
                missingRelName = "[not specified]";
            }
        }
        
        String text =  String.format("<html><font color = 'RED'>"
                + "<b>Missing relationship: </b></font> %s. ", 
                missingRelName);
        
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
        
        final MissingSemanticRelationshipError<?, ?> other = (MissingSemanticRelationshipError<?, ?>) obj;
        
        if (!Objects.equals(this.missingRelType, other.missingRelType)) {
            return false;
        }
        
        if (!Objects.equals(this.missingTarget, other.missingTarget)) {
            return false;
        }
        
        return true;
    }
    
    
}
