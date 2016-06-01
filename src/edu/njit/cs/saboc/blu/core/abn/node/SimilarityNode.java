package edu.njit.cs.saboc.blu.core.abn.node;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Represents an abstraction network node that captures similarity of some kind, 
 * for example, structural similarity for an area.
 * 
 * @author Chris O
 */
public abstract class SimilarityNode extends PartitionedNode {
    
    public SimilarityNode(int id, 
            ArrayList<Node> parentNodes, 
            HashSet<SinglyRootedNode> internalNodes) {
        
        super(id, parentNodes, internalNodes);
    }
    
    public abstract String getName(String separator);
}
