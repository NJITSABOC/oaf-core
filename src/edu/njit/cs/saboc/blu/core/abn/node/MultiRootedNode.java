
package edu.njit.cs.saboc.blu.core.abn.node;

import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public abstract class MultiRootedNode extends Node {
    
    public MultiRootedNode(
            int id, 
            ArrayList<Node> parentNodes) {
        
        super(id, parentNodes);
    }
}
