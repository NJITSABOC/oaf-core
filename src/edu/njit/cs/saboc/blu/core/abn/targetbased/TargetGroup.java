package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.SingleRootedConceptHierarchy;
import java.util.Set;

/**
 * 
 * @author Chris O
 */
public class TargetGroup extends SinglyRootedNode {
    
    private final IncomingRelationshipDetails relationships;
    
    public TargetGroup(SingleRootedConceptHierarchy conceptHierarchy, IncomingRelationshipDetails relationships) {
        super(conceptHierarchy);
        
        this.relationships = relationships;
    }
    
    public IncomingRelationshipDetails getIncomingRelationshipDetails() {
        return relationships;
    }
    
    public Set<Concept> getIncomingRelationshipSources() {
        return relationships.getSourceConcepts();
    }
    
    public Set<Concept> getIncomingRelationshipTargets() {
        return relationships.getTargetConcepts();
    }
}
