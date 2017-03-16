package edu.njit.cs.saboc.blu.core.abn.diff.change.concepts;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 * Represents a concept no longer being summarized by a node in the "TO" version
 * of the abstraction network but it is still summarized by at least one node
 * it was summarized by in the "FROM" release.
 * 
 * @author Chris O
 */
public class ConceptRemovedFromNode extends NodeConceptChange {

    private final Set<Node> remainsInNodes;
    
    public ConceptRemovedFromNode(Node node, Concept concept, Set<Node> remainsInNodes) {
        super(NodeConceptSetChangeType.RemovedFromNode, node, concept);
        
        this.remainsInNodes = remainsInNodes;
    }
    
    public Set<Node> getOtherNodes() {
        return remainsInNodes;
    }
}