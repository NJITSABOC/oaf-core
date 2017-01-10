package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 * 
 * @author Chris O
 */
public class TargetGroup extends SinglyRootedNode {
    
    private final IncomingRelationshipDetails relationshipDetails;
    
    public TargetGroup(
            Hierarchy<Concept> conceptHierarchy, 
            IncomingRelationshipDetails relationships) {
        
        super(conceptHierarchy);
        
        this.relationshipDetails = relationships;
    }

    @Override
    public int getConceptCount() {
        return getIncomingRelationshipTargets().size();
    }

    @Override
    public Set<Concept> getConcepts() {
        return getIncomingRelationshipTargets();
    }
 
    public IncomingRelationshipDetails getIncomingRelationshipDetails() {
        return relationshipDetails;
    }
    
    public Set<Concept> getIncomingRelationshipSources() {
        return relationshipDetails.getSourceConcepts();
    }
    
    public Set<Concept> getIncomingRelationshipTargets() {
        return relationshipDetails.getTargetConcepts();
    }
}
