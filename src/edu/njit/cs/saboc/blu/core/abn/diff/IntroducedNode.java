
package edu.njit.cs.saboc.blu.core.abn.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.change.IntroduceNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.node.Node;

/**
 *
 * @author Chris O
 */
public class IntroducedNode extends DiffNode<IntroduceNodeDetails> {
    
    private final Node introducedNode;
    
    public IntroducedNode(Node introducedNode, IntroduceNodeDetails details) {
        super(details);
        
        this.introducedNode = introducedNode;
    }
    
    public Node getIntroducedNode() {
        return introducedNode;
    }
}
