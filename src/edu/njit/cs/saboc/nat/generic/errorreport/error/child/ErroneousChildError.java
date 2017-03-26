package edu.njit.cs.saboc.nat.generic.errorreport.error.child;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public class ErroneousChildError<T extends Concept> extends IncorrectChildError<T> {
    
    public ErroneousChildError(Ontology<T> ontology, T erroneousChild) {
        super(ontology, erroneousChild);
    }
    
    public ErroneousChildError(
            Ontology<T> ontology, 
            T erroneousChild, 
            String comment, 
            OntologyError.Severity severity) {
        
        super(ontology, erroneousChild, comment, severity);
    }
    
    @Override
    public JSONObject toJSON() {
        return super.getBaseJSON("ErroneousChild");
    }

    @Override
    public String getSummaryText() {
        return String.format("Erroneous child: ", super.getErroneousChild().getName());
    }

    @Override
    public String getTooltipText() {
        String text = String.format("<html><font color = 'RED'><b>Child is erroneous</b></font> %s", 
                super.getErroneousChild().getName());
        
        if(this.getComment().isEmpty()) {
            text += ("<br>" + this.getStyledEmptyCommentText());
        } else {
            text += ("<br>" + this.getStyledCommentText());
        }
        
        return text;
    }

    @Override
    public String getStyledText() {
        String text = String.format("<html><font color = 'RED'><b>Erroneous child: </b></font> %s", 
                super.getErroneousChild().getName());
        
        if(!this.getComment().isEmpty()) {
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
        
        final ErroneousChildError<?> other = (ErroneousChildError<?>) obj;
        
        return this.getErroneousChild().equals(other.getErroneousChild());
    }
}