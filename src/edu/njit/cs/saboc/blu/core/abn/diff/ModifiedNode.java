package edu.njit.cs.saboc.blu.core.abn.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.change.ModifiedNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.node.Node;

/**
 *
 * @author Chris O
 */
public class ModifiedNode extends DiffNode<ModifiedNodeDetails> {

    private final Node from;
    private final Node to;
    
    public ModifiedNode(Node from, Node to, ModifiedNodeDetails details) {
        super(details);
        
        this.from = from;
        this.to = to;
    }
    
    public Node getFromNode() {
        return from;
    }
    
    public Node getToNode() {
        return to;
    }
}