package edu.njit.cs.saboc.blu.core.abn.diff.change.concepts;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
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
