package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.semanticrel.RemoveSemanticRelationshipError;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 * @param <V>
 */
public class ErroneousSemanticRelationshipInitializer<T extends Concept, V extends InheritableProperty>
        implements ErrorReportPanelInitializer<RemoveSemanticRelationshipError<T, V>> {
    
    private final Ontology<T> theOntology; 
    
    private final V erroneousRelType;
    private final T erroneousTarget;

    public ErroneousSemanticRelationshipInitializer(Ontology<T> ontology, V erroneousRelType, T erroneousTarget) {
        this.theOntology = ontology;
        
        this.erroneousRelType = erroneousRelType;
        this.erroneousTarget = erroneousTarget;
    }

    @Override
    public String getStyledErrorDescriptionText() {
        return String.format(""
                + "<html><font size = '6'><b>Remove erroneous relationship:</b><br>"
                + "<b><font color='red'> == </font><i>%s</i> <font color='red'>==></font></b> %s", 
                erroneousRelType.getName(), 
                erroneousTarget.getName());
    }

    @Override
    public OntologyError.Severity getDefaultSeverity() {
        return OntologyError.Severity.Severe;
    }

    @Override
    public RemoveSemanticRelationshipError<T, V> generateError(String comment, OntologyError.Severity severity) {
        return new RemoveSemanticRelationshipError<>(theOntology, erroneousRelType, erroneousTarget, comment, severity);
    }

    @Override
    public String getErrorTypeName() {
        return "Erroneous Semantic Relationship";
    }
}
