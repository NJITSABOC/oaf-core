package edu.njit.cs.saboc.blu.core.abn.diff.change;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import java.util.Collections;

/**
 *
 * @author Chris O
 */
public class UnmodifiedNodeDetails extends NodeChangeDetails {
    
    public UnmodifiedNodeDetails(Node node) {
        super(ChangeState.Unmodified, 
                node, 
                Collections.emptySet(), 
                Collections.emptySet());
    }
}
