package edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor.result;

/**
 *
 * @author Chris O
 */
public class AncestorDepthResult<T>  {
    private T node;
    
    private int depth;
    
    public AncestorDepthResult(T node, int depth) {
        this.node = node;
        this.depth = depth;
    }
    
    public T getNode() {
        return node;
    }
    
    public int getDepth() {
        return depth;
    }
}
