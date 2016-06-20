
package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class RelationshipTriple {
    private final Concept source;
    private final InheritableProperty property;
    private final Concept target;

    public RelationshipTriple(Concept source, InheritableProperty property, Concept target) {
        this.source = source;
        this.property = property;
        this.target = target;
    }
    
    public Concept getSource() {
        return source;
    }

    public InheritableProperty getRelationship() {
        return property;
    }

    public Concept getTarget() {
        return target;
    }
}
