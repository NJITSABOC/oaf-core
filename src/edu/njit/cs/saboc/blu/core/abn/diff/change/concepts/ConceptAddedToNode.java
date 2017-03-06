package edu.njit.cs.saboc.blu.core.abn.diff.change.concepts;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 * Represents a concept now being summarized by a node in the "TO" version
 * of an abstraction network while also still being summarized by at least one
 * node is summarized by in the "FROM" release
 * 
 * @author Chris O
 */
public class ConceptAddedToNode extends NodeConceptChange {

    private final Set<Node> otherCurrentNodes;
    
    public ConceptAddedToNode(Node node, Concept concept, Set<Node> otherCurrentNodes) {
        super(NodeConceptSetChangeType.AddedToNode, node, concept);
        
        this.otherCurrentNodes = otherCurrentNodes;
    }
    
    public Set<Node> getOtherNodes() {
        return otherCurrentNodes;
    }
}
