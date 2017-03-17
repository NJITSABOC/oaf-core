package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.semanticrel.OtherSemanticRelationshipError;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 * @param <V>
 */
public class OtherSemanticRelationshipErrorInitializer<T extends Concept, V extends InheritableProperty>
        implements ErrorReportPanelInitializer<OtherSemanticRelationshipError<T, V>> {
    
    private final Ontology<T> theOntology; 
    
    private final V erroneousRelType;
    private final T erroneousTarget;

    public OtherSemanticRelationshipErrorInitializer(Ontology<T> ontology, V erroneousRelType, T erroneousTarget) {
        this.theOntology = ontology;
        
        this.erroneousRelType = erroneousRelType;
        this.erroneousTarget = erroneousTarget;
    }

    @Override
    public String getStyledErrorDescriptionText() {
        return String.format(""
                + "<html><font size = '6'><b>Other relationship error:</b><br>"
                + "<b><font color='red'> == </font><i>%s</i> <font color='red'>==></font></b> %s", 
                erroneousRelType.getName(), 
                erroneousTarget.getName());
    }

    @Override
    public OntologyError.Severity getDefaultSeverity() {
        return OntologyError.Severity.Severe;
    }

    @Override
    public OtherSemanticRelationshipError<T, V> generateError(String comment, OntologyError.Severity severity) {
        return new OtherSemanticRelationshipError<>(theOntology, erroneousRelType, erroneousTarget, comment, severity);
    }

    @Override
    public String getErrorTypeName() {
        return "Other Error with Semantic Relationship";
    }
}
