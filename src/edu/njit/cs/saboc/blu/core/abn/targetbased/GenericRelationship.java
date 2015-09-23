package edu.njit.cs.saboc.blu.core.abn.targetbased;

/**
 *
 * @author Chris O
 */
public class GenericRelationship<V, T> {
    private V type;
    private T target;
    
    public GenericRelationship(V type, T target) {
        this.type = type;
        this.target = target;
    }
    
    public V getType() {
        return type;
    }
    
    public T getTarget() {
        return target;
    }
}
