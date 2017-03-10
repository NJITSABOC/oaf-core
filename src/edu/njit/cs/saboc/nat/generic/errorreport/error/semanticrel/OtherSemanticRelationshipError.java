package edu.njit.cs.saboc.nat.generic.errorreport.error.semanticrel;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 * @param <V>
 */
public class OtherSemanticRelationshipError<T extends Concept, V extends InheritableProperty> extends SemanticRelationshipError<T> {
    
    private final V relType;
    private final T target;
     
    public OtherSemanticRelationshipError(Ontology<T> ontology, V relType, T target) {
        super(ontology);
        
        this.relType = relType;
        this.target = target;
    }
    
    public OtherSemanticRelationshipError(
            Ontology<T> ontology, 
            V relType, 
            T target,
            String comment, 
            OntologyError.Severity severity) {
        
       super(ontology, comment, severity);
       
       this.relType = relType;
       this.target = target;
    }

    public V getRelType() {
        return relType;
    }

    public T getTarget() {
        return target;
    }
}
