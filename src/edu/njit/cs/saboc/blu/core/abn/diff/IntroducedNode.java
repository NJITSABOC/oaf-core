
package edu.njit.cs.saboc.blu.core.abn.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.change.NodeIntroduced;
import edu.njit.cs.saboc.blu.core.abn.node.Node;

/**
 *
 * @author Chris O
 */
public class IntroducedNode extends DiffNode<NodeIntroduced> {
    
    private final Node introducedNode;
    
    public IntroducedNode(Node introducedNode, NodeIntroduced details) {
        super(details);
        
        this.introducedNode = introducedNode;
    }
    
    public Node getIntroducedNode() {
        return introducedNode;
    }
}
