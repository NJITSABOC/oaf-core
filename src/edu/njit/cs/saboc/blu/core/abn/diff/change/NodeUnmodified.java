package edu.njit.cs.saboc.blu.core.abn.diff.change;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import java.util.Collections;

/**
 *
 * @author Chris O
 */
public class NodeUnmodified extends NodeChange {
    public NodeUnmodified(Node node) {
        super(NodeChangeState.Unmodified, node, Collections.emptySet(), Collections.emptySet());
    }
}
