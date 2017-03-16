package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.semanticrel.ReplaceTargetError;

/**
 *
 * @author Chris O
 * @param <T>
 * @param <V>
 */
public class ReplaceTargetInitializer<T extends Concept, V extends InheritableProperty> 
        implements MissingConceptInitializer<T, ReplaceTargetError<T, V>>  {
    
    private final Ontology<T> ontology;
    
    private final V relType;
    private final T erroneousTarget;
    
    public ReplaceTargetInitializer(Ontology<T> ontology, V relType, T erroneousTarget) {
        this.ontology = ontology;
        this.relType = relType;
        this.erroneousTarget = erroneousTarget;
    }

    @Override
    public String getStyledErrorDescriptionText() {
        return String.format(""
                + "<html><font size = '6'><b>Replace erroneous target:</b><br>"
                + "<b><font color='red'> == </font><i>%s</i> <font color='red'>==></font></b> %s", 
                relType.getName(), 
                erroneousTarget.getName());
    }

    @Override
    public OntologyError.Severity getDefaultSeverity() {
        return OntologyError.Severity.Moderate;
    }

    @Override
    public ReplaceTargetError<T, V> generateError(String comment, OntologyError.Severity severity) {
        return new ReplaceTargetError<>(ontology, relType, erroneousTarget, comment, severity);
    }
    
    @Override
    public ReplaceTargetError<T, V> generateError(String comment, OntologyError.Severity severity, T replacementConcept) {
        ReplaceTargetError<T, V> error = this.generateError(comment, severity);
        error.setReplacementTarget(replacementConcept);
        
        return error;
    }
}