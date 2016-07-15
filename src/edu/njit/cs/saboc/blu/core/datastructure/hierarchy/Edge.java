package edu.njit.cs.saboc.blu.core.datastructure.hierarchy;

/**
 *
 * @author Chris O
 */
public class Edge<T> {
    private final T from;
    private final T to;
    
    public Edge(T from, T to) {
        this.from = from;
        this.to = to;
    }
    
    public T getFrom() {
        return from;
    }
    
    public T getTo() {
        return to;
    }
}
