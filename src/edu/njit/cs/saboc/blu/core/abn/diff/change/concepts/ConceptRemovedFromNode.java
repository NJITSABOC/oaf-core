package edu.njit.cs.saboc.blu.core.abn.diff.change.concepts;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class ConceptRemovedFromNode extends NodeConceptChange {

    private final Set<Node> remainsInNodes;
    
    public ConceptRemovedFromNode(Node node, Concept concept, Set<Node> remainsInNodes) {
        super(node, concept);
        
        this.remainsInNodes = remainsInNodes;
    }
    
    public Set<Node> getOtherNodes() {
        return remainsInNodes;
    }
}