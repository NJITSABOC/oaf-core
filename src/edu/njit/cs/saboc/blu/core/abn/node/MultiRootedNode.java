
package edu.njit.cs.saboc.blu.core.abn.node;

import java.util.Set;

/**
 *
 * @author Chris O
 */
public abstract class MultiRootedNode extends Node {
    
    public MultiRootedNode(Set<Node> parentNodes) {
        super(parentNodes);
    }
}
