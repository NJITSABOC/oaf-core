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
    
    public boolean equals(Object o) {
        if(o instanceof Edge) {
            Edge<T> other = (Edge<T>)o;
            
            return other.from.equals(from) && other.to.equals(to);
        }
        
        return false;
    }
    
    public int hashCode() {
        return Integer.hashCode(from.hashCode() + to.hashCode());
    }
}
